<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="card">
    <div class="card-header" id="cl_tickets">
        <button class="btn collapsed btn-custom" data-toggle="collapse" data-target="#collapse_cl_tickets" aria-expanded="false" aria-controls="collapse_cl_tickets">
            <h5 class="card-title">Билеты клиента</h5>
        </button>
    </div>
    <!--чтобы скрыть надо убрать класс show-->
    <div id="collapse_cl_tickets" class="collapse" aria-labelledby="headingOne" data-parent="#cl_tickets">
        <div class="card-body">
            <div class="col text-left mb-2">
                <button class="btn btn-primary" data-toggle="modal" data-target="#choice_ticket" id="btn_cl_ticket" disabled>Добавить билет</button>
            </div>
            <div class="col">
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
            </div>
        </div>
    </div>
</div>
