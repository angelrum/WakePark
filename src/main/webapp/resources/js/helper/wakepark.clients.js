var clientAjaxUrl = "/ajax/controller/clients/";

var clientsTable = {
    datatable_id: '#dt_clients',
    ajaxUrl: clientAjaxUrl,
    form:   $('#clientForm'),
    create: $('#clientCreate'),
    title:  $("#modalTitle"),
    datatableOpts: {
        "columns": [
            {"data": "id",
                "orderable": false,
                "render": function (data, type, row) {
                    return renderRadioId(data, type, row, "cl_select");
                },
                "defaultContent": ""
            },
            {"data": "firstname"},
            {"data": "lastname"},
            {"data": "middlename"},
            {"data": "telnumber"},
            {"data": "city"},
            {
                "data": "email",
                "defaultContent": ""},
            {
                "render": function (data, type, row) {
                    if (type === "display") {
                        return "<a onclick='clientsTable.updateRow(" + row.id + ");'><span class='fa fa-pencil'></span></a>";
                    }
                },
                "defaultContent": "",
                "orderable": false
            },
            {
                "render": function (data, type, row) {
                    if (type === "display") {
                        return "<a onclick='clientsTable.deleteRow(" + row.id + ");'><span class='fa fa-trash-o'></span></a>";
                    }
                },
                "defaultContent": "",
                "orderable": false
            }
        ]
    },
    datatableParam: {
        "info": false,
        "ajax": {
            "url": clientAjaxUrl,
            "dataSrc": '',
            "error": function (xhr, error, thrown) {
                this.datatableApi.draw(); // в случае пустых данных, чистим таблицу
            }
        }
    },
    convertModalValue: function(key, value) {
        if (key === 'telnumber') { return  convertPhoneNumber(value); }
        return value;
    },
    //Чистим блок с клиентскими билетами, деактивируем кнопку добавить билет
    postDelete: function () {
        if (typeof clTickets !== "undefined") {
            clTickets.datatableApi.clear();
            clTickets.datatableApi.draw();
            $('#btn_cl_ticket').attr('disabled', true)
        }
    }
};

function handleOnChange(ev, flag) {
    $('tr input[id^="radio"]')
        .parent().parent().removeClass("tr-hover");
    $('tr input[id="radio'+ ev.value + '"').parent().parent().addClass("tr-hover");
    if (flag) {
        clTickets.updateTableWithParam(ev.value);
        $('#btn_cl_ticket').removeAttr("disabled");
        $('#collapse_cl_tickets').collapse('show');
    }
}

function showTickets() {
    ticketsTable.updateTable();
    ticketsTable.list.modal();
}

