<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<table class="dataTables_wrapper dt-bootstrap4 fl-table table-hover" id="dt_cl_tickets" style="width:100%">
    <thead>
    <tr>
        <th scope="col" rowspan="2"><spring:message code="tickets.type"/></th>
        <th scope="col" rowspan="2"><spring:message code="tickets.name"/></th>
        <th scope="col" rowspan="2"><spring:message code="tickets.equipment"/></th>
        <th scope="col" colspan="2"><spring:message code="tickets.interval"/></th>
        <th scope="col" rowspan="2"><spring:message code="tickets.day.long"/></th>
        <th scope="col" rowspan="2"><spring:message code="tickets.month.long"/></th>
        <th scope="col" rowspan="2"><spring:message code="tickets.duration"/></th>
        <th scope="col" rowspan="2"><spring:message code="common.count"/></th>
        <th scope="col" rowspan="2"></th>
        <th scope="col" rowspan="2"></th>
    </tr>
    <tr>
        <th><spring:message code="tickets.from"/></th>
        <th><spring:message code="tickets.to"/></th>
    </tr>
    </thead>
</table>