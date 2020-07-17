<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <jsp:include page="fragments/head.jsp"></jsp:include>
</head>
<body>
<div class="container">
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
                            <c:set var="instance" value="${thead}" scope="request"/>
                            <jsp:include page="fragments/table.jsp"/>
                        </div>
                    </div>
                    <jsp:include page="fragments/client_tickets.jsp"/>
                </div>
            </div>
        </div>
        <!-- Модальное окно для создания объекта -->
        <div class="modal fade" id="create" role="dialog" tabindex="-1" aria-labelledby="modalTitle" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <form class="" id="detailsForm">
                    <input type="hidden" id="id" name="id">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="modalTitle">Добавить клиента</h5>
                            <button type="button" class="close" aria-label="Close" data-dismiss="modal"><span aria-hidden="true" onclick="closeNoty()">×</span></button>
                        </div>
                        <div class="m-3 modal-body">
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="h6">Номер телефона</label>
                                        <div class="input-group mb-3">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text">+7</span>
                                            </div>
                                            <input type="text" id="telnumber" name="telnumber" class="form-control" onkeyup="return checkPhoneKey(key)" onblur="phoneblur()" placeholder="(9XX) XXX-XX-XX">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="h6">Город</label>
                                        <input name="city" id="city" type="text" class="form-control">
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="h6">Фамилия</label>
                                        <input name="lastname" type="text" id="lastname" class="form-control">
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="h6">Имя</label>
                                        <input name="firstname" type="text" id="firstname" class="form-control">
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="h6">Отчество</label>
                                        <input name="middlename" type="text" id="middlename" class="form-control">
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="h6">Почта</label>
                                        <div class="mb-3 input-group"><div class="input-group-prepend">
                                            <span class="input-group-text">@</span>
                                        </div>
                                            <input name="email" placeholder="test@test.ru" type="text" class="form-control"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-danger" data-dismiss="modal" onclick="closeNoty()">Закрыть</button>
                            <button type="button" class="btn btn-primary" onclick="save()">Сохранить</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <jsp:include page="fragments/choice_ticket.jsp"/>
    </main>
</div>

<script src="resources/js/wakepark.const.js"></script>
<script src="resources/js/wakepark.common_render.js"></script>
<script src="resources/js/wakepark.common.js" defer></script>

<script src="resources/js/wakepark.clients.js" defer></script>
<script src="resources/js/wakepark.ch_ticket.js" defer></script>
<script src="resources/js/wakepark.client_tickets.js"></script>
</body>
</html>
