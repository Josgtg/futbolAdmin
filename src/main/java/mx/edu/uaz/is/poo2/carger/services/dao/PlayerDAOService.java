package mx.edu.uaz.is.poo2.carger.services.dao;

import java.util.Optional;

import jakarta.persistence.EntityManager;
import mx.edu.uaz.is.poo2.carger.model.daos.PlayerDAO;
import mx.edu.uaz.is.poo2.carger.model.entities.Player;

public class PlayerDAOService extends DAOService<Player> {
    public PlayerDAOService(EntityManager em) {
        this.dao = new PlayerDAO(em);
    }

    public Optional<Player> findByName(String name) {
        Optional team = Optional.empty();
        for (Player t : this.findAll()) {
            if (t.getName().equals(name))
                team = Optional.of(t);
        }
        return team;
    }
}