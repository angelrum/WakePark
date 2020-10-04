<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<table class="dataTables_wrapper dt-bootstrap4 fl-table table-hover" id="dt_cl_tickets" style="width:100%">
    <thead>
    <tr>
        <th scope="col" rowspan="2"><spring:message code="ticket.pass"/></th>
        <th scope="col" rowspan="2"><spring:message code="ticket.name"/></th>
        <th scope="col" rowspan="2"><spring:message code="ticket.equipment"/></th>
        <th scope="col" colspan="2"><spring:message code="ticket.interval"/></th>
        <th scope="col" rowspan="2"><spring:message code="ticket.day.long"/></th>
        <th scope="col" rowspan="2"><spring:message code="ticket.month.long"/></th>
        <th scope="col" rowspan="2"><spring:message code="ticket.duration"/></th>
        <th scope="col" rowspan="2"><spring:message code="common.count"/></th>
        <th scope="col" rowspan="2"></th>
        <th scope="col" rowspan="2"></th>
    </tr>
    <tr>
        <th><spring:message code="ticket.period.from"/></th>
        <th><spring:message code="ticket.period.to"/></th>
    </tr>
    </thead>
</table>