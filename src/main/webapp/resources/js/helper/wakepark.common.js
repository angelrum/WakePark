var telnumber = $('#telnumber');

function datatableCustomStyle(dt_id) {
    let dt_filter = $(dt_id + '_filter input');
    dt_filter.addClass('form-control');
    dt_filter.removeClass('form-control-sm');

    $(dt_id + '_filter input').attr('placeholder', 'Поиск...');
    $('.dataTables_length').addClass('bs-select');
    $(dt_id + '_length label').addClass('text-muted');
    $(dt_id + '_paginate ul').addClass('text-muted');
    $(dt_id + '_paginate').addClass('mt-2');
    $(dt_id).on( 'draw.dt', function () {
        $('.page-item.active > .page-link').css('background-color', '#4FC3A1');
        $('.page-item.active > .page-link').css('border-color', '#4FC3A1');
    });
}

$('.sidebar-toggle').on('click touch', (function() {
    $('.sidebar').toggleClass('toggled');
}));

function makeEditable(ctx, api = true) {
    if(!api) { //удаляем ajax св-во
        delete ctx.datatableParam.ajax;
    }
    ctx.datatableApi = $(ctx.datatable_id).DataTable (
        $.extend(true, ctx.datatableOpts, ctx.datatableParam, languageDatatable)
    );
    // update table
    if (typeof ctx.updateTable === "undefined") {
        ctx.updateTable = function () {
            if (typeof ctx.datatableApi !== "undefined") {
                $.get(ctx.ajaxUrl, function (data) {
                    ctx.datatableApi.clear();
                    ctx.datatableApi.rows.add(data).draw();
                });
            }
        }
    }

    ctx.updateTableByData = function (data) {
        ctx.datatableApi.clear();
        ctx.datatableApi.rows.add(data).draw();
    };

    if (typeof ctx.updateRow === "undefined") {
        ctx.updateRow = updateRow;
    }

    if (typeof ctx.deleteRow === "undefined") {
        ctx.deleteRow = deleteRow;
    }

    if (typeof ctx.add === "undefined") {
        ctx.add = add;
    }
    if (typeof ctx.save === "undefined") {
        ctx.save = save;
    }

    datatableCustomStyle(ctx.datatable_id);
    // solve problem with cache in IE: https://stackoverflow.com/a/4303862/548473
    $.ajaxSetup({cache: false});

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
}
//*** Функции для работы с контекстом ***
// update row in table
function updateRow(id) {
    var  ctx = this;
    ctx.title.html(i18n['editTitle']);
    $.get(this.ajaxUrl + id, function (data) {
        ctx.form.find(":input").val("");
        $.each(data, function (key, value) { setElementValue(key, value, ctx);});
        ctx.create.modal();
    });
}

// delete row in table
function deleteRow(id) {
    var ctx = this;
    if (confirm(i18n['common.confirm'])) {
        $.ajax({url: this.ajaxUrl + id, type: "DELETE" }).done(function () {
            ctx.updateTable();
            successNoty('common.deleted');
            if (typeof ctx.postDelete != "undefined") {
                ctx.postDelete();
            }
        });
    }
}
// отображение модального окна для добавления записи
function add() {
    this.title.html(i18n["addTitle"]);
    this.form.find(":input").val("");
    if (typeof this.defModalValue != "undefined") {
        this.defModalValue(this.form);
    }
    this.create.modal();
}

function save() {
    var ctx = this;
    if (typeof ctx.presave != "undefined") { ctx.presave(ctx.form);}

    //var $ser = ctx.form.serialize();
    // if ($ser.endsWith('&email=')) {
    //     $ser = $ser.replace('&email=', '');
    // }

    var $ser = ctx.form.serializeArray();
    if (typeof ctx.presavedata != "undefined") { ctx.presavedata($ser);}
    // for (var i = 0; i < $ser.length; i++) {
    //     if ($ser[i].name === 'telnumber') {
    //         $ser[i].value = $ser[i].value.startsWith('+7') ? $ser[i].value : '+7' + $ser[i].value;
    //     }
    // }

    $.ajax({ type: "POST", url: ctx.ajaxUrl, data: $ser }).done(function () {
        closeAllModal();
        ctx.updateTable();
        if (typeof ctx.postSave != "undefined") {
            ctx.postSave();
        }
        successNoty('common.saved');
    });
}

//*** конец функций ***

function setElementValue(key, value, context) {
    if (typeof context.convertModalValue != "undefined") {
        value = context.convertModalValue(key, value);
    }
    var el = context.form.find("input[name='" + key + "']");
    if (el.length === 0) {
        el = context.form.find("select[name='" + key + "']");
    }
    if (el.length > 0) {
        if (el.is(':checkbox')) { el.prop('checked', value) }
        else { el.val(value); }
    }
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

function closeAllModal() {
    $('.modal').modal('hide');
    closeNoty();
}

var phoneblur = function (id) {
    var phone = document.getElementById(id);
    if(phone.value === "(") { phone.value = "";}
};

var checkPhoneKey = function (key, id) {
    var tel = $('#' + id);
    return (key >= '0' && key <= '9' && tel.val().length < 15) || key === 'ArrowLeft' || key === 'ArrowRight' || key === 'Delete' || key === 'Backspace';
};

function convertPhoneNumber(telnumber) {
    telnumber = telnumber.replace('+7', '');
    var phonenumber = "";
    for(let num of telnumber) {
        if (/\d/i.test(num) && phonenumber.length < 15) {
            if (phonenumber.length === 0) { phonenumber = "("; }
            switch (phonenumber.length) {
                case 4: phonenumber+=") ";
                    break;
                case 9: case 12:
                    phonenumber+="-";
                    break;
            }
            phonenumber += num;
        }
    }
    return phonenumber;
}

telnumber.keypress(function (evt) {
    return checkPhoneKey(evt.key, this.id);
});

telnumber.click(function (evt) {
    if (this.value.length === 0) { this.value = "("; }
});

telnumber.keyup(function (evt) {
    this.value = convertPhoneNumber(this.value);
});

function removeField(id, clearVal = null) {
    $('#'+id).val(''); //each(function () { this.value = '';});
    if (clearVal != null) clearVal();
}

//Может в отдельный файл вынести?
var failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(key) {
    closeNoty();
    new Noty({
        text: "<span class='fa fa-lg fa-check'></span> &nbsp;" + i18n[key],
        type: 'success',
        layout: "bottomRight",
        timeout: 1000
    }).show();
}

function failNotyByKey(key, type = 'error') {
    failedNote = new Noty({
        text: "<span class='fa fa-lg fa-exclamation-circle'></span> &nbsp;" +  + i18n[key],
        type: type,
        layout: "bottomRight",
        timeout: 1000
    }).show();
}

function failNoty(jqXHR, timeout = 2000) {
    closeNoty();
    if (jqXHR.responseText !== undefined && jqXHR.responseText !== '') {
        var errorInfo = JSON.parse(jqXHR.responseText);
        //errorInfo.details = substitution(errorInfo.details);
        failedNote = new Noty({
            text: "<span class='fa fa-lg fa-exclamation-circle'></span> &nbsp;" + errorInfo.typeMessage + "<br>" + errorInfo.details.join("<br>"),
            type: !Object.is(errorInfo.info, undefined) ? errorInfo.info : "error",
            layout: "bottomRight",
            timeout: timeout
        }).show();
    }
}

function substitution(details) {
    for (let i = 0; i < details.length; i++) {
        let message = details[i];
        let start = message.indexOf('[') + 1;
        let end = message.indexOf(']');
        if (start > 0 && end > 0 ) {
            let name = message.substring(start, end);
            let page = window.location.pathname.replace("/", '');
            page = page === 'main' ? 'clients' : page;
            name = page + "." + name;
            if (!Object.is(i18n[name], undefined)) {
                details[i] = "'" + i18n[name] + "'" + message.substring(end + 1);
            }
        }
    }
    return details;
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