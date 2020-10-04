<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<table class="dataTables_wrapper dt-bootstrap4 fl-table" id="dt_tickets">
    <thead>
    <tr>
        <th scope="col" rowspan="2"><spring:message code="ticket.pass"/></th>
        <th scope="col" rowspan="2"><spring:message code="ticket.name"/></th>
        <th scope="col" rowspan="2" width=5%><spring:message code="ticket.enable"/></th>
        <th scope="col" rowspan="2" width=5%><spring:message code="ticket.equipment"/></th>
        <th scope="col" rowspan="2" width=5%><spring:message code="ticket.duration.short"/></th>
        <th scope="col" colspan="2"><spring:message code="ticket.period"/></th>
        <th scope="col" colspan="2"><spring:message code="ticket.duration.fix"/></th>
        <th scope="col" rowspan="2"><spring:message code="ticket.day.br"/></th>
        <th scope="col" rowspan="2"><spring:message code="ticket.month.br"/></th>
        <th scope="col" rowspan="2"><spring:message code="ticket.cost.br"/></th>
        <th scope="col" rowspan="2"><spring:message code="ticket.weekendcost.br"/></th>
        <th scope="col" rowspan="2"></th>
        <th scope="col" rowspan="2"></th>
    </tr>
    <tr>
        <th width=5%><spring:message code="ticket.period.from"/></th>
        <th width=5%><spring:message code="ticket.period.to"/></th>
        <th width=5% nowrap><spring:message code="ticket.start_date"/></th>
        <th width=9% nowrap><spring:message code="ticket.end_date"/></th>
    </tr>
    </thead>
</table>