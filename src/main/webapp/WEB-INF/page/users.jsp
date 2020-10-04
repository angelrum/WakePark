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
    <jsp:include page="fragments/sidebar.jsp">
        <jsp:param name="page" value="user"/>
    </jsp:include>
    <div class="main">
        <jsp:include page="fragments/navbar.jsp"></jsp:include>
        <main class="content">
            <div class="container-fluid">
                <div class="row">
                    <div class="col">
                        <div class="card">
                            <div class="card-header">
                                <h5 class="card-title"><spring:message code="user.title"/></h5>
                                <h6 class="card-subtitle text-muted"><spring:message code="user.subtitle"/></h6>
                            </div>
                            <div class="card-body">
                                <div class="col text-left mb-2">
                                    <!-- параметр data-target ссылается на модальное окно -->
                                    <button class="btn btn-primary" onclick="usersTable.add()">Добавить</button>
                                </div>
                                <div class="col">
                                    <table class="dataTables_wrapper dt-bootstrap4 my-table table-hover" id="dt_users">
                                        <thead>
                                        <tr>
                                            <th scope="col"><spring:message code="user.avatar"/></th>
                                            <th scope="col"><spring:message code="user.firstname"/></th>
                                            <th scope="col"><spring:message code="user.lastname"/></th>
                                            <th scope="col"><spring:message code="user.middlename"/></th>
                                            <th scope="col"><spring:message code="user.telnumber"/></th>
                                            <th scope="col"><spring:message code="user.login"/></th>
                                            <th scope="col"><spring:message code="user.password"/></th>
                                            <th scope="col"><spring:message code="user.email"/></th>
                                            <th scope="col"><spring:message code="user.enabled"/></th>
                                            <th scope="col"><spring:message code="user.role"/></th>
                                            <th scope="col" width="60px"></th>
                                        </tr>
                                        </thead>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Модальное окно для создания объекта -->
                <div class="modal fade" id="userCreate" role="dialog" tabindex="-1" aria-labelledby="userTitle" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <form class="" id="userForm">
                            <input type="hidden" id="id" name="id">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="modalTitle"></h5>
                                    <button type="button" class="close" aria-label="Close" data-dismiss="modal"><span aria-hidden="true" onclick="closeAllModal()">×</span></button>
                                </div>
                                <div class="m-3 modal-body">
                                    <div class="row">
                                        <div class="btn-group btn-group-lg mb-3 scrolling-wrapper" role="group">
                                            <button type="button" class="btn btn-outline-success btn-outline-active"><img src="https://img.icons8.com/bubbles/50/000000/gender-neutral-user.png"/></button>
                                            <button type="button" class="btn btn-outline-success"><img src="https://img.icons8.com/bubbles/50/000000/student-male.png"/></button>
                                            <button type="button" class="btn btn-outline-success"><img src="https://img.icons8.com/bubbles/50/000000/user-male.png"/></button>
                                            <button type="button" class="btn btn-outline-success"><img src="https://img.icons8.com/bubbles/50/000000/administrator-male.png"/></button>
                                            <button type="button" class="btn btn-outline-success"><img src="https://img.icons8.com/bubbles/50/000000/ginger-man-in-yellow-shirt.png"/></button>
                                            <button type="button" class="btn btn-outline-success"><img src="https://img.icons8.com/bubbles/50/000000/blond-long-hair-business-lady.png"/></button>
                                            <button type="button" class="btn btn-outline-success"><img src="https://img.icons8.com/bubbles/50/000000/blond-curly-hair-business-lady.png"/></button>
                                            <button type="button" class="btn btn-outline-success"><img src="https://img.icons8.com/bubbles/50/000000/brown-curly-hair-lady-in-green-shirt.png"/></button>
                                            <button type="button" class="btn btn-outline-success"><img src="https://img.icons8.com/bubbles/50/000000/short-hair-lady-in-blue-shirt.png"/></button>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label class="h6"><spring:message code="user.login"/></label>
                                                <input name="login" type="text" id="login" class="form-control">
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label class="h6"><spring:message code="user.password"/></label>
                                                <input name="password" type="password" id="password" class="form-control">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label class="h6"><spring:message code="user.telnumber"/></label>
                                                <div class="input-group mb-3">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text">+7</span>
                                                    </div>
                                                    <input type="text" id="telnumber" name="telnumber" class="form-control" onblur="phoneblur(this.id)" placeholder="(9XX) XXX-XX-XX">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label class="h6"><spring:message code="user.email"/></label>
                                                <div class="mb-3 input-group">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text">@</span>
                                                    </div>
                                                    <input name="email" placeholder="test@test.ru" type="text" class="form-control">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label class="h6"><spring:message code="user.lastname"/></label>
                                                <input name="lastname" type="text" id="lastname" class="form-control">
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label class="h6"><spring:message code="user.firstname"/></label>
                                                <input name="firstname" type="text" id="firstname" class="form-control">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label class="h6"><spring:message code="user.middlename"/></label>
                                                <input name="middlename" type="text" id="middlename" class="form-control">
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label class="h6"><spring:message code="user.role"/></label>
                                                <select name="roles" id="roles" class="form-control">
                                                    <option value="MANAGER"><spring:message code="user.role.manager"/></option>
                                                    <option value="ADMIN"><spring:message code="user.role.admin"/></option>
                                                    <option value="USER" selected><spring:message code="user.role.user"/></option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-danger" data-dismiss="modal" onclick="closeAllModal()">Закрыть</button>
                                    <button type="button" class="btn btn-primary" onclick="usersTable.save()">Сохранить</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

            </div>
        </main>
    </div>
</div>

<script src="resources/js/helper/wakepark.const.js"></script>
<script src="resources/js/helper/wakepark.render.js"></script>
<script src="resources/js/helper/wakepark.common.js"></script>
<script src="resources/js/helper/wakepark.user.js"></script>
<script src="resources/js/wakepark.page.users.js"></script>
</body>
<jsp:include page="fragments/i18n.jsp">
    <jsp:param name="page" value="user"/>
</jsp:include>
</html>