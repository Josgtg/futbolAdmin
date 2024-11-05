package mx.edu.uaz.is.poo2.carger.model.daos.events;

import java.io.Serializable;
import jakarta.persistence.EntityManager;

import mx.edu.uaz.is.poo2.carger.model.daos.DAO;
import mx.edu.uaz.is.poo2.carger.model.entities.events.Event;

public class EventDAO extends DAO<Event> implements Serializable {
    private static final long serialVersionUID = 1L;

    public EventDAO(EntityManager entityManager) {
        super(Event.class, entityManager);
    }
}
