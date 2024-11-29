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
        Optional team = Optional.empty();
        for (Team t : this.findAll()) {
            if (t.getName().equals(name))
                team = Optional.of(t);
        }
        return team;
    }
}