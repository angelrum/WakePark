<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<nav class="navbar navbar-expand navbar-theme">
    <a class="sidebar-toggle d-flex mr-2">
        <i class="hamburger align-self-center"></i>
    </a>
    <div class="navbar-collapse collapse">
        <ul class="navbar-nav ml-auto">
            <li class="nav-item dropdown active">
                <a class="nav-link dropdown-toggle position-relative" href="#" id="messagesDropdown" data-toggle="dropdown">
                    <span class='align-middle mr-2 fas fa fa-envelope-o'></span>
                </a>
                <div class="dropdown-menu dropdown-menu-lg dropdown-menu-right py-0" aria-labelledby="messagesDropdown">
                    <div class="dropdown-menu-header">
                        <div class="position-relative">
                            1 Новое сообщение
                        </div>
                    </div>
                    <div class="list-group">
                        <a href="#" class="list-group-item">
                            <div class="row no-gutters align-items-center">
                                <div class="col-2">
                                    <img src="https://img.icons8.com/material-two-tone/24/000000/user-male.png" class="avatar img-fluid rounded-circle" alt="Roman">
                                </div>
                                <div class="col-10 pl-2">
                                    <div class="text-dark">Roman</div>
                                    <div class="text-muted small mt-1">Запускай лебедку</div>
                                    <div class="text-muted small mt-1">5 мин. назад</div>
                                </div>
                            </div>
                        </a>
                    </div>
                    <div class="dropdown-menu-footer">
                        <a href="#" class="text-muted">Показать все сообщения</a>
                    </div>
                </div>
            </li>
            <li class="nav-item">
                <form:form class="form-inline" action="logout" method="post">
                    <button class="btn btn-primary my-1" type="submit">
                        <span class="fa fa-sign-out"></span>
                    </button>
                </form:form>
            </li>
        </ul>
    </div>
</nav>