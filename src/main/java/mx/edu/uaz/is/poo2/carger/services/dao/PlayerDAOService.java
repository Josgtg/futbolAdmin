package mx.edu.uaz.is.poo2.carger.services.dao;

import java.util.Optional;

import jakarta.persistence.EntityManager;
import mx.edu.uaz.is.poo2.carger.model.daos.PlayerDAO;
import mx.edu.uaz.is.poo2.carger.model.entities.Player;
import mx.edu.uaz.is.poo2.carger.model.entities.Team;

public class PlayerDAOService extends DAOService<Player> {
    public PlayerDAOService(EntityManager em) {
        this.dao = new PlayerDAO(em);
    }

    public Optional<Player> findByName(String name) {
        return this.dao.resultQuery("SELECT * FROM player WHERE name = ?;", name);
    }

    public Optional<Player> findByTeam(Team team) {
        return this.dao.resultQuery("SELECT * FROM player WHERE team_id = ?;", team.getId());
    }
}