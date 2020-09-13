<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<table class="dataTables_wrapper dt-bootstrap4 fl-table" id="dt_tickets">
    <thead>
    <tr>
        <th scope="col" rowspan="2">Тип билета</th>
        <th scope="col" rowspan="2">Название</th>
        <th scope="col" rowspan="2" width=5%>Активен</th>
        <th scope="col" rowspan="2" width=5%>Снаряжение</th>
        <th scope="col" rowspan="2" width=5%>Длит.сета</th>
        <th scope="col" colspan="2">Период</th>
        <th scope="col" colspan="2">Фикс. длительность</th>
        <th scope="col" rowspan="2">Кол-во<br>дней</th>
        <th scope="col" rowspan="2">Кол-во<br>месяцев</th>
        <th scope="col" rowspan="2">Стоимость<br>в будни</th>
        <th scope="col" rowspan="2">Стоимость<br>в выходные</th>
        <th scope="col" rowspan="2"></th>
        <th scope="col" rowspan="2"></th>
    </tr>
    <tr>
        <th width=5%>С</th>
        <th width=5%>По</th>
        <th width=5% nowrap>Дата начала</th>
        <th width=9% nowrap>Дата окончания</th>
    </tr>
    </thead>
</table>