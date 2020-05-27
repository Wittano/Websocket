package com.websocket.websocket.interfaces.service;

import com.websocket.websocket.models.Message;

import java.util.List;

public interface MessageService {
    void addMessage(Message message);
    List<Message> getCorrespondence(String from, String to);
}
