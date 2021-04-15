package com.wittano.websocket.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageRequest {
    private String from;
    private String to;
    private String content;
    private String queueName;
}
