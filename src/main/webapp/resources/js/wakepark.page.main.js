var btnRegistration = $('#registration');
var btnAddQueue = $('#add_in_queue');
var btnTickets = $('#tickets');
var fieldTelnumber = $('#search_telnumber'); //для страницы main
var urlGetQueue = "/queue";

$(function () {
    clientsTable.postSave = function() {
        var phone = this.form.find('#telnumber').val();
        fieldTelnumber.val(phone);
        clearClientValue();
        getClientForRegistrationForm('+7' + phone);
    };

    clTickets.datatableOpts = {
        "columns": [
            {
                "data": "ticketId",
                "render": function (data, type, row) {
                    return renderRadioId(data, type, row, 'ticketId', false);
                },
                "defaultContent": ""
            },
            {"data": "pass"},
            {"data": "name"},
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
            { "data": "day" },
            { "data": "month" },
            { "data": "duration" },
            {
                "data": "count",
                "render": function (data, type, row) {
                    return renderCount(data, type, row, row.countEdit, row.count);
                },
                "defaultContent": ""
            }

        ]
    };
    clTickets.datatableParam = {
        "info": false,
        "paging": false,
        "searching": false,
            "ajax": {
            "url": clTickets.ajaxUrl,
                "dataSrc": '',
                "error": function (xhr, error, thrown) {
                clTickets.datatableApi.draw(); // в случае пустых данных, чистим таблицу
            }
        }
    };

    ticketsTable.postSave = function(clientId) {
        $('.modal').modal('hide');
        updateTableByCurrentTime(clientId, $('#filt_actual').prop('checked'));
        $('#cl_ticket').modal();
    };

    makeEditable(clientsTable, false);
    makeEditable(clTickets, false);
    makeEditable(ticketsTable, false);
    makeEditable(queueTable);
    //обновляем статус таймера
    updateTimerState();

    //queu = new loadQueue();
    //wsQuqu = new wsQueue();

    window.onload = function() {
        //queu.start();
        queueTable.webSocket.start('/topic/queue', function (messageOutput) {
                var data = JSON.parse(messageOutput.body);
                queueTable.updateTableByData(data);
            });
    };

    window.onunload = function(event) { //выполняется при закрытии страницы
        //event.preventDefault();
        queueTable.webSocket.stop();
        $.ajax({url: '/logout', type: 'POST'});
    };
    // window.onbeforeunload = function(event) {
    //     event.preventDefault();
    //     //queu.stop();
    //     //wsQuqu.stop();
    //     queueTable.webSocket.stop();
    //     event.returnValue = 'Что то пишем здесь'; // Chrome requires returnValue to be set
    //     return;
    // };

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(jqXHR, 1000);
    });

    // solve problem with cache in IE: https://stackoverflow.com/a/4303862/548473
    $.ajaxSetup({cache: false});
});

btnRegistration.click(function (event) {
    event.preventDefault();
    clientsTable.add();
    //$('#create').modal();
    $('#telnumber')[0].value = fieldTelnumber.val();
});

btnAddQueue.click(function (event) {
    event.preventDefault();
    var id = $('#client_id')[0].value;
    if (typeof id !== "undefined" && id.length > 0) {
        updateClTickectTable();
        $('#cl_id')[0].value = id;
        $('#cl_ticket').modal();
    }
});

function updateClTickectTable() {
    var currentDate = new Date();
    var filter = 'startTime=' + leadZero(currentDate.getHours()) + ':' + leadZero(currentDate.getMinutes());
    clTickets.updateTableWithParam($('#client_id')[0].value, $('#filt_actual').prop('checked'), filter)
}

//для страницы main
fieldTelnumber.keypress(function (evt) {
    return checkPhoneKey(evt.key, this.id);
});

fieldTelnumber.click(function (evt) {
    if (this.value.length === 0) { this.value = "("; }
});

fieldTelnumber.keyup(function (evt) {
    this.value = convertPhoneNumber(this.value);
    clearClientValue();
    if (this.value.length === 15) {
        var telnumber = '+7' + this.value;
        getClientForRegistrationForm(telnumber);
    }
});

btnTickets.click(function (evt) {
    evt.preventDefault();
    ticketsTable.updateTable();
    closeAllModal();
    $('#choice_ticket').modal();
});

function addClientTickets() {
    var clientId = $('#client_id')[0].value;
    saveClTicket(clientId);
    $('.modal').modal('hide');
    updateTableByCurrentTime(clientId, $('#filt_actual').prop('checked'));
    $('#cl_ticket').modal();
}

function getClientForRegistrationForm(phone) {
    $.get(clientAjaxUrl + "filter", {"telnumber": phone})
        .done(function (data) {
            btnAddQueue.toggleClass("d-none");
            btnRegistration.toggleClass("d-none");
            $('#client_id')[0].value = data.id;
            $('#fr_lastname')[0].value = data.lastname;
            $('#fr_firstname')[0].value = data.firstname;
            $('#fr_middlename')[0].value = data.middlename;
        }).fail(function (data) {
        clearClientValue();
    });
}

function clearClientValue() {
    $('#fr_lastname')[0].value='';
    $('#fr_firstname')[0].value='';
    $('#fr_middlename')[0].value='';
    $('#client_id')[0].value = '';
    btnAddQueue.addClass("d-none");
    btnRegistration.removeClass("d-none");
}

function loadQueue() {
    this.source = null;
    this.uuid = Date.now();
    url = urlGetQueue + "/stream?uuid=" + this.uuid;
    this.start = function () {
        this.source = new EventSource(url);

        this.source.addEventListener("message", function (event) {
            var data = JSON.parse(event.data);
            updateTimerState(); //обновляем таймер
            queueTable.updateTableByData(data);
        });
        this.source.onerror = function () {
            this.close();
        };
    };
    this.stop = function() {
        $.get(urlGetQueue + "/stream/remove?uuid=" + this.uuid);
        this.source.close();
    }
}
