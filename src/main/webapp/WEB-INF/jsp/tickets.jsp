<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <jsp:include page="fragments/head.jsp"></jsp:include>
</head>
<body>
<main class="content">
    <div class="container-fluid">
        <div class="row">
            <div class="col">
                <div class="card">
                    <div class="card-header">
                        <h5 class="card-title">Default</h5>
                        <h6 class="card-subtitle text-muted">Highly flexible tool that many advanced features to any HTML table.</h6>
                    </div>
                    <div class="card-body">
                        <div class="col text-left mb-2">
                            <!-- параметр data-target ссылается на модальное окно -->
                            <button class="btn btn-primary" data-toggle="modal" data-target="#create" onclick="add()">Добавить</button>
                        </div>
                        <div class="col">
                            <table class="dataTables_wrapper dt-bootstrap4 fl-table" id="datatable" style="font-size: 12px">
                                <thead>
                                <tr>
                                    <th scope="col" rowspan="2">Тип билета</th>
                                    <th scope="col" rowspan="2">Название</th>
                                    <th scope="col" rowspan="2" width=5%>Активен</th>
                                    <th scope="col" rowspan="2" width=5%>Снаряжение</th>
                                    <th scope="col" rowspan="2" width=5%>Длит.сета</th>
                                    <th scope="col" colspan="2">Период</th>
                                    <th scope="col" colspan="2">Фикс. длительность</th>
                                    <th scope="col" rowspan="2">Кол-во<br>дней</th>
                                    <th scope="col" rowspan="2">Кол-во<br>месяцев</th>
                                    <th scope="col" rowspan="2">Стоимость<br>в будни</th>
                                    <th scope="col" rowspan="2">Стоимость<br>в выходные</th>
                                    <th scope="col" rowspan="2"></th>
                                    <th scope="col" rowspan="2"></th>
                                </tr>
                                <tr>
                                    <th width=5%>С</th>
                                    <th width=5%>По</th>
                                    <th width=5% nowrap>Дата начала</th>
                                    <th width=9% nowrap>Дата окончания</th>
                                </tr>
                                </thead>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Модальное окно для создания объекта -->
    <div class="modal fade" id="create" role="dialog" tabindex="-1" aria-labelledby="modalTitle" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <form class="" id="detailsForm">
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
                                    <div class="custom-switch custom-control"><input type="checkbox" id="onlyActive" name="enable" class="custom-control-input" checked><label class="custom-control-label" for="onlyActive">Билет активен</label></div>
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
                        <button type="button" class="btn btn-danger" data-dismiss="modal" onclick="closeNoty()">Закрыть</button>
                        <button type="button" class="btn btn-success" onclick="save()">Сохранить</button></div>
                </div>
            </form>
        </div>
    </div>
</main>
<script src="resources/js/wakepark.tickets.js" defer></script>
<script src="resources/js/wakepark.common.js" defer></script>
</body>
</html>
