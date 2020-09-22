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
                    <h5 class="modal-title" id="ticket_title"><spring:message code="tickets.choice"/></h5>
                    <button type="button" class="close" aria-label="Close" onclick="closeAllModal()"><span aria-hidden="true">×</span></button>
                </div>
                <div class="m-3 modal-body">
                    <div class="row">
                        <div class="col p-0">
                            <table class="dataTables_wrapper dt-bootstrap4 fl-table table-hover" id="dt_tickets" style="width: 100%">
                                <thead>
                                <tr>
                                    <th scope="col" rowspan="2"></th>
                                    <th scope="col" rowspan="2"><spring:message code="tickets.type"/></th>
                                    <th scope="col" rowspan="2"><spring:message code="tickets.name"/></th>
                                    <th scope="col" rowspan="2" width=5%><spring:message code="tickets.equipment"/></th>
                                    <th scope="col" colspan="2"><spring:message code="tickets.period"/></th>
                                    <th scope="col" rowspan="2"><spring:message code="tickets.day.br"/></th>
                                    <th scope="col" rowspan="2"><spring:message code="tickets.month.br"/></th>
                                    <th scope="col" rowspan="2"><spring:message code="tickets.cost.br"/></th>
                                    <th scope="col" rowspan="2"><spring:message code="tickets.cost.weekendcost.br"/></th>
                                    <th scope="col" rowspan="2" width=5%><spring:message code="tickets.duration.short"/></th>
                                    <th scope="col" rowspan="2" width=5%><spring:message code="common.count"/></th>
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
                    <button type="button" class="btn btn-secondary" onclick="closeAllModal()"><spring:message code="common.cancel"/></button>
                    <button type="button" class="btn btn-success" id="saveTicket" onclick="saveClTicket()"><spring:message code="common.save"/></button></div>
            </div>
        </form>
    </div>
</div>