var clientAjaxUrl = "/wakepark/ajax/controller/clients/";
var phone = document.getElementById("telnumber");

$(function () {
        makeEditable({
            ajaxUrl: clientAjaxUrl,
            datatableOpts: {
                "columns": [
                    {
                        "data": "id",
                        "orderable": false,
                        "render": renderRadioId,
                        "defaultContent": ""
                    },
                    {
                        "data": "firstname",
                        "render": renderLabelRadio,
                        "defaultContent": ""
                    },
                    {
                        "data": "lastname",
                        "render": renderLabelRadio,
                        "defaultContent": ""
                    },
                    {
                        "data": "middlename",
                        "render": renderLabelRadio,
                        "defaultContent": ""
                    },
                    {
                        "data": "telnumber",
                        "render": renderLabelRadio,
                        "defaultContent": ""
                    },
                    {
                        "data": "city",
                        "render": renderLabelRadio,
                        "defaultContent": ""
                    },
                    {
                        "data": "email",
                        "render": renderLabelRadio,
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
            updateTable: function () {
                $.get(clientAjaxUrl, updateTableByData);
            }
        });
        datatableCustom();
        phoneConvertFunction();
        radioEvent();
    }
);

function phoneConvertFunction() {
    if (!Object.is(phone, null)) {
        phone.addEventListener('click', function(evt){
            if (this.value.length === 0) {
                this.value = "(";
            }
        });

        phone.addEventListener('keyup', function(evt) {
            this.value = convertPhoneNumber(this.value);
        });
    }
}

function radioEvent() {

}


var phoneblur = function () {
    if(phone.value === "(") {
        phone.value = "";
    }
};


var checkPhoneKey = function (key) {
    console.log(key);
    return (key >= '0' && key <= '9' && phone.value.length < 17) ||
        key === 'ArrowLeft' || key === 'ArrowRight' || key === 'Delete' || key === 'Backspace';
};

function renderRadioId(data, type, row) {
    if (type === "display") {
        return "<input type='radio' id='radio"+ row.id +"' value='"+ data +"' name='select'>";
    }
}

function renderLabelRadio(data, type, row) {
    if (type === "display") {
        if (data === undefined) {
            return null;
        }
        return "<label for='radio"+ row.id +"'>"+ data +"</label>"
    }
}