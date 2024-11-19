package mx.edu.uaz.is.poo2.carger;

import java.util.ArrayList;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import mx.edu.uaz.is.poo2.carger.controller.ConsultController;
import mx.edu.uaz.is.poo2.carger.controller.ControllerCreator;
import mx.edu.uaz.is.poo2.carger.model.daos.TeamDAO;
import mx.edu.uaz.is.poo2.carger.model.entities.Player;
import mx.edu.uaz.is.poo2.carger.model.entities.Team;

public class App {
    public static void main(String[] args) {
        EntityManager em = Persistence.createEntityManagerFactory("UPFutbolAdmin").createEntityManager();
        TeamDAO tdao = new TeamDAO(em);
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player());
        var t = new Team("Madri", players);
        tdao.save(t);

        ControllerCreator creator = new ControllerCreator();
        creator.initializeAll(tdao.findAll(), new ArrayList<>());

        ConsultController consultCtrl = creator.getConsultController();

        consultCtrl.start();
    }
}
