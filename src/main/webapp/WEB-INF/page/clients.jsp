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
                        <div class="card">
                            <div class="card-header">
                                <h5 class="card-title">Клиенты</h5>
                                <h6 class="card-subtitle text-muted">Просмотри и ведение клиентов.</h6>
                            </div>
                            <div class="card-body">
                                <div class="col text-left mb-2">
                                    <!-- параметр data-target ссылается на модальное окно -->
                                    <button class="btn btn-primary" onclick="clientsTable.add()" >Добавить</button>
                                </div>
                                <div class="col">
                                    <table class="dataTables_wrapper dt-bootstrap4 fl-table table-hover" id="dt_clients" style="width: 100%">
                                        <thead>
                                        <tr>
                                            <th scope="col">#</th>
                                            <th scope="col">Имя</th>
                                            <th scope="col">Фамилия</th>
                                            <th scope="col">Отчество</th>
                                            <th scope="col">Номер телефона</th>
                                            <th scope="col">Город</th>
                                            <th scope="col">Email</th>
                                            <th></th>
                                            <th></th>
                                        </tr>
                                        </thead>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <div class="card">
                            <div class="card-header" id="cl_tickets">
                                <button class="btn collapsed btn-custom" data-toggle="collapse" data-target="#collapse_cl_tickets" aria-expanded="false" aria-controls="collapse_cl_tickets">
                                    <h5 class="card-title">Билеты клиента</h5>
                                </button>
                            </div>
                            <!--чтобы скрыть надо убрать класс show-->
                            <div id="collapse_cl_tickets" class="collapse" aria-labelledby="headingOne" data-parent="#cl_tickets">
                                <div class="card-body">
                                    <div class="col text-left mb-2">
                                        <button class="btn btn-primary" id="btn_cl_ticket" onclick="showTickets()" disabled>Добавить билет</button>
                                    </div>
                                    <div class="col">
                                        <jsp:include page="fragments/table/table_client_tickets.jsp"></jsp:include>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <jsp:include page="fragments/modal/create_client.jsp"></jsp:include>
            <jsp:include page="fragments/modal/tickets_list.jsp"></jsp:include>
        </main>
    </div>
</div>

<script src="resources/js/helper/wakepark.const.js"></script>
<script src="resources/js/helper/wakepark.render.js"></script>
<script src="resources/js/helper/wakepark.common.js"></script>
<script src="resources/js/helper/wakepark.clients.js"></script>
<script src="resources/js/helper/wakepark.client.tickets.js"></script>
<script src="resources/js/helper/wakepark.choice.tickets.js"></script>
<script src="resources/js/helper/wakepark.clients.js"></script>
<script src="resources/js/wakepark.page.clients.js"></script>
</body>
</html>