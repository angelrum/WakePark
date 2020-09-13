var ticketAjaxUrl = "/ajax/controller/tickets/";
//"/wakepark/ajax/controller/tickets/"

var ticketsTable = {
    datatable_id: '#dt_tickets',
    ajaxUrl: ticketAjaxUrl,
    list: $('#choice_ticket'),
    datatableOpts: {
        "columns": [
            {
                "data": "id",
                "render": renderRadioIdChT,
                "orderable": false,
                "defaultContent": ""
            },
            {
                "data": "pass"
            },
            {
                "data": "name"
            },
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
                "data": "cost",
                "defaultContent": ""
            },
            {
                "data": "weekendcost",
                "defaultContent": ""
            },
            {
                "data": "duration",
                "defaultContent": ""
            },
            {
                "defaultContent": "",
                "orderable": false,
                "render": function (data, type, row) {
                    return renderCount(data, type, row, row.countEdit);
                }
            }
        ]
    },
    datatableParam: {
        "info": false,
        "autoWidth": true,
        "ajax": {
            "url": ticketAjaxUrl,
            "dataSrc": ''
        }
    },
    postSave: function (clientId = null) {
        closeAllModal();
        if (clientId != null) {
            clTickets.updateTableWithParam(clientId);
        }
    }
};

function getClTicketData(clientId = null) {
    var choiceT = getSingleTrValue("select_cht",'radio');                   //getRadioCheckedVal("select_cht");
    if (clientId == null) {
        clientId = $('#client_id').length > 0 ? $('#client_id').val()
            : getSingleTrValue("cl_select",'radio'); //getRadioCheckedVal("cl_select");
    }
    var count = getSingleTrValue("count_" + choiceT,'number', HtmlType.ID); //getCountByName("count_" + cht);
    var error = ( choiceT === null ) ? 'Выберите билет' : '';

    if (clientId === null) {
        error += (error.length > 0) ? '<br>' : '';
        error+="Укажите клиента";
    }
    if (count === null || count == 0 || count.length === 0) {
        error += (error.length > 0) ? '<br>' : '';
        error+="Кол-во должно быть больше 0";
    }
    if (error.length > 0) {
        failNotyWithText(error, "error");
        return null;
    }
    return {
        "client_id" : clientId,
        "ticket_id" : choiceT,
        "count" : count
    };
}

function saveClTicket(clientId = null) {
    var $ser = getClTicketData(clientId);
    if ($ser != null) {
        $.post(clTickets.ajaxUrl, $ser)
            .done(function () {
                if (typeof ticketsTable.postSave !== 'undefined') {
                    ticketsTable.postSave($ser.client_id);
                }
                successNoty("common.saved");
            }).fail(function () {
                failNotyWithText("common.error")
        });
    }
}