package mx.edu.uaz.is.poo2.carger.services.dao;

import jakarta.persistence.EntityManager;
import mx.edu.uaz.is.poo2.carger.model.daos.MatchDAO;
import mx.edu.uaz.is.poo2.carger.model.entities.Match;

public class MatchDAOService extends DAOService<Match> {
    public MatchDAOService(EntityManager em) {
        this.dao = new MatchDAO(em);
    }
}