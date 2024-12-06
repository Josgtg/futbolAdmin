package mx.edu.uaz.is.poo2.carger.services.dao;

import java.util.Optional;

import jakarta.persistence.EntityManager;
import mx.edu.uaz.is.poo2.carger.model.daos.TeamDAO;
import mx.edu.uaz.is.poo2.carger.model.entities.Team;

public class TeamDAOService extends DAOService<Team> {
    public TeamDAOService(EntityManager em) {
        this.dao = new TeamDAO(em);
    }

    public Optional<Team> findByName(String name) {
        return this.dao.resultQuery("SELECT * FROM team WHERE name = ?;", name);
    }
}