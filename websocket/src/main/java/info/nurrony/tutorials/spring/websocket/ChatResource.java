package info.nurrony.tutorials.spring.websocket;

import java.util.Optional;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ChatResource {

    @SendTo("/topic/public")
    @MessageMapping("/chat.send-message")
    public ChatMessage sendMessage(@Payload ChatMessage message) {
        log.info("got message from client {}", message.toString());
        return message;
    }

    @SendTo("/topic/public")
    @MessageMapping("/chat.add-user")
    public ChatMessage user(@Payload ChatMessage message, SimpMessageHeaderAccessor sessionHeaderAccessor) {
        Optional.ofNullable(sessionHeaderAccessor.getSessionAttributes()).ifPresent((sessionAttributes) -> {
            sessionAttributes.put("username", message.getSender());
        });
        return message;
    }
}
