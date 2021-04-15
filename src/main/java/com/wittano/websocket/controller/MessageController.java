package com.wittano.websocket.controller;

import com.wittano.websocket.models.entity.Message;
import com.wittano.websocket.models.request.MessageRequest;
import com.wittano.websocket.service.controller.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MessageController {
    private final MessageService service;

    @GetMapping("/news/{from}/{to}")
    public List<Message> getAllMessage(@PathVariable("from") String from, @PathVariable("to") String to) {
        return service.getCorrespondence(from, to);
    }

    @PostMapping("/news")
    public void send(@RequestBody MessageRequest message) throws Throwable {
        service.send(message);
    }

    @MessageMapping("/message")
    @SendTo("/chat/{queue}")
    public Message message(Message message) {
        return message;
    }
}
