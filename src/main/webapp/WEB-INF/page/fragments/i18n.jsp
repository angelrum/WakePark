<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
    var i18n = [];
    <%-- user.add/user.edit or meal.add/meal.edit --%>
    if (String.valueOf("${param.page}")!=="") {
        i18n["addTitle"] = '<spring:message code="${param.page}.add"/>';
        i18n["editTitle"] = '<spring:message code="${param.page}.edit"/>';
    } else {
        i18n["addTitle"] = '<spring:message code="common.add"/>';
        i18n["editTitle"] = '<spring:message code="common.edit"/>';
    }

    <c:forEach var='key' items='<%=new String[]{"common.deleted", "common.saved", "common.enabled", "common.disabled", "common.errorStatus", "common.search", "common.confirm",
    "tickets.cost", "tickets.day", "tickets.equipment", "tickets.from", "tickets.interval", "tickets.month", "tickets.name", "tickets.period", "tickets.start", "tickets.weekendcost",
    "clients.city", "clients.firstname", "clients.lastname", "clients.middlename", "clients.telnumber", "tickets.type.abonement.short"}%>'>
        i18n['${key}'] = '<spring:message code="${key}"/>';
    </c:forEach>
</script>