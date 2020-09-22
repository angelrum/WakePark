var clTicketAjaxUrl = "/ajax/controller/client/tickets/";

var clTickets = {
    datatable_id: '#dt_cl_tickets',
    ajaxUrl: clTicketAjaxUrl,
    datatableOpts: {
        "columns": [
            {"data": "pass"},
            {"data": "name"},
            {
                "data": "equipment",
                "render": renderCheckBox,
                "defaultContent": ""
            },
            {
                "data": "startTime",
                "render": convertTime,
                "defaultContent": ""
            },
            {
                "data": "endTime",
                "render": convertTime,
                "defaultContent": ""
            },
            {
                "data": "day",
                "defaultContent": ""
            },
            {
                "data": "month",
                "defaultContent": ""
            },
            {
                "data": "duration",
                "defaultContent": ""
            },
            {
                "data": "count",
                "render": function (data, type, row) {
                    return renderCount(data, type, row, false);
                },
                "defaultContent": ""
            },
            {
                "render": renderEditBtn,
                "defaultContent": "",
                "orderable": false
            },
            {
                "render": renderDeleteBtn,
                "defaultContent": "",
                "orderable": false
            }

        ]
    },
    datatableParam: {
        "info": false,
        "ajax": {
            "url": clTicketAjaxUrl,
            "dataSrc": '',
            "error": function (xhr, error, thrown) {
                clTickets.datatableApi.draw(); // в случае пустых данных, чистим таблицу
            }
        }
    },
    updateTableWithParam: function (id, filter = false, param = null) {
        var url = this.ajaxUrl;
        if (filter) {
            url+= 'filter?' + param + '&clientId=' + id;
        } else {
            url+=id;
        }
        $.get(url, function (data) {
            clTickets.datatableApi.clear();
            clTickets.datatableApi.rows.add(data).draw();
        });
    },
    //переопределяем ф-ии, устанавливаемые в common
    deleteRow: function (id) {
        if (confirm(i18n['common.confirm'])) {
            updateOrDeleteClTicket(id, 'DELETE', i18n['common.deleted'], function () {
                clTickets.updateTableWithParam(getSingleTrValue("cl_select",'radio'));
            });
        }
    },
    editRow: function(id) {
        $('#sv_row_' + id).removeClass('d-none');
        $('#ed_row_' + id).addClass('d-none');
        $('#count_' + id).attr('disabled', false);
    },
    save: function (id) {
        $('#count_' + id).attr('disabled', true);
        $('#sv_row_' + id).addClass('d-none');
        $('#ed_row_' + id).removeClass('d-none');
        updateOrDeleteClTicket(id);
    }
};

function updateTableByCurrentTime(clientId = null, filter = false) {
    var currentDate = new Date();
    var param = 'startTime=' + leadZero(currentDate.getHours()) + ':' + leadZero(currentDate.getMinutes());
    clTickets.updateTableWithParam(clientId, filter, param);
}

// кнопки на полях, для редактирования кол-ва и удаления строк
function renderEditBtn(data, type, row) {
    let st_class = "";
    if (type === "display") {
        if (row.countEdit === false) st_class = "class='d-none'";
        return "<a id='sv_row_"+ row.id +"' onclick='clTickets.save(" + row.id + ");' class='d-none'><span class='fa fa-save'></span></a>" +
            "<a id='ed_row_"+ row.id +"' onclick='clTickets.editRow(" + row.id + ");' " + st_class + "><span class='fa fa-pencil'></span></a>";
    }
}

function renderDeleteBtn(data, type, row) {
    if (type === "display") {
        return "<a id='del_row_"+ row.id +"' onclick='clTickets.deleteRow(" + row.id +");'><span class='fa fa-trash-o'></span></a>";
    }
}

function updateOrDeleteClTicket(id, type = 'PUT', successText = i18n['common.saved'], successFunction = function () {}) {
    var client = getSingleTrValue("cl_select",'radio');                //getRadioCheckedVal("cl_select");
    var count = getSingleTrValue("count_" + id,'number', HtmlType.ID); //getCountByName("count_" + id);

    $.get(clTickets.ajaxUrl,{ "id": id, "clientId": client})
        .done(function (data) {
            $.ajax({
                type: type,
                url: clTickets.ajaxUrl,
                data: { "client_id": data.clientId, "ticket_id": data.ticketId, "count": count }
            }).done(function () {
                successNoty(successText);
                successFunction();
            }).fail(function () {failNotyWithText(i18n['common.errorStatus'])});
        }).fail(function () {failNotyWithText(i18n['common.errorStatus'])});
}