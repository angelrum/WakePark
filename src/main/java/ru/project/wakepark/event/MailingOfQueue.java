package ru.project.wakepark.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.core.MessageSendingOperations;
import ru.project.wakepark.service.QueueService;

public class MailingOfQueue implements EventListener {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private static final String URL_QUEUE = "/topic/queue";

    private MessageSendingOperations<String> message;

    private QueueService service;

    public MailingOfQueue(MessageSendingOperations<String> message, QueueService service) {
        this.message = message;
        this.service = service;
    }

    @Override
    public void update(int companyId) {
        log.info("send queue in message broker");
        message.convertAndSend(URL_QUEUE, service.getAll(companyId));
    }
}
