package mx.edu.uaz.is.poo2.carger.services;


import java.util.ArrayList;
import java.util.List;
import org.junit.*;


import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import mx.edu.uaz.is.poo2.carger.model.daos.MatchDAO;
import mx.edu.uaz.is.poo2.carger.model.entities.Match;
import mx.edu.uaz.is.poo2.carger.model.entities.Team;

public class PruebaMatchGeneratorTest {
    @Test
    public void testGen() {
        EntityManager em = Persistence.createEntityManagerFactory("UPFutbolAdmin").createEntityManager();
        MatchDAO mdao = new MatchDAO(em);


        ArrayList<Team> equipos = new ArrayList<>();
        List<Match> calendar;
        equipos.add(new Team("madrid"));
        equipos.add(new Team("barca"));
        equipos.add(new Team("manchester"));
        equipos.add(new Team("liverpool"));
        equipos.add(new Team("chivas"));
        equipos.add(new Team("america"));
        equipos.add(new Team("juarez"));
        equipos.add(new Team("mineros"));
        equipos.add(new Team("chelsea"));
        //equipos.add(new Team("paris"));

        MatchGenerator matchGenerator = new MatchGenerator(mdao);
        matchGenerator.generateMatches(equipos);
        calendar = mdao.findAll();
        for (Match match : calendar) {
            System.out.println("date: " + match.getDate() +", gameweek: " +match.getGameWeek()+", "+ match.getHomeTeam().getName() +" vs " + match.getAwayTeam().getName() );    
        }
        

    }
}
