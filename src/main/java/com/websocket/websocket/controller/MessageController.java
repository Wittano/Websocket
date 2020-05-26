package com.websocket.websocket.controller;

import com.websocket.websocket.models.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class MessageController {

    private final SimpMessagingTemplate template;

    public MessageController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @PostMapping("/send")
    public void send(@RequestBody Message message){
        template.convertAndSend("/chat/test", message);
    }
}
