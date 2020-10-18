<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <jsp:include page="fragments/head.jsp"></jsp:include>
</head>
<body style="height: 100%">
<div class="wrapper h-100">
    <div class="main h-100">
        <main class="content h-100" style="font-family:Jost,-apple-system,BlinkMacSystemFont,Segoe UI,Helvetica Neue,Arial,sans-serif">
            <div class="p-0 container-fluid h-100">
                <div class="row h-100">
                    <div class="col h-100">
                        <div class="flex-fill w-100 card h-75">
                            <div class="card-header">
                                <p style="font-size: 2em"><spring:message code="queue.timer"/></p>
                            </div>
                            <div class="d-flex card-body">
                                <div class="col align-self-center text-center" style="font-size: 4em">
                                    <div class="timer_panel_nums h2">
                                        <!-- минуты… -->
                                        <span class="timer_nums minutes" >00</span>
                                        <span>:</span>
                                        <!-- …и секунды -->
                                        <span class="timer_nums seconds" >00</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="flex-fill w-100 card">
                            <div class="d-flex card-body">
                                <div class="col align-self-center text-center">
                                    <p style="font-size: 2em"><spring:message code="query.duration"/> <span class="timer_common minutes" >00</span>
                                        <span>:</span>
                                        <span class="timer_common seconds" >00</span>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col">
                        <div class="flex-fill w-100 card">
                            <div class="card-header">
                                <p style="font-size: 2em"><spring:message code="query.active.title"/></p>
                            </div>
                            <div class="d-flex card-body">
                                <table class="my-0 table table-striped w-100" id="dt_queue">
                                    <thead>
                                    <tr>
                                        <th scope="col"><spring:message code="query.title"/></th>
                                        <th width="20%" scope="col"><spring:message code="query.count"/></th>
                                    </tr>
                                    </thead>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>
</div>

<!--WebSocket-->
<script src="resources/js/websocket/sockjs-0.3.4.js"></script>
<script src="resources/js/websocket/stomp.js"></script>

<script src="resources/js/helper/wakepark.const.js"></script>
<script src="resources/js/helper/wakepark.render.js"></script>
<script src="resources/js/helper/wakepark.common.js"></script>
<script src="resources/js/helper/wakepark.timer.js"></script>
<script src="resources/js/helper/wakepark.queue.js"></script>
<script src="resources/js/wakepark.page.screen.js"></script>
</body>
<jsp:include page="fragments/i18n.jsp">
    <jsp:param name="page" value="client"/>
</jsp:include>
</html>