package com.websocket.websocket.repository;

import com.websocket.websocket.interfaces.MessageRepository;
import com.websocket.websocket.models.Message;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class MessageRepo implements MessageRepository {

    private final EntityManager manager;

    public MessageRepo(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public List<Message> getCorrespondence(String from, String to) {
        return Optional.of(manager
                .createQuery("from Message m where m.from = :from and m.to = :to order by m.date asc")
                .setParameter("from", from).setParameter("to", to).getResultList()).orElseThrow(() -> {
            throw new NoResultException("Correspondence didn't found");
        });
    }

    @Override
    @Transactional
    public void save(Message message) {
        manager.persist(message);
    }
}
