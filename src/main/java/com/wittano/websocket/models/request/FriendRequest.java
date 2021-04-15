package com.wittano.websocket.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FriendRequest {
    private String username;
    private String friendName;
}
