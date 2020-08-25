package com.websocket.websocket.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "messages", schema = "local")
public class Message {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(name = "message_from", updatable = false)
    private String from;
    @Column(name = "message_to", updatable = false)
    private String to;
    @Column(name = "content")
    private String content;
    @Column(name = "date")
    private Date date = new Date();
    @Column(name = "queue_name")
    private String queueName;

    public Message() {
    }

    public Message(String from, String to, String content, Date date, String queueName) {
        this.from = from;
        this.to = to;
        this.content = content;
        this.date = date;
        this.queueName = queueName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }
}
