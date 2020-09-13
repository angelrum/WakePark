var ticketAjaxUrl = "/ajax/controller/tickets/";

var ticketsTable = {
    datatable_id: '#dt_tickets',
    ajaxUrl: ticketAjaxUrl,
    form:   $('#ticketForm'),
    create: $('#ticketCreate'),
    title:  $("#ticketTitle"),
    datatableOpts: {
        "columns": [
            {"data": "pass"},
            {"data": "name"},
            {
                "data": "enable",
                "render": renderCheckBox,
                "defaultContent": ""
            },
            {
                "data": "equipment",
                "render": renderCheckBox,
                "defaultContent": ""
            },
            {
                "data": "duration"
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
                "data": "startDate",
                "defaultContent": ""
            },
            {
                "data": "endDate",
                "defaultContent": ""
            },
            {
                "data": "day"
            },
            {
                "data": "month"
            },
            {
                "data": "cost"
            },
            {
                "data": "weekendcost"
            },
            {
                "render": function (data, type, row) {
                    if (type === "display") {
                        return "<a onclick='ticketsTable.updateRow(" + row.id + ");'><span class='fa fa-pencil'></span></a>";
                    }
                },
                "defaultContent": "",
                "orderable": false
            },
            {
                "render": function (data, type, row) {
                    if (type === "display") {
                        return "<a onclick='ticketsTable.deleteRow(" + row.id + ");'><span class='fa fa-remove'></span></a>";
                    }
                },
                "defaultContent": "",
                "orderable": false
            }
        ]
    },
    datatableParam: {
        "info": false,
        "scrollX": true,
        "ajax": {
            "url": ticketAjaxUrl,
            "dataSrc": ''
        }
    },
    defModalValue: function (form) {
        form.find('#duration').val("5");
        form.find('#select').val("Разовый");
        form.find("input[name='day']").val(0);
        form.find("input[name='month']").val(0);
        form.find("input[name='startTime']").val('09:00');
        form.find("input[name='endTime']").val('23:00');
        form.find('#year').val(new Date().getFullYear());
    },
    presave: function (form) {
        form.find('input[type=checkbox]').each(function () {
            if (!this.checked) {
                $(this).attr("value","0");
            }else{
                $(this).attr("value","1");
            }
        });
        form.find('input[type=time]').each(function () {
            if (this.value.length === 8) {
                this.value = this.value.substring(0, 5);
            }
        })
    },
};
// дополнительные функции блока
function clearFilter() {
    $("#filter")[0].reset();
}

function updateFilteredTable() {
    $.get(ticketsTable.ajaxUrl + 'filter', $('#filter').serialize()).done(function (data) {
        ticketsTable.datatableApi.clear();
        ticketsTable.datatableApi.rows.add(data).draw();
    });
}
