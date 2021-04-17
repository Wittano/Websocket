package com.wittano.websocket.service;

import com.wittano.websocket.models.entity.Message;
import com.wittano.websocket.repository.MessageRepository;
import com.wittano.websocket.repository.UserRepository;
import com.wittano.websocket.service.controller.MessageService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static com.wittano.websocket.utils.TestData.*;

@SpringBootTest
public class MessageServiceTest {
    @Autowired
    private MessageRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageService service;

    @BeforeEach
    void fillList() {
        if (userRepository.count() == 0) {
            userRepository.saveAll(Arrays.asList(TEST_USER, TEST_USER_2));
        }
    }

    @AfterEach
    void clearList() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("Get correspondence")
    void getMessages() {
        List<Message> messages = service
                .getCorrespondence(TEST_MESSAGE_REQUEST.getFrom(), TEST_MESSAGE_REQUEST.getTo());

        Assertions.assertEquals(1, messages.size());
    }

    @Test
    @DisplayName("Send message between 2 users")
    void send() throws Throwable {
        service.send(TEST_MESSAGE_REQUEST);

        Assertions.assertNotEquals(0, repository.getCorrespondence(
                TEST_MESSAGE.getFrom().getUsername(),
                TEST_MESSAGE.getTo().getUsername()).size());
    }
}
