<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<nav id="sidebar" class="sidebar toggled">
    <a class="sidebar-brand" href="#">
        <img src="https://img.icons8.com/fluent/48/000000/logo.png"/>WakePark</a>
    <div class="sidebar-content">
        <div class="sidebar-user">
            <img src="https://img.icons8.com/dotty/80/000000/user-male.png"/>
            <div class="font-weight-bold">Иванов Иван</div>
            <small>Оператор</small>
            <ul class="sidebar-nav">
                <li class="sidebar-header">Меню</li>
                <li class="sidebar-item active">
                    <a class="sidebar-link" href="/main">
                        <span class='align-middle mr-2 fas fa fa-table'></span>
                        <span class="align-middle">Очередь</span>
                    </a>
                </li>
                <li class="sidebar-item">
                    <a class="sidebar-link" href="/clients">
                        <span class='align-middle mr-2 fas fa fa-book'></span>
                        <span class="align-middle">Клиенты</span>
                    </a>
                </li>
                <li class="sidebar-item">
                    <a class="sidebar-link" href="/tickets">
                        <span class='align-middle mr-2 fas fa fa-ticket'></span>
                        <span class="align-middle">Билеты</span>
                    </a>
                </li>
            </ul>
        </div>
    </div>
</nav>