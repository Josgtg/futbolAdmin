package mx.edu.uaz.is.poo2.carger.model.daos;

import org.junit.Test;
// import static org.junit.Assert.*;
import org.junit.Before;
import java.sql.Timestamp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import mx.edu.uaz.is.poo2.carger.model.entities.Match;

import mx.edu.uaz.is.poo2.carger.model.entities.Player;
import mx.edu.uaz.is.poo2.carger.model.entities.Team;

public class PlayerDAOTest {

    EntityManager em;
    PlayerDAO pdao;
    TeamDAO tdao;
    MatchDAO mdao;
    Player p;
    Team t;
    Match m;

    @Before
    public void inicializar(){
        this.em = Persistence.createEntityManagerFactory("UPFutbolAdmin").createEntityManager();
        this.pdao = new PlayerDAO(this.em);
        this.tdao = new TeamDAO(this.em);
        this.mdao = new MatchDAO(this.em);
        this.p = new Player("Juan", 19);
        this.t = new Team("Urracas de Jerez");
        this.m = new Match(Timestamp.valueOf("2024-12-23 00:00:00"), 1);
    }

    @Test
    public void save() {
        // Orden en el que sí jala
        pdao.save(p);
        p.setTeam(t);
        t.addPlayer(p);
        tdao.save(t);
        m.setAwayTeam(t);
        m.setHomeTeam(t);
        mdao.save(m);
        //assertEquals(p, dao.findByID(p.getId()));
    }
}
    