package mx.edu.uaz.is.poo2.carger.model.daos;

import java.io.Serializable;
import jakarta.persistence.EntityManager;

import mx.edu.uaz.is.poo2.carger.model.entities.Player;

public class PlayerDAO extends DAO<Player> implements Serializable {
    private static final long serialVersionUID = 1L;

    public PlayerDAO(EntityManager entityManager) {
        super(Player.class, entityManager);
    }
}
