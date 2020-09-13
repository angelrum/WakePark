<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!-- Модальное окно для создания клиента -->
<div class="modal fade" id="clientCreate" role="dialog" tabindex="-1" aria-labelledby="modalTitle" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <form class="" id="clientForm">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="modalTitle">Добавить клиента</h5>
                    <button type="button" class="close" aria-label="Close" data-dismiss="modal"><span aria-hidden="true" onclick="closeNoty()">×</span></button>
                </div>
                <div class="m-3 modal-body">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="h6">Номер телефона</label>
                                <div class="input-group mb-3">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text">+7</span>
                                    </div>
                                    <input type="text" id="telnumber" name="telnumber" class="form-control" onblur="phoneblur(this.id)" placeholder="(9XX) XXX-XX-XX">
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="h6">Город</label>
                                <input name="city" id="city" type="text" class="form-control">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="h6">Фамилия</label>
                                <input name="lastname" type="text" id="lastname" class="form-control">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="h6">Имя</label>
                                <input name="firstname" type="text" id="firstname" class="form-control">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="h6">Отчество</label>
                                <input name="middlename" type="text" id="middlename" class="form-control">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="h6">Почта</label>
                                <div class="mb-3 input-group"><div class="input-group-prepend">
                                    <span class="input-group-text">@</span>
                                </div>
                                    <input name="email" placeholder="test@test.ru" type="text" class="form-control"></div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" data-dismiss="modal" onclick="closeNoty()">Закрыть</button>
                    <button type="button" class="btn btn-primary" onclick=clientsTable.save()>Сохранить</button>
                </div>
            </div>
        </form>
    </div>
</div>