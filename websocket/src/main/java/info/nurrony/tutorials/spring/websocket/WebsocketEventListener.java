package info.nurrony.tutorials.spring.websocket;

import java.util.Optional;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebsocketEventListener {
    private final SimpMessageSendingOperations _sendingTemplate;

    @EventListener
    public void sessionDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        Optional.ofNullable(headerAccessor.getSessionAttributes()).ifPresent(sessionAttribute -> {
            Optional.ofNullable(sessionAttribute.get("username")).ifPresent((username) -> {
                log.info("user disconnected : {}", username);
                var message = ChatMessage.builder().type(MessageType.LEAVE).sender((String) username).build();
                _sendingTemplate.convertAndSend("/topic/public", message);
            });
        });
    }
}
