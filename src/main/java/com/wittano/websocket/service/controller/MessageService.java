package com.wittano.websocket.service.controller;

import com.wittano.websocket.interfaces.Mapper;
import com.wittano.websocket.models.entity.Message;
import com.wittano.websocket.models.request.MessageRequest;
import com.wittano.websocket.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class MessageService {
    private final SimpMessagingTemplate template;
    private final MessageRepository repository;
    private final Mapper<MessageRequest, Message> mapper;

    public List<Message> getCorrespondence(String from, String to) {
        return repository.getCorrespondence(from, to);
    }

    @Transactional
    public void send(MessageRequest message) throws Throwable {
        repository.save(mapper.map(message));
        template.convertAndSend(String.format("/chat/%s-notification", message.getTo()), message.getFrom());
        template.convertAndSend(String.format("/chat/%s", message.getQueueName()), message);
    }
}
