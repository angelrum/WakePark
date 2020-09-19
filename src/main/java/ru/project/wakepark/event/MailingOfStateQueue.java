package ru.project.wakepark.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.core.MessageSendingOperations;
import ru.project.wakepark.service.QueueStateService;

public class MailingOfStateQueue implements EventListener {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private static final String URL_STATE = "/topic/state";

    private MessageSendingOperations<String> message;

    private QueueStateService stateService;

    public MailingOfStateQueue(MessageSendingOperations<String> message, QueueStateService stateService) {
        this.message = message;
        this.stateService = stateService;
    }

    @Override
    public void update(int companyId) {
        log.info("send state in message broker");
        message.convertAndSend(URL_STATE, stateService.getState(companyId));
    }
}
