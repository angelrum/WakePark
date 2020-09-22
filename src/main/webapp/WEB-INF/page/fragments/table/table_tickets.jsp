<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<table class="dataTables_wrapper dt-bootstrap4 fl-table" id="dt_tickets">
    <thead>
    <tr>
        <th scope="col" rowspan="2"><spring:message code="tickets.type"/></th>
        <th scope="col" rowspan="2"><spring:message code="tickets.name"/></th>
        <th scope="col" rowspan="2" width=5%><spring:message code="tickets.active"/></th>
        <th scope="col" rowspan="2" width=5%><spring:message code="tickets.equipment"/></th>
        <th scope="col" rowspan="2" width=5%><spring:message code="tickets.duration.short"/></th>
        <th scope="col" colspan="2"><spring:message code="tickets.period"/></th>
        <th scope="col" colspan="2"><spring:message code="tickets.fix.duration"/></th>
        <th scope="col" rowspan="2"><spring:message code="tickets.day.br"/></th>
        <th scope="col" rowspan="2"><spring:message code="tickets.month.br"/></th>
        <th scope="col" rowspan="2"><spring:message code="tickets.cost.br"/></th>
        <th scope="col" rowspan="2"><spring:message code="tickets.cost.weekendcost.br"/></th>
        <th scope="col" rowspan="2"></th>
        <th scope="col" rowspan="2"></th>
    </tr>
    <tr>
        <th width=5%><spring:message code="tickets.from"/></th>
        <th width=5%><spring:message code="tickets.to"/></th>
        <th width=5% nowrap><spring:message code="tickets.start"/></th>
        <th width=9% nowrap><spring:message code="tickets.end"/></th>
    </tr>
    </thead>
</table>