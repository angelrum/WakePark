<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!--Модальное окно со списком доступных билетов-->
<div class="modal fade" id="choice_ticket" role="dialog" tabindex="-1" aria-labelledby="ticket_title" aria-hidden="true">
    <div class="modal-dialog modal-xl" role="document">
        <form class="">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="ticket_title">Выбрать билет</h5>
                    <button type="button" class="close" aria-label="Close" onclick="closeAllModal()"><span aria-hidden="true">×</span></button>
                </div>
                <div class="m-3 modal-body">
                    <div class="row">
                        <div class="col p-0">
                            <table class="dataTables_wrapper dt-bootstrap4 fl-table table-hover" id="dt_tickets" style="width: 100%">
                                <thead>
                                <tr>
                                    <th scope="col" rowspan="2"></th>
                                    <th scope="col" rowspan="2">Тип билета</th>
                                    <th scope="col" rowspan="2">Название</th>
                                    <th scope="col" rowspan="2" width=5%>Снаряжение</th>
                                    <th scope="col" colspan="2">Период</th>
                                    <th scope="col" rowspan="2">Кол-во<br>дней</th>
                                    <th scope="col" rowspan="2">Кол-во<br>месяцев</th>
                                    <th scope="col" rowspan="2">Стоимость<br>в будни</th>
                                    <th scope="col" rowspan="2">Стоимость<br>в выходные</th>
                                    <th scope="col" rowspan="2" width=5%>Длит.сета</th>
                                    <th scope="col" rowspan="2" width=5%>Кол-во</th>
                                </tr>
                                <tr>
                                    <th width=5%>С</th>
                                    <th width=5%>По</th>
                                </tr>
                                </thead>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" onclick="closeAllModal()">Закрыть</button>
                    <button type="button" class="btn btn-success" id="saveTicket" onclick="saveClTicket()">Сохранить</button></div>
            </div>
        </form>
    </div>
</div>