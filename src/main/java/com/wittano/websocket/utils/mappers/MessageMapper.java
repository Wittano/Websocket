package com.wittano.websocket.utils.mappers;

import com.wittano.websocket.interfaces.Mapper;
import com.wittano.websocket.models.entity.Message;
import com.wittano.websocket.models.entity.User;
import com.wittano.websocket.models.request.MessageRequest;
import com.wittano.websocket.repository.UserRepository;
import com.wittano.websocket.utils.statics.ExceptionTemplate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@AllArgsConstructor
public class MessageMapper implements Mapper<MessageRequest, Message> {
    private final UserRepository repo;

    @Override
    public Message map(MessageRequest data) throws Throwable {
        return new Message(
                0,
                getUser(data.getFrom()),
                getUser(data.getTo()),
                data.getContent(),
                new Date(),
                data.getQueueName());
    }

    private User getUser(String username) throws Throwable {
        return repo.findUserByUsername(username)
                .orElseThrow(ExceptionTemplate.USER_NOT_FOUND(username));
    }
}
