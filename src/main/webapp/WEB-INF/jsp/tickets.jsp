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
            <main class="content">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col">
                            <!-- Фильтр старт -->
                            <div class="card" id="accordion">
                                <div class="card-header" id="headingOne">
                                    <button class="btn collapsed btn-custom" data-toggle="collapse" data-target="#collapseOne" aria-expanded="false" aria-controls="collapseOne">
                                        <h5 class="card-title">Фильтр</h5>
                                    </button>
                                </div>
                                <!--чтобы скрыть надо убрать класс show-->
                                <div id="collapseOne" class="collapse" aria-labelledby="headingOne" data-parent="#accordion">
                                    <div class="card-body">
                                        <form id="filter">
                                            <div class="row">
                                                <div class="col-md-4 text-left">
                                                    <h6>По параметрам:</h6>
                                                    <div class="form-group">
                                                        <div class="row custom-switch custom-control">
                                                            <div class="col-md-12">
                                                                <input type="checkbox" id="filt_active" name="active" class="custom-control-input">
                                                                <label class="custom-control-label text-muted" for="filt_active">Только активные</label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <div class="row custom-switch custom-control">
                                                            <div class="col-md-12">
                                                                <input type="checkbox" id="filt_equipment" name="equipment" class="custom-control-input">
                                                                <label class="custom-control-label text-muted" for="filt_equipment">Только со снаряжением</label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-md-4 text-left">
                                                    <h6>По времени:</h6>
                                                    <div class="form-group">
                                                        <div class="row">
                                                            <div class="col-md-5 mt-auto mb-auto">
                                                                <label class="form-label text-muted m-auto">Начало</label>
                                                            </div>
                                                            <div class="col-md-4">
                                                                <input class="form-control" name="timeStart" type="time">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <div class="row">
                                                            <div class="col-md-5 mt-auto mb-auto">
                                                                <label class="form-label text-muted m-auto">Окончание</label>
                                                            </div>
                                                            <div class="col-md-4">
                                                                <input class="form-control" name="timeEnd" type="time">
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-md-4 text-left">
                                                    <h6>По дате:</h6>
                                                    <div class="form-group">
                                                        <div class="row">
                                                            <div class="col-md-3 mt-auto mb-auto">
                                                                <label class="form-label text-muted m-auto">Начало</label>
                                                            </div>
                                                            <div class="col-md-6">
                                                                <input class="form-control" name="dateStart" type="date">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <div class="row">
                                                            <div class="col-md-3 mt-auto mb-auto">
                                                                <label class="form-label text-muted m-auto">Окончание</label>
                                                            </div>
                                                            <div class="col-md-6">
                                                                <input class="form-control" name="dateEnd" type="date">
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </form>
                                        <div class="col text-left mb-2">
                                            <button class="btn btn-primary" onclick="updateFilteredTable();">Применить</button>
                                            <button class="btn btn-danger"onclick="clearFilter();">Очистить</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- Фильтр конец -->

                            <div class="card">
                                <div class="card-header">
                                    <h5 class="card-title">Билеты</h5>
                                    <h6 class="card-subtitle text-muted">Просмотр и ведение билетов парка</h6>
                                </div>
                                <div class="card-body">
                                    <div class="col text-left mb-2">
                                        <!-- параметр data-target ссылается на модальное окно -->
                                        <button class="btn btn-primary" onclick="ticketsTable.add()">Добавить</button>
                                    </div>
                                    <div class="col">
                                        <jsp:include page="fragments/table/table_tickets.jsp"></jsp:include>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Модальное окно для создания объекта -->
                <div class="modal fade" id="ticketCreate" role="dialog" tabindex="-1" aria-labelledby="ticketTitle" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <form class="" id="ticketForm">
                            <input type="hidden" id="id" name="id"> <!--id-->
                            <input type="hidden" id="year" name="year">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="modalTitle">Добавить билет</h5>
                                    <button type="button" class="close" aria-label="Close" data-dismiss="modal"><span aria-hidden="true" onclick="closeNoty()">×</span></button>
                                </div>
                                <div class="m-3 modal-body">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label class="h6">Тип билета</label>
                                                <select id="select" name="pass" class="form-control">
                                                    <option selected>Разовый</option>
                                                    <option>Абонемент</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label class="h6">Название</label>
                                                <input name="name" type="text" class="form-control">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label class="h6">Параметры</label>
                                                <div class="custom-switch custom-control"><input type="checkbox" id="onlyActive" name="enable" class="custom-control-input"><label class="custom-control-label" for="onlyActive">Билет активен</label></div>
                                                <div class="custom-switch custom-control"><input type="checkbox" id="onlyWithEquipment" name="equipment" class="custom-control-input"><label class="custom-control-label" for="onlyWithEquipment">Снаряжение включено</label></div>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label class="h6">Длительность сета (мин.)</label>
                                                <!--Нужно добавить установку времени для св-ва duration-->
                                                <input id="duration" name="duration" type="number" class="form-control" value = "0" min="0">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label class="h6">Интервал использования</label>
                                                <div class="row">
                                                    <div class="col-sm-5">
                                                        <input name="startTime" type="time" class="form-control pad-rl" placeholder="HH:MM" min="00:00" max="23:59">
                                                    </div>
                                                    <p> - </p>
                                                    <div class="col-sm-5">
                                                        <input name="endTime" type="time" class="form-control pad-rl" placeholder="HH:MM" min="00:00" max="23:59">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label class="h6">Динамическая длительность</label>
                                                <div class="row">
                                                    <div class="row form-group"><label class="text-md-right col-sm-6 col-form-label">Кол-во дней:</label>
                                                        <div class="col-sm-4"><input name="day" type="number" class="form-control" value = "0" min="0">
                                                        </div>
                                                    </div>
                                                    <div class="row form-group"><label class="text-md-right col-sm-6 col-form-label">Кол-во месяцев:</label>
                                                        <div class="col-sm-4"><input name="month" type="number" class="form-control" value = "0" min="0">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label class="h6">Фиксированная длительность</label>
                                                <div class="row">
                                                    <div class="row form-group"><label class="text-md-right col-sm-4 col-form-label">Дата начала:</label>
                                                        <div class="col-sm-7 m-auto">
                                                            <input name="startDate" type="date" class="form-control">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label class="h6">&nbsp;</label>
                                                <div class="row">
                                                    <div class="row form-group"><label class="text-md-right col-sm-5 col-form-label">Дата окончания:</label>
                                                        <div class="col-sm-7 m-auto">
                                                            <input name="endDate" type="date" class="form-control">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label class="h6">Стоимость</label>
                                                <div class="row">
                                                    <div class="row form-group"><label class="text-md-right col-sm-5 col-form-label">В будни</label>
                                                        <div class="col-sm-5 m-auto">
                                                            <input name="cost" type="number" class="form-control" value = "0" min="0">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label class="h6">&nbsp;</label>
                                                <div class="row">
                                                    <div class="row form-group"><label class="text-md-right col-sm-5 col-form-label">В выходные</label>
                                                        <div class="col-sm-5 m-auto">
                                                            <input name="weekendcost" type="number" class="form-control" value = "0" min="0">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-danger" onclick="closeAllModal()">Закрыть</button>
                                    <button type="button" class="btn btn-success" onclick="ticketsTable.save()">Сохранить</button></div>
                            </div>
                        </form>
                    </div>
                </div>
            </main>
        </div>
    </div>

    <script src="resources/js/helper/wakepark.const.js"></script>
    <script src="resources/js/helper/wakepark.render.js"></script>
    <script src="resources/js/helper/wakepark.common.js"></script>
    <script src="resources/js/helper/wakepark.tickets.js"></script>
    <script src="resources/js/wakepark.page.tickets.js"></script>
</body>
</html>