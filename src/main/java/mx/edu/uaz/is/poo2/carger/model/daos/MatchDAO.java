package mx.edu.uaz.is.poo2.carger.model.daos;

import java.io.Serializable;

import jakarta.persistence.EntityManager;
import mx.edu.uaz.is.poo2.carger.model.entities.*;

public class MatchDAO extends DAO<Match> implements Serializable {
    private static final long serialVersionUID = 1L;

    public MatchDAO(EntityManager entityManager) {
        super(Match.class, entityManager);
    }
}
