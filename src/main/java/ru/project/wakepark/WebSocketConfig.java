package ru.project.wakepark;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import javax.net.ssl.SSLContext;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(final MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(final StompEndpointRegistry registry) {
        registry.addEndpoint("/event");
        registry.addEndpoint("/event").withSockJS();

        registry.addEndpoint("/chat");
        registry.addEndpoint("/chat").withSockJS();
    }

    @Bean
    public StandardWebSocketClient getWebSocketClient() throws NoSuchAlgorithmException {
        StandardWebSocketClient simpleWebSocketClient =
                new StandardWebSocketClient();
        Map<String, Object> userProperties = new HashMap<>();
        userProperties.put("org.apache.tomcat.websocket.SSL_CONTEXT", SSLContext.getDefault());
        simpleWebSocketClient.setUserProperties(userProperties);
        return simpleWebSocketClient;
    }

}
