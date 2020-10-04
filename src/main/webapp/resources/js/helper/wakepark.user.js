var userAjaxUrl = "/ajax/controller/users/";

var usersTable = {
    datatable_id: '#dt_users',
    ajaxUrl: userAjaxUrl,
    form:   $('#userForm'),
    create: $('#userCreate'),
    title:  $("#userTitle"),
    datatableOpts: {
        "columns": [
            {
                "data": "avatar",
                "orderable": false,
                "render": function (data, type, row) {
                    if (!Object.is(data, null) && !Object.is(data, undefined)) {
                        return "<img src='"+ data +"'/>";
                    }
                    return data;
                },
                "defaultContent": ""
            },
            // {"data": "id",
            //     "orderable": false,
            //     "render": function (data, type, row) {
            //         return renderRadioId(data, type, row, "us_select");
            //     },
            //     "defaultContent": ""
            // },
            {"data": "firstname"},
            {"data": "lastname"},
            {
                "data": "middlename",
                "defaultContent": ""
            },
            {"data": "telnumber"},
            {"data": "login"},
            {
                "data": "password",
                "render": renderPassword,
                "defaultContent": ""
            },
            {
                "data": "email",
                "defaultContent": ""
            },
            {
                "data": "enabled",
                "render": function (data, type, row) {
                    return renderCheckBox(data, type, row, false, "clickOnCheckbox(this,"+row.id+")")
                },
                "defaultContent": ""
            },
            {
                "data": "roles",
                "render": getRoleCode
            },
            {
                "render": function (data, type, row) {
                    if (type === "display") {
                        return "<div class='row m-auto'><div class='col-2'><a onclick='usersTable.updateRow(" + row.id + ");'><span class='fa fa-pencil'></span></a></div>" +
                            "<div class='col-2'><a onclick='usersTable.deleteRow(" + row.id + ");'><span class='fa fa-trash-o'></span></a> </div> </div>"
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
            "url": userAjaxUrl,
            "dataSrc": '',
            "error": function (xhr, error, thrown) {
                this.datatableApi.draw(); // в случае пустых данных, чистим таблицу
            }
        }
    },

    convertModalValue: function(key, value) {
        if (key === 'telnumber' && value.startsWith('+7')) {
            value = value.replace('+7', '');
        }
        if (key === 'avatar') {
            let group = $('.btn-group button');
            group.removeClass('btn-outline-active');
            group.find('img').each(function (im) {
                if (this.src === value) {
                    this.parentElement.classList.add('btn-outline-active');
                }
            })
        }
        return value;
    },

    defModalValue: function (form) {
        form.find('#roles').val('USER');
        let group = $('.btn-group button');
        group.removeClass('btn-outline-active');
        group[0].classList.add('btn-outline-active');

    },

    presavedata: function (mas) {
        for (var i = 0; i < mas.length; i++) {
            if (mas[i].name === 'telnumber') {
                mas[i].value = mas[i].value.startsWith('+7') ? mas[i].value : '+7' + mas[i].value;
            }
        }
        var avatar = $('.btn-group .btn-outline-active').find('img').attr('src');
        mas.push({name: "avatar", value: avatar});
    }
};

function handleOnChange(ev, flag) {
    $('tr input[id^="radio"]')
        .parent().parent().removeClass('active');
    $(ev).parent().parent().addClass('active');
}

function clickOnCheckbox(element, id) {
    let active = element.checked;
    $.get(userAjaxUrl + id, function (data) {
        data.enabled = active;
        $.ajax({ type: "POST", url: userAjaxUrl, data: data }).done(function () {
            closeAllModal();
            successNoty("common.saved");
        }).always(function () {
            usersTable.updateTable();
        });
    });
}