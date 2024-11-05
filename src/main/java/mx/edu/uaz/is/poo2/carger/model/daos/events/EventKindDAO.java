package mx.edu.uaz.is.poo2.carger.model.daos.events;

import java.io.Serializable;
import jakarta.persistence.EntityManager;

import mx.edu.uaz.is.poo2.carger.model.daos.DAO; 
import mx.edu.uaz.is.poo2.carger.model.entities.events.EventKind;

public class EventKindDAO extends DAO<EventKind> implements Serializable {
    private static final long serialVersionUID = 1L;

    public EventKindDAO(EntityManager entityManager) {
        super(EventKind.class, entityManager);
    }
}
