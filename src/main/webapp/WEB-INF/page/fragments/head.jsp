<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<head>
    <!-- Required meta tags -->
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

    <title>Заголовок</title>
    <base href="${pageContext.request.contextPath}/"/>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="webjars/bootstrap/4.4.1/dist/css/bootstrap.min.css"/>
    <!--Noty для отображения статусов и ошибок-->
    <link rel="stylesheet" href="webjars/noty/3.1.4/lib/noty.css"/>
    <!--Icon-->
    <link rel="stylesheet" href="webjars/noty/3.1.4/demo/font-awesome/css/font-awesome.min.css">
    <!-- Custom css-->
    <link rel="stylesheet" href="resources/style/spark.bootlab.light.css">
    <link rel="stylesheet" href="resources/style/style.css">

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script type="text/javascript" src="webjars/jquery/3.4.1/jquery.min.js"></script>
    <script type="text/javascript" src="webjars/popper.js/2.0.2/umd/popper.min.js" defer></script>
    <script type="text/javascript" src="webjars/bootstrap/4.4.1/dist/js/bootstrap.min.js" defer></script>
    <!--Noty для отображения статусов и ошибок-->
    <script type="text/javascript" src="webjars/noty/3.1.4/lib/noty.min.js" defer></script>

    <!-- Datatable -->
    <script type="text/javascript" src="webjars/datatables/1.10.20/js/jquery.dataTables.min.js" defer></script>
    <script type="text/javascript" src="webjars/datatables/1.10.20/js/dataTables.bootstrap4.min.js" defer></script>
</head>