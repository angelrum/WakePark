<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <jsp:include page="fragments/head.jsp"></jsp:include>
</head>
<body>
<main class="main h-100 w-100">
    <div class="container h-100">
        <div class="row h-100">
            <div class="col-sm-10 col-md-8 col-lg-6 mx-auto d-table h-100">
                <div class="d-table-cell align-middle">

                    <div class="text-center mt-4">
                        <h1 class="h2">Добро пожаловать!</h1>
                        <p class="lead">
                            Войдите в Ваш аккаунт
                        </p>
                    </div>

                    <div class="card">
                        <div class="card-body">
                            <div class="m-sm-4">
                                <div class="text-center">
                                    <img src="//img.icons8.com/officel/100/000000/xbox-l.png" alt="Wakepark" class="img-fluid rounded-circle" width="132" height="132">
                                </div>
                                <form:form id="login_form" action="perform_login" method="post">
                                    <div class="form-group">
                                        <label>Логин</label>
                                        <input class="form-control form-control-lg" type="text" name="username" placeholder="Enter your login">
                                    </div>
                                    <div class="form-group">
                                        <label>Пароль</label>
                                        <input class="form-control form-control-lg" type="password" name="password" placeholder="Enter your password">
                                        <!--<small>
                                            <a href="pages-reset-password.html">Забыли пароль?</a>
                                        </small> -->
                                    </div>
                                    <div>
                                        <c:if test="${param.error}">
                                            <div class="error">${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</div>
                                        </c:if>
                                        <c:if test="${not empty param.message}">
                                            <div class="message"><spring:message code="${param.message}"/></div>
                                        </c:if>
                                    </div>
                                    <div class="text-center mt-3">
                                        <!--<a href="dashboard-default.html" class="btn btn-lg btn-primary">Sign in</a> -->
                                        <button type="submit" class="btn btn-lg btn-primary">Sign in</button>
                                    </div>
                                </form:form>
                            </div>
                            <div><button type="button" class="btn btn-info" onclick="clickRegistration()">Auto registration</button></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
</body>
<script>
    function clickRegistration() {
        $('input[name="username"]').val('admin');
        $('input[name="password"]').val('pass');
        $('button[type=submit]').click();
    }
</script>
</html>