package mx.edu.uaz.is.poo2.carger.services.dao;

import jakarta.persistence.EntityManager;
import mx.edu.uaz.is.poo2.carger.model.daos.events.EventDAO;
import mx.edu.uaz.is.poo2.carger.model.entities.events.Event;

public class EventDAOService extends DAOService<Event> {
    public EventDAOService(EntityManager em) {
        this.dao = new EventDAO(em);
    }
}