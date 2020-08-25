package com.websocket.websocket;

import com.websocket.websocket.interfaces.service.MessageService;
import com.websocket.websocket.models.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
public class MessageServiceTest {

    private final Message message;

    @Autowired
    private MessageService service;

    public MessageServiceTest() {
        message = new Message("root", "test", "Hi mark", new Date(), "");
    }

    @Test
    void addMessageForTestUser() {
        List<Message> messages = service.getCorrespondence(message.getFrom(), message.getTo());

        service.addMessage(message);

        List<Message> secondMessageList = service.getCorrespondence(message.getFrom(), message.getTo());

        Assertions.assertNotEquals(messages.size(), secondMessageList.size());

        boolean isMessageContainInSecondMessageList = secondMessageList.stream().
                anyMatch(element -> element.getFrom().equals(message.getFrom()) &&
                        element.getTo().equals(message.getTo()) &&
                        element.getContent().equals(message.getContent()));

        Assertions.assertTrue(isMessageContainInSecondMessageList);
    }

    @Test
    void getMessageSpecifUser() {
        service.addMessage(message);

        List<Message> messages = service.getCorrespondence(message.getFrom(), message.getTo());

        Assertions.assertNotEquals(messages.size(), 0);
    }
}
