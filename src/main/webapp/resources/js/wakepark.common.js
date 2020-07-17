var context, form;

function makeEditable(ctx) {
    context = ctx;
    context.datatableApi = $(ctx.datatable_id).DataTable (
        $.extend(true, ctx.datatableOpts, {
                "info": false,
                "scrollX": true,
                "ajax": {
                    "url": context.ajaxUrl,
                    "dataSrc": ""
                }
            }, languageDatatable)
    );
    datatableCustomStyle(ctx.datatable_id);
    form = $('#detailsForm');
    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(jqXHR);
    });

    // solve problem with cache in IE: https://stackoverflow.com/a/4303862/548473
    $.ajaxSetup({cache: false});
}

function datatableCustomStyle(dt_id) {
    let dt_filter = $(dt_id + '_filter input');
    dt_filter.addClass('form-control');
    dt_filter.removeClass('form-control-sm');

    $(dt_id + '_filter input').attr('placeholder', 'Поиск...');
    $('.dataTables_length').addClass('bs-select');
    $(dt_id + '_length label').addClass('text-muted');
    $(dt_id + '_paginate ul').addClass('text-muted');
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
            setElementValue(key, value);
        });
        $('#create').modal();
    });
}

function setElementValue(key, value) {
    if (typeof context.convertModalValue != "undefined") {
        value = context.convertModalValue(key, value);
    }
    var el = form.find("input[name='" + key + "']");
    if (el.length === 0) {
        el = form.find("select[name='" + key + "']");
    }
    if (el.length > 0) {
        if (el.is(':checkbox')) {
            el.prop('checked', value)
        } else {
            el.val(value);
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

function clearFilter() {
    $("#filter")[0].reset();
}

function updateFilteredTable() {
    $.get(context.ajaxUrl + 'filter', $('#filter').serialize()).done(function (data) {
        updateTableByData(data);
    });
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

// Type = 'alert' | 'success' | 'warning' | 'error' | 'info' | 'information';
function failNotyWithText(text, type = 'error') {
    failedNote = new Noty({
        text: "<span class='fa fa-lg fa-exclamation-circle'></span> &nbsp; Ошибка проверки данных <br>" + text,
        type: type,
        layout: "bottomRight",
        timeout: 1000
    }).show();
}

/*
* Основные комбинации:
* $('tr input[name='+ name +']:radio:checked')
* $('tr input[id='+ name +'][type=number]');
* */
function getSingleTrValue(name, type = null, htmlType = HtmlType.NAME) {
    var sel = 'tr input['+ htmlType +'='+ name +']';
    sel += (type==='radio') ? ':radio:checked' : ((type != null) ? '[type='+ type + ']' : '');
    var $value = $(sel);
    if ($value.length > 0)
        return $value[0].value;
    return null;
}

/* старые данные
function getRadioCheckedVal(name) {
    var sel = $('tr input[name='+ name +']:radio:checked');
    if (sel.length === 1) {
        return sel[0].value;
    }
    return null;
}

function getCountByName(name) {
    var sel = $('tr input[id='+ name +'][type=number]');
    if (sel.length === 1) {
        return sel[0].value;
    }
    return null;
} */