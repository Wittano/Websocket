package com.wittano.websocket.repository;

import com.wittano.websocket.models.entity.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {
    @Query("from Message m where m.from.username = ?1 and m.to.username = ?2 order by m.date asc")
    List<Message> getCorrespondence(String from, String to);
}
