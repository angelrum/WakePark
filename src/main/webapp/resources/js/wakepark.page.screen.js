
$(function () {
    queueTable.ajaxUrl = window.location.origin + queueAjaxUrl;
    queueTable.datatableOpts = {
        "columns": [
            {"data": "name"},
            {
                "data": "count",
                "render": function (data, type, row) {
                    if (row.pass==='SEASON') {
                        return i18n['ticket.pass.season'] }
                    return data;
                },
                "defaultContent": ""
            }
            ]
    };
    makeEditable(queueTable);

    //обновляем статус таймера
    updateTimerState();

    window.onload = function() {
        //queu.start();
        queueTable.webSocket.start('/topic/queue/active', function (messageOutput) {
            var data = JSON.parse(messageOutput.body);
            queueTable.updateTableByData(data);
        });
    };

    window.onunload = function(event) { //выполняется при закрытии страницы
        //event.preventDefault();
        queueTable.webSocket.stop();
        //$.ajax({url: '/logout', type: 'POST'});
    };

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(jqXHR, 1000);
    });

    // solve problem with cache in IE: https://stackoverflow.com/a/4303862/548473
    $.ajaxSetup({cache: false});
});