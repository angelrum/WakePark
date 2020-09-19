package ru.project.wakepark.websocket;

import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

@org.springframework.stereotype.Service
public class QuoteService {

    private final MessageSendingOperations<String> messagingTemplate;

    public QuoteService(MessageSendingOperations<String> messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Scheduled(fixedDelay = 1500)
    public void sendQuotes() {
        final String time = new SimpleDateFormat("HH:mm").format(new Date());
        System.out.println("send");
        messagingTemplate.convertAndSend("/topic/messages", new OutputMessage("Hl", "jkkkk", time));
    }
}
