package mx.edu.uaz.is.poo2.carger.model.daos;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import mx.edu.uaz.is.poo2.carger.model.entities.Player;
import mx.edu.uaz.is.poo2.carger.model.entities.Team;

public class PlayerDAOTest {

    EntityManager em;
    PlayerDAO dao;
    Player p;

    @Before
    public void crearObjetoPrueba(){
       
    }

    @Test
    public void save() {
        em = Persistence.createEntityManagerFactory("UPFutbolAdmin").createEntityManager();
        dao = new PlayerDAO(em);
        // 
        p = new Player("Juan", 23,new Team("Madrid"));
        assertNotNull(p);
        assertNull(p.getId());
        var resultado = dao.save(p);
        assertNotNull(resultado.getId());
        //assertEquals(p, dao.findByID(p.getId()));
    }
}
    