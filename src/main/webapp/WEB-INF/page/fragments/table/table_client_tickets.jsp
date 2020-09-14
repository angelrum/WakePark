<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<table class="dataTables_wrapper dt-bootstrap4 fl-table table-hover" id="dt_cl_tickets" style="width:100%">
    <thead>
    <tr>
        <th scope="col" rowspan="2">Тип билета</th>
        <th scope="col" rowspan="2">Название</th>
        <th scope="col" rowspan="2">Снаряжение</th>
        <th scope="col" colspan="2">Интервал</th>
        <th scope="col" rowspan="2">Срок действия, дни</th>
        <th scope="col" rowspan="2">Срок действия, мес.</th>
        <th scope="col" rowspan="2">Длит.сета, мин.</th>
        <th scope="col" rowspan="2">Кол-во</th>
        <th scope="col" rowspan="2"></th>
        <th scope="col" rowspan="2"></th>
    </tr>
    <tr>
        <th>с</th>
        <th>по</th>
    </tr>
    </thead>
</table>