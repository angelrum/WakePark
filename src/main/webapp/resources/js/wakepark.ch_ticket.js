let cont_dt = {
    datatable_id: '#dt_ch_ticket',
    datatableOpts: {
        "columns": [
            {
                "data": "id",
                "render": renderRadioIdChT,
                "orderable": false,
                "defaultContent": ""
            },
            {
                "data": "pass",
                //"render": renderLabelRadio,
                "defaultContent": ""
            },
            {
                "data": "name",
                //"render": renderLabelRadio,
                "defaultContent": ""

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
                //"render": renderLabelRadio,
                "defaultContent": ""
            },
            {
                "data": "month",
                //"render": renderLabelRadio,
                "defaultContent": ""
            },
            {
                "data": "cost",
                //"render": renderLabelRadio,
                "defaultContent": ""
            },
            {
                "data": "weekendcost",
                //"render": renderLabelRadio,
                "defaultContent": ""
            },
            {
                "data": "duration",
                //"render": renderLabelRadio,
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
    ajaxUrl: "/ajax/controller/tickets/",
    updateTable: function () {
        $.get(this.ajaxUrl, function (data) {
            cont_dt.datatableApi.clear();
            cont_dt.datatableApi.rows.add(data).draw();
        });
    }
};

function renderRadioIdChT(data, type, row) {
    if (type === "display") {
        return "<input type='radio' id='radio"+ row.id +"' value='"+ data +"' name='select_cht' onchange='handleOnChange(this, false)'>";
    }
}

function startModal()  {
    cont_dt.datatableApi = $(cont_dt.datatable_id).DataTable (
        $.extend(true, cont_dt.datatableOpts, {
            "info": false,
            "scrollX": true,
            "ordering": false
        }, languageDatatable)
    );

    datatableCustomStyle(cont_dt.datatable_id);

    var modal = $('#choice_ticket');

    modal.on('show.bs.modal', function (evt) {
        cont_dt.updateTable(); //при открытие модального окна запрашиваем обновленные данные
    });

    modal.on('shown.bs.modal', function (evt) {
        cont_dt.datatableApi.columns.adjust(); //нужно для выравнивания заголовка к строкам таблицы
    })
}


// function renderCount(data, type, row) {
//     return "<input id='count_"+ row.id + "' name='count' type='number' value = '1' min='1' max='99'>";
// }

