package com.websocket.websocket.interfaces;

import com.websocket.websocket.models.Message;

import java.util.List;

public interface MessageRepository {
    List<Message> getCorrespondence(String from, String to);

    void save(Message message);
}
