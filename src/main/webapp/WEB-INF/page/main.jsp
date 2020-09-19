<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <jsp:include page="fragments/head.jsp"></jsp:include>
</head>
<body>
<div class="wrapper">
    <jsp:include page="fragments/sidebar.jsp"></jsp:include>
    <div class="main">
        <jsp:include page="fragments/navbar.jsp"></jsp:include>
        <main class="content" style="font-family:Jost,-apple-system,BlinkMacSystemFont,Segoe UI,Helvetica Neue,Arial,sans-serif">
            <div class="container-fluid">
                <div class="p-0 container-fluid">
                    <div class="row">
                        <div class="col-lg-4">
                            <div class="flex-fill mb-3 card">
                                <div class="card-header">
                                    <h5 class="mb-0 card-title">Регистрация клиента</h5>
                                </div>
                                <div class="d-flex card-body">
                                    <form style="width: 95%">
                                        <input type="text" id="client_id" disabled class="d-none">
                                        <div class="form-group row">
                                            <label class="col-form-label col-sm-5 text-sm-right text-muted">Телефон клиента:</label>
                                            <div class="col-sm-7 input-group">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text">+7</span>
                                                </div>
                                                <input type="text" id="search_telnumber" name="telnumber" class="form-control" onblur="phoneblur(this.id)" placeholder="(9XX) XXX-XX-XX">
                                                <span class="input-group-append">
                                            <button class="btn btn-outline-info" onclick="removeField('search_telnumber', clearClientValue())" type="button">X</button>
                                        </span>
                                            </div>
                                        </div>
                                        <div class="form-group row">
                                            <label class="col-form-label col-sm-5 text-sm-right text-muted">Фамилия:</label>
                                            <div class="col-sm-7">
                                                <input name="lastname" id="fr_lastname" class="form-control" placeholder="Фамилия" disabled>
                                            </div>
                                        </div>
                                        <div class="form-group row">
                                            <label class="col-form-label col-sm-5 text-sm-right text-muted">Имя:</label>
                                            <div class="col-sm-7">
                                                <input name="firstname" id="fr_firstname" class="form-control" placeholder="Имя" disabled>
                                            </div>
                                        </div>
                                        <div class="form-group row">
                                            <label class="col-form-label col-sm-5 text-sm-right text-muted">Отчество:</label>
                                            <div class="col-sm-7">
                                                <input name="middlename" id="fr_middlename" class="form-control" placeholder="Отчество" disabled>
                                            </div>
                                        </div>
                                        <div class="mb-3 text-center">
                                            <button class="btn btn-success d-none" id="add_in_queue">Добавить в очередь</button>
                                            <button class="btn btn-success" id="registration">Зарегистрировать</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                            <div class="flex-fill mb-3 card">
                                <div class="card-header">
                                    <h5 class="mb-0 card-title">Задачи на текущий день</h5>
                                </div>
                                <div class="d-flex card-body">
                                    <div class="align-self-center w-100">
                                        <ul class="timeline mt-2">
                                            <li class="timeline-item"><strong>Открытие парка</strong><span class="float-right text-muted text-sm">09:00</span>
                                                <p>Проверка инвентаря, уборка, запуск лебедки.</p>
                                            </li>
                                            <li class="timeline-item"><strong>Технический перерыв</strong><span class="float-right text-muted text-sm">11:45 -12:00</span>
                                                <p>Остановка лебедки. Выполнение плановых проверок.</p>
                                            </li>
                                            <li class="timeline-item"><strong>Перерыв</strong><span class="float-right text-muted text-sm">13:30 - 14:00</span>
                                                <p>Остановка лебедки. Пересменка.</p>
                                            </li>
                                            <li class="timeline-item"><strong>Технический перерыв</strong><span class="float-right text-muted text-sm">16:45 - 17:00</span>
                                                <p>Остановка лебедки. Выполнение плановых проверок.</p>
                                            </li>
                                            <li class="timeline-item"><strong>Закрытие парка</strong><span class="float-right text-muted text-sm">20:00</span>
                                                <p>Остановка лебедки, выполнение плановых проверок, закрытие парка.</p>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-8">
                            <div class="flex-fill w-100 card">
                                <div class="card-header">
                                    <h3>Активная очередь</h3>
                                </div>
                                <div class="d-flex card-body">
                                    <table class="my-0 table table-striped" id="dt_queue" style="width: 100%">
                                        <thead>
                                        <tr>
                                            <th width="32px" scope="col">Вв</th>
                                            <th width="32px" scope="col">Вн</th>
                                            <th width="32px" scope="col">Ост</th>
                                            <th width="32px" scope="col">Уд</th>
                                            <th scope="col">Очередь</th>
                                            <th width="10%" scope="col">Кол-во сетов</th>
                                        </tr>
                                        </thead>
                                    </table>
                                </div>
                            </div>
                            <div class="w-100 card">
                                <div class="card-body">
                                    <div class="row justify-content-center">
                                        <div class="col col-md-auto align-self-center d-none" id="update">
                                            <img class="rot" src="resources/img/update-48.png">
                                        </div>
                                        <div class="col col-md-auto align-self-center" id="play">
                                            <img src="resources/img/play-48.png"
                                                 onmouseover="this.src='resources/img/play-48-hover.png'"
                                                 onmouseout="this.src='resources/img/play-48.png'" onclick="queueClick(QueueControl.PLAY)"/>
                                        </div>
                                        <div class="col col-md-auto align-self-center d-none" id="pause">
                                            <img src="resources/img/pause-48.png"
                                                 onmouseover="this.src='resources/img/pause-48-hover.png'"
                                                 onmouseout="this.src='resources/img/pause-48.png'" onclick="queueClick(QueueControl.PAUSE)"/>
                                        </div>
                                        <div class="col col-md-auto align-self-center">
                                            <img src="resources/img/stop-48.png"
                                                 onmouseover="this.src='resources/img/stop-48-hover.png'"
                                                 onmouseout="this.src='resources/img/stop-48.png'" onclick="queueClick(QueueControl.STOP)" />
                                        </div>
                                        <div class="col col-md-auto align-self-center">
                                            <div class="timer_panel_nums">
                                                <!-- минуты… -->
                                                <span class="timer_nums minutes" >00</span>
                                                <span>:</span>
                                                <!-- …и секунды -->
                                                <span class="timer_nums seconds" >00</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row justify-content-center">
                                        <div class="col col-md-auto align-self-center">
                                            <h5>Длительность очереди <span class="timer_common minutes" >00</span>
                                                <span>:</span>
                                                <span class="timer_common seconds" >00</span>
                                            </h5>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <jsp:include page="fragments/modal/create_client.jsp"></jsp:include>
            <!--Модальное окно для билетов клиента-->
            <div class="modal fade" id="cl_ticket" role="dialog" tabindex="-1" aria-labelledby="modalTitle" aria-hidden="true">
                <div class="modal-dialog modal-xl" role="document">
                    <form class="" id="cl_ticket_form">
                        <input type="hidden" id="cl_id" name="id">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title">Билеты клиента</h5>
                                <button type="button" class="close" aria-label="Close" data-dismiss="modal"><span aria-hidden="true" onclick="closeAddInQueueModal()">×</span></button>
                            </div>
                            <div class="m-3 modal-body">
                                <div class="row">
                                    <div class="col">
                                        <div class="row custom-switch custom-control">
                                            <div class="col-md-12">
                                                <input type="checkbox" id="filt_actual" name="filt_actual" class="custom-control-input" checked onclick="updateClTickectTable()">
                                                <label class="custom-control-label text-muted" for="filt_actual">Актуальные по интервалу</label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col">
                                        <jsp:include page="fragments/table/table_client_tickets.jsp"></jsp:include>
                                        <button class="btn btn-primary" id="tickets">Добавить билет</button>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-danger" data-dismiss="modal" onclick="closeAddInQueueModal()">Закрыть</button>
                                <button type="button" class="btn btn-primary" onclick="addInQueue()">Добавить</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>

            <!--Модальное окно со списком доступных билетов из фрагмента choice_ticket-->
            <jsp:include page="fragments/modal/tickets_list.jsp"></jsp:include>
        </main>
    </div>
</div>

<!--WebSocket-->
<script src="resources/js/websocket/sockjs-0.3.4.js"></script>
<script src="resources/js/websocket/stomp.js"></script>

<script src="resources/js/helper/wakepark.const.js"></script>
<script src="resources/js/helper/wakepark.render.js"></script>
<script src="resources/js/helper/wakepark.common.js"></script>
<script src="resources/js/helper/wakepark.clients.js"></script>
<script src="resources/js/helper/wakepark.client.tickets.js"></script>
<script src="resources/js/helper/wakepark.choice.tickets.js"></script>
<script src="resources/js/helper/wakepark.timer.js"></script>
<script src="resources/js/helper/wakepark.queue.js"></script>

<script src="resources/js/wakepark.page.main.js"></script>
</body>
</html>