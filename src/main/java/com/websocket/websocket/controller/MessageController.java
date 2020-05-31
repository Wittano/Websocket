package com.websocket.websocket.controller;

import com.websocket.websocket.interfaces.MessageRepository;
import com.websocket.websocket.models.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@CrossOrigin
public class MessageController {

    private final SimpMessagingTemplate template;
    private final MessageRepository repository;

    public MessageController(SimpMessagingTemplate template, MessageRepository repository) {
        this.template = template;
        this.repository = repository;
    }

    @GetMapping("/news/{from}/{to}")
    public List<Message> getAllMessage(@PathVariable("from") String from, @PathVariable("to") String to) {
        return repository.getCorrespondence(from, to);
    }

    @PostMapping("/news")
    public void send(@RequestBody Message message) {
        Thread thread = new Thread(() -> repository.save(message));
        thread.setName("SaveMessageInDatabaseThread");
        thread.start();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() ->
                template.convertAndSend(String.format("/chat/%s-notification", message.getTo()), message.getFrom()));
        template.convertAndSend(String.format("/chat/%s", message.getQueueName()), message);
    }

    @MessageMapping("/message")
    @SendTo("/chat/{queue}")
    public Message message(@DestinationVariable("queue") String queueName, Message message) {
        return message;
    }
}
