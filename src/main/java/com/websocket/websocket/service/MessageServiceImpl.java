package com.websocket.websocket.service;

import com.websocket.websocket.interfaces.MessageRepository;
import com.websocket.websocket.interfaces.service.MessageService;
import com.websocket.websocket.models.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepo;

    public MessageServiceImpl(MessageRepository messageRepo) {
        this.messageRepo = messageRepo;
    }

    @Override
    public void addMessage(Message message) {
        messageRepo.save(message);
    }

    @Override
    public List<Message> getCorrespondence(String from, String to) {
        return messageRepo.getCorrespondence(from, to);
    }
}
