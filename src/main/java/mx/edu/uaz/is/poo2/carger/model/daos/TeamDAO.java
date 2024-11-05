package mx.edu.uaz.is.poo2.carger.model.daos;

import java.io.Serializable;

import jakarta.persistence.EntityManager;
import mx.edu.uaz.is.poo2.carger.model.entities.Team;

public class TeamDAO extends DAO<Team> implements Serializable{
    private static final long serialVersionUID = 1L;

    public TeamDAO(EntityManager entityManager) {
        super(Team.class, entityManager);
    }
}
