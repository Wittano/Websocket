package com.websocket.websocket.controller;

import com.websocket.websocket.interfaces.MessageRepository;
import com.websocket.websocket.models.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

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
    public List<Message> getAllMessage(@PathVariable("from") String from, @PathVariable("to") String to){
        return repository.getCorrespondence(from, to);
    }

    @PostMapping("/news")
    public void send(@RequestBody Message message){
        Thread thread = new Thread(() -> repository.save(message));
        thread.setName("SaveMessageInDatabaseThread");
        thread.start();

        template.convertAndSend(String.format("/chat/%s-%s", message.getFrom(), message.getTo()), message);
    }
}
