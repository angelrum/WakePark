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

            </div>
        </main>
    </div>
</div>

<script src="resources/js/helper/wakepark.const.js"></script>
<script src="resources/js/helper/wakepark.render.js"></script>
<script src="resources/js/helper/wakepark.common.js"></script>
</body>
</html>