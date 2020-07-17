var ticketAjaxUrl = "/ajax/controller/tickets/";
var buttons = document.querySelectorAll(".btn-inactive");
var duration = $('#duration');

$(function () {
        makeEditable({
            datatable_id: '#datatable',
            ajaxUrl: ticketAjaxUrl,
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
                $.get(ticketAjaxUrl, updateTableByData);
            },
            // synchronyzeButtons: function (value) {
            //     clearClass(buttons, 'btn-secondary', 'btn-success');
            //     for (let button of buttons) {
            //         if (button.outerText===String(value)) {
            //             button.classList.add('btn-success');
            //         }
            //     }
            // },
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
            defModalValue: function (form) {
                form.find('#duration').val("5");
                form.find('#select').val("Разовый");
                form.find("input[name='day']").val(0);
                form.find("input[name='month']").val(0);
                form.find('#year').val(new Date().getFullYear());
            }
        });
        datatableCustomStyle('#datatable');
        //modalButtonEvent(); блок кнопок для установки продолжительности. Исключили.
    }
);

// function renderCheckBox(data, type, row) {
//     if (type === "display") {
//         var check = (data === true) ? 'checked' : '';
//         return "<div class='custom-control custom-checkbox'>" +
//                     "<input type='checkbox' class='custom-control-input' disabled " + check +">" +
//                     "<span class='custom-control-label'>" +
//                 "</span></div>";
//     }
// }
//
// function modalButtonEvent() {
//     for (let button of buttons) {
//         button.addEventListener("click", function (evt) {
//             // Отменяем действие по умолчанию
//             evt.preventDefault();
//             clearClass(buttons, 'btn-secondary', 'btn-success');
//             this.classList.add('btn-success');
//             duration.val(this.outerText);
//         });
//     }
// }

var clearClass = function(elements, addClass, remClass) {
    for(let element of elements) {
        element.classList.remove(remClass);
        element.classList.add(addClass);
    }
};