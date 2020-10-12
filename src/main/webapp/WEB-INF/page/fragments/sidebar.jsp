<%@ page contentType="text/html" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav id="sidebar" class="sidebar toggled">
    <a class="sidebar-brand" href="#">
        <img src="https://img.icons8.com/fluent/48/000000/logo.png"/>WakePark</a>
    <div class="sidebar-content">
        <div class="sidebar-user">
            <c:if test="${authUser != null}">
                <img src="${authUser.avatar}"/>
                <div class="font-weight-bold">${authUser.lastname} ${authUser.firstname}</div>
                <small>${authUser.roles.toString()}</small>
            </c:if>
            <ul class="sidebar-nav">
                <li class="sidebar-header"><spring:message code="menu.title"/></li>
                <li class="sidebar-item <c:if test="${param.page.equals('main')}">active</c:if>">
                    <a class="sidebar-link" href="/main">
                        <span class='align-middle mr-2 fas fa fa-table'></span>
                        <span class="align-middle"><spring:message code="query.title"/></span>
                    </a>
                </li>
                <li class="sidebar-item <c:if test="${param.page.equals('client')}">active</c:if>">
                    <a class="sidebar-link" href="/clients">
                        <span class='align-middle mr-2 fas fa fa-book'></span>
                        <span class="align-middle"><spring:message code="client.title"/></span>
                    </a>
                </li>
                <li class="sidebar-item <c:if test="${param.page.equals('ticket')}">active</c:if>">
                    <a class="sidebar-link" href="/tickets">
                        <span class='align-middle mr-2 fas fa fa-ticket'></span>
                        <span class="align-middle"><spring:message code="ticket.title"/></span>
                    </a>
                </li>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <li class="sidebar-item <c:if test="${param.page.equals('user')}">active</c:if>">
                        <a class="sidebar-link" href="/users">
                            <span class='align-middle mr-2 fas fa fa-user'></span>
                            <span class="align-middle"><spring:message code="user.title"/></span>
                        </a>
                    </li>
                </sec:authorize>
            </ul>
        </div>
    </div>
</nav>