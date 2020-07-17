var cl_tickets = {
    datatable_id: '#dt_cl_tickets',
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
                "data": "day"
            },
            {
                "data": "month"
            },
            {
                "data": "duration"
            },
            {
                "data": "count",
                "render": function (data, type, row) {
                    return renderCount(data, type, row, false);
                },
                "defaultContent": ""
            },
            {
                "render": renderClTEditBtn,
                "defaultContent": "",
                "orderable": false
            },
            {
                "render": renderClTDeleteBtn,
                "defaultContent": "",
                "orderable": false
            }

        ]
    },
    ajaxUrl: "/ajax/controller/client/tickets/",
    updateTable: function () {
        $.get(cl_tickets.ajaxUrl, function (data) {
            cl_tickets.datatableApi.clear();
            cl_tickets.datatableApi.rows.add(data).draw();
        });
    },
    getTable: function (id) {
        $.get(cl_tickets.ajaxUrl + id, function (data) {
            cl_tickets.datatableApi.clear();
            cl_tickets.datatableApi.rows.add(data).draw();
        });
    }
};

function renderClTEditBtn(data, type, row) {
    let st_class = "";
    if (type === "display") {
        if (row.countEdit === false) st_class = "class='d-none'";
        return "<a id='sv_row_"+ row.id +"' onclick='saveRow(" + row.id + ");' class='d-none'><span class='fa fa-save'></span></a>" +
            "<a id='ed_row_"+ row.id +"' onclick='editRow(" + row.id + ");' " + st_class + "><span class='fa fa-pencil'></span></a>";
    }
}

function renderClTDeleteBtn(data, type, row) {
    if (type === "display") {
        return "<a id='del_row_"+ row.id +"' onclick='deleteClTRow(" + row.id +");'><span class='fa fa-remove'></span></a>";
    }
}

function editRow(id) {
    $('#sv_row_' + id).removeClass('d-none');
    $('#ed_row_' + id).addClass('d-none');
    $('#count_' + id).attr('disabled', false);
}

function saveRow(id) {
    $('#count_' + id).attr('disabled', true);
    $('#sv_row_' + id).addClass('d-none');
    $('#ed_row_' + id).removeClass('d-none');
    updateOrDeleteClTicket(id);
}

function deleteClTRow(id) {
    if (confirm('common.confirm')) {
        updateOrDeleteClTicket(id, 'DELETE', 'common.deleted', function () {
            cl_tickets.getTable(getSingleTrValue("cl_select",'radio'));
        });
    }
}

function downloadTickets(clientId) {
    if (typeof cl_tickets.datatableApi === 'undefined')
        init();
    cl_tickets.getTable(clientId);
    $('#btn_cl_ticket').removeAttr("disabled");
}

function init() {
    cl_tickets.datatableApi = $(cl_tickets.datatable_id).DataTable(
        $.extend(true, cl_tickets.datatableOpts, {
            "info": false,
            "ordering": false,
            "paging": false,
            "searching": false,
            "autoFill": true
        }, languageDatatable)
    );

    datatableCustomStyle(cl_tickets.datatable_id);

    $('#collapse_cl_tickets').on('shown.bs.collapse', function () {
        cl_tickets.datatableApi.columns.adjust().draw();
    });
}

function saveClTicket() {
    var $ser = getClTicketData();
    if ($ser != null) {
        $.post(cl_tickets.ajaxUrl, $ser)
            .done(function () {
                $('#choice_ticket').modal('hide');
                cl_tickets.getTable($ser.client_id);
                successNoty("common.saved");
            }).fail(function () {
                failNotyWithText("common.error")
            });

        /*
        $.ajax({
            type: "POST",
            url: cl_tickets.ajaxUrl,
            data: $ser
        }).done(function () {
            $('#choice_ticket').modal('hide');
            cl_tickets.getTable($ser.client_id);
            successNoty("common.saved");
        }); */
    }
}

function updateOrDeleteClTicket(id, type = 'PUT', successText = 'common.saved', successFunction = function () {}) {
    var client = getSingleTrValue("cl_select",'radio');                //getRadioCheckedVal("cl_select");
    var count = getSingleTrValue("count_" + id,'number', HtmlType.ID); //getCountByName("count_" + id);

    $.get(cl_tickets.ajaxUrl,{ "id": id, "clientId": client})
        .done(function (data) {
            $.ajax({
                type: type,
                url: cl_tickets.ajaxUrl,
                data: { "client_id": data.clientId, "ticket_id": data.ticketId, "count": count }
            }).done(function () {
                successNoty(successText);
                successFunction();
            }).fail(function () {failNotyWithText("common.error")});
        }).fail(function () {failNotyWithText("common.error")});

    /*
    $.ajax({
        type: "GET",
        url: cl_tickets.ajaxUrl,
        data: {
            "id": id,
            "clientId": client
        }
    }).done(function (data) {
        $.ajax({
            type: "PUT",
            url: cl_tickets.ajaxUrl,
            data: {
                "client_id": data.clientId,
                "ticket_id": data.ticketId,
                "count": count
            }
        }).done(function () {
            successNoty("common.saved");
        });
    });
     */
}

function getClTicketData() {
    var choiceT = getSingleTrValue("select_cht",'radio');                   //getRadioCheckedVal("select_cht");
    var client = getSingleTrValue("cl_select",'radio');                     //getRadioCheckedVal("cl_select");
    var count = getSingleTrValue("count_" + choiceT,'number', HtmlType.ID); //getCountByName("count_" + cht);
    var error = ( choiceT === null ) ? 'Выберите билет' : '';

    if (client === null) {
        error += (error.length > 0) || '<br>';
        error+="Укажите клиента";
    }
    if (count === null || count == 0 || count.length === 0) {
        error += (error.length > 0) || '<br>';
        error+="Кол-во должно быть больше 0";
    }
    if (error.length > 0) {
        failNotyWithText(error, "error");
        return null;
    }
    return {
        "client_id" : client,
        "ticket_id" : choiceT,
        "count" : count
    };
}