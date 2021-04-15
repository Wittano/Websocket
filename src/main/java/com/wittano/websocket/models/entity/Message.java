package com.wittano.websocket.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    private long id;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    private User from;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    private User to;
    private String content;
    private Date date = new Date();
    private String queueName;
}
