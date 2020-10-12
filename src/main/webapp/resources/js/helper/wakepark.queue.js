var queueAjaxUrl = "/ajax/controller/queue/";

var queueTable = {
    datatable_id: '#dt_queue',
    ajaxUrl: queueAjaxUrl,
    datatableOpts: {
        "columns": [
            {
                "data": "up",
                "render": function (data, type, row) {
                    return renderDirection(data, type, row, "up-28", MoveRow.UP);
                },
                "defaultContent": ""
            },
            {
                "data": "down",
                "render": function (data, type, row) {
                    return renderDirection(data, type, row, "down-28", MoveRow.DOWN);
                },
                "defaultContent": ""
            },
            {
                "data": "control",
                "render": function (data, type, row) {
                    var img = '';
                    switch (MoveRow[data]) {
                        case MoveRow.PAUSE: img = "pause-28";
                            break;
                        case MoveRow.PLAY: img = "play-28";
                            break;
                    }
                    return renderDirection(data, type, row, img, MoveRow[data]);
                },
                "defaultContent": ""
            },
            {
                "data": "delete",
                "render": function (data, type, row) {
                    return renderDirection(data, type, row, "delete-28", MoveRow.DELETE);
                },
                "defaultContent": ""
            },
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
    },
    datatableParam: {
        "paging": false,
        "searching": false,
        "ordering":  false,
        "ajax": {
            "url": queueAjaxUrl,
            "dataSrc": ''
        }
    },
    webSocket: new wsQueue()
};

function addInQueue() {
    var ticketId = getSingleTrValue('ticketId', 'radio');
    var count = getSingleTrValue('count');
    var clientId = $('#cl_id')[0].value;
    if (ticketId === null) {
        failNotyWithText("Выберите билет");
    } else {
        $.ajax({type: "POST", url: queueAjaxUrl, data: {"client_id" : clientId, "ticket_id" : ticketId, "count" : count}})
            .done(function () {
                successNoty('queue.add.success');
                queueTable.updateTable();
                closeAddInQueueModal();
            }).fail(function () {
            failNotyByKey('queue.add.error');
            closeAddInQueueModal();
        });
    }
}

function closeAddInQueueModal() {
    $('#cl_id')[0].value = '';
    closeAllModal();
    removeField('search_telnumber', clearClientValue());
}

function renderDirection(data, type, row, imgname, move = null) {
    var img = 'resources/img/' + imgname + '.png';
    var disabled = "style='opacity:0.5' disabled";
    var oncl = '';
    if (!row.disControl && (typeof data!== "undefined" || ( row.upOrDown === MoveRow.ALL || move === row.upOrDown || move === MoveRow.DELETE ))) {
        disabled = "onmouseover='imgover(this)' onmouseout='imgout(this)'";

        if (!Object.is(move, null)) {
            oncl = "imgclick("+row.id+",'"+move.toUpperCase()+"')";
        }
    }

    return "<div class='row'>" +
            " <span style='overflow: visible; position: relative;'>" +
            "   <a title='Изменить последовательность' class='btn btn-sm btn-clean btn-icon btn-icon-md'> " +
            "       <img id='#' src='" + img + "' " + disabled + " onclick="+oncl+" />" +
            "   </a>" +
            " </span>" +
            "</div>"
}

function imgover(img) {
    img.src = img.src.replace('.png', '-hover.png');
}

function imgout(img) {
    img.src = img.src.replace('-hover.png', '.png');
}

function imgclick(id, move = MoveRow.UP) {
    if (typeof id === 'undefined' || id === null) {
        failNotyWithText("Ошибка при изменении очереди");
    }
    $.get(queueAjaxUrl + "control", {'move': move, 'ctId' : id}).done(function () {
        queueTable.updateTable();
        var text = '';
        switch (move) {
            case "up":
            case "down":
                text = "Очередь изменена";
                break;
            case "delete":
                text = "Пользователь удален из очереди";
                break;
            case "play":
                text = "Пользователь возвращен в очередь";
                break;
            case "pause":
                text = "Пользователь удален из активной очереди";
        }
        if (text!=='')
            successNoty(text);
    })
}

function queueClick(control = '') {
    // switch (control) {
    //     case QueueControl.PAUSE:
    //         pauseTimer();
    //         break;
    //     case QueueControl.PLAY:
    //         //startTimer();
    //         break;
    //     case QueueControl.STOP:
    //         stopTimer();
    //         break;
    //     default:
    //         return false;
    // }

    queueTable.webSocket.send(JSON.stringify({'state' : control}));
    // $.get(urlGetQueue, {'control' : control}).done(function () {
    //     queueTable.updateTable();
    // })
}

function wsQueue() {

    var stompClient = null;

    this.start = function() {
        stompClient = Stomp.over(new SockJS('/event'));
        stompClient.connect({}, function(frame) {
            console.log('Connected: ' + frame);

            stompClient.subscribe('/topic/state', function(messageOutput) {
                var data = JSON.parse(messageOutput.body);
                updateTimer(data);
            });

            stompClient.subscribe('/topic/queue', function (messageOutput) {
                var data = JSON.parse(messageOutput.body);
                queueTable.updateTableByData(data);
            })
        });
    };

    this.stop = function () {
        if(stompClient != null) {
            stompClient.disconnect();
        }
        console.log("Disconnected");
    };

    this.send = function (data) {
        stompClient.send("/app/event", {}, data);
    }
}