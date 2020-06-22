var context, form;

var languageDatatable = {
    "language" : {
        processing: "Подождите...",
        search: "",
        lengthMenu: "<h6>Показать _MENU_ записей</h6>",
        info: "Записи с _START_ до _END_ из _TOTAL_ записей",
        infoEmpty: "Записи с 0 до 0 из 0 записей",
        infoFiltered: "(отфильтровано из _MAX_ записей)",
        infoPostFix: "",
        loadingRecords: "Загрузка записей...",
        zeroRecords: "Записи отсутствуют.",
        emptyTable: "В таблице отсутствуют данные",
        paginate: {
            first: "Первая",
            previous: "Предыдущая",
            next: "Следующая",
            last: "Последняя"
        },
        aria: {
            sortAscending: ": активировать для сортировки столбца по возрастанию",
            sortDescendin: ": активировать для сортировки столбца по убыванию"
        },
        select: {
            rows: {
                "0": "Кликните по записи для выбора",
                "1": "Выбрана одна запись",
                "_": "Выбрано записей: %d"
            }
        }
    }
};

function makeEditable(ctx) {
    context = ctx;
    context.datatableApi = $('#datatable').DataTable (
        $.extend(true, ctx.datatableOpts, {
                "info": false,
                "scrollX": true,
                "ajax": {
                    "url": context.ajaxUrl,
                    "dataSrc": ""
                }
            }, languageDatatable)
    );

    form = $('#detailsForm');
    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(jqXHR);
    });

    // solve problem with cache in IE: https://stackoverflow.com/a/4303862/548473
    $.ajaxSetup({cache: false});
}

function datatableCustom() {
    var dt_filter = $('#datatable_filter > label > input');
    dt_filter.addClass('form-control');
    dt_filter.removeClass('form-control-sm');

    document.querySelector('#datatable_filter > label > input')
        .setAttribute('placeholder', 'Поиск...');

    $('.dataTables_length').addClass('bs-select');
    $('#datatable_length > label').addClass('text-muted');
    $('#datatable_paginate > ul').addClass('text-muted');
}

function renderEditBtn(data, type, row) {
    if (type === "display") {
        return "<a onclick='updateRow(" + row.id + ");'><span class='fa fa-pencil'></span></a>";
    }
}

function renderDeleteBtn(data, type, row) {
    if (type === "display") {
        return "<a onclick='deleteRow(" + row.id + ");'><span class='fa fa-remove'></span></a>";
    }
}

function save() {
    if (typeof context.presave != "undefined") {
        context.presave(form);
    }

    var $ser = form.serialize();
    if ($ser.endsWith('&email=')) {
        $ser = $ser.replace('&email=', '');
    }
    $.ajax({
        type: "POST",
        url: context.ajaxUrl,
        data: $ser
    }).done(function () {
        $("#create").modal("hide");
        context.updateTable();
        successNoty("common.saved");
    });
}

function updateRow(id) {
    $("#modalTitle").html("Изменить данные");
    $.get(context.ajaxUrl + id, function (data) {
        form.find(":input").val("");
        $.each(data, function (key, value) {
            if (key === 'telnumber') {
                value = convertPhoneNumber(value);
            }
            setElementValue(key, value);
        });
        $('#create').modal();
    });
}

function setElementValue(key, value) {
    var el = form.find("input[name='" + key + "']");
    if (el.length === 0) {
        el = form.find("select[name='" + key + "']");
    }
    if (el.length > 0) {
        el.val(value);
        if (el[0].id === "duration") {
            context.synchronyzeButtons(value);
        }
    }
}

function add() {
    $("#modalTitle").html("Добавить данные");
    form.find(":input").val("");
    if (typeof context.defModalValue != "undefined") {
        context.defModalValue(form);
    }

    $("#create").modal();
}

function deleteRow(id) {
    if (confirm('common.confirm')) {
        $.ajax({
            url: context.ajaxUrl + id,
            type: "DELETE"
        }).done(function () {
            context.updateTable();
            successNoty("common.deleted");
        });
    }
}

function updateTableByData(data) {
    context.datatableApi.clear();
    context.datatableApi.rows.add(data).draw();
}

var failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(text) {
    closeNoty();
    new Noty({
        text: "<span class='fa fa-lg fa-check'></span> &nbsp;" + text,
        type: 'success',
        layout: "bottomRight",
        timeout: 1000
    }).show();
}

function failNoty(jqXHR) {
    closeNoty();
    var errorInfo = JSON.parse(jqXHR.responseText);
    failedNote = new Noty({
        text: "<span class='fa fa-lg fa-exclamation-circle'></span> &nbsp;" + errorInfo.typeMessage + "<br>" + errorInfo.details.join("<br>"),
        type: "error",
        layout: "bottomRight"
    }).show();
}

function convertPhoneNumber(telnumber) {
    telnumber = telnumber.replace('+7', '');
    var phonenumber = "";
    for(let num of telnumber) {
        if (/\d/i.test(num) && phonenumber.length < 15) {
            if (phonenumber.length === 0) {
                phonenumber = "(";
            }
            switch (phonenumber.length) {
                case 4:
                    phonenumber+=") ";
                    break;
                case 9:
                case 12:
                    phonenumber+="-";
                    break;

            }
            phonenumber += num;
        }
    }
    return phonenumber;
};