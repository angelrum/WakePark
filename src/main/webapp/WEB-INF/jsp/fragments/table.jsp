<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="col">
    <c:choose>
        <c:when test="${not empty instance}">
            <table class="dataTables_wrapper dt-bootstrap4 fl-table table-hover" id="datatable" style="width: 100%">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <c:forEach items="${instance}" var="th">
                        <th scope="col"><c:out value="${th}"/></th>
                    </c:forEach>
                    <th scope="col"></th>
                    <th scope="col"></th>
                </tr>
                </thead>
            </table>
        </c:when>
        <c:otherwise>
            <c:out value="Ошибка при создании таблицы"/>
        </c:otherwise>
    </c:choose>
</div>