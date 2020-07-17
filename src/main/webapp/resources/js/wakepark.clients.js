var clientAjaxUrl = "/ajax/controller/clients/";
var phone = document.getElementById("telnumber");


$(function () {
        makeEditable({
            datatable_id: '#datatable',
            ajaxUrl: clientAjaxUrl,
            datatableOpts: {
                "columns": [
                    {
                        "data": "id",
                        "orderable": false,
                        "render": function (data, type, row) {
                            return renderRadioId(data, type, row, "cl_select");
                        },
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
            },
            convertModalValue: function(key, value) {
                if (key === 'telnumber') {
                    return  convertPhoneNumber(value);
                }
                return value;
            }
        });
        phoneConvertFunction();
        startModal()
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

function handleOnChange(ev, flag) {
    $('tr input[id^="radio"]')
        .parent().parent().removeClass("tr-hover");
    $('tr input[id="radio'+ ev.value + '"').parent().parent().addClass("tr-hover");
    if (flag) {
        downloadTickets(ev.value);
        $('#collapse_cl_tickets').collapse('show');
    }
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
}