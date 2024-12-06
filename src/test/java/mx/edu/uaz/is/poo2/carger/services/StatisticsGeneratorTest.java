package mx.edu.uaz.is.poo2.carger.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.*;
import static org.junit.Assert.*;

import mx.edu.uaz.is.poo2.carger.model.entities.*;
import mx.edu.uaz.is.poo2.carger.model.entities.events.*;
import mx.edu.uaz.is.poo2.carger.services.dao.*;

public class StatisticsGeneratorTest {

    TeamDAOService tdao;
    // PlayerDAOService pdao;
    MatchDAOService mdao;

    Match match;

    StatisticsGenerator sg;

    public StatisticsGeneratorTest() {
        this.tdao = DAOServiceFactory.getTeamDAOService();
        // this.pdao = DAOServiceFactory.getPlayerDAOService();
        this.mdao = DAOServiceFactory.getMatchDAOService();

        this.sg = StatisticsGenerator.getInstance();
    }

    @Before
    public void createEntities() {
        Team home = new Team("Barcelona");
        Player messi = new Player("Messi", 30);
        Player neymar = new Player("Neymar", 22);
        home.addPlayer(messi);
        home.addPlayer(neymar);

        Team away = new Team("Madrid");
        Player ronaldo = new Player("Ronaldo", 34);
        Player mbappe = new Player("Mbappé", 26);
        away.addPlayer(ronaldo);
        away.addPlayer(mbappe);

        home = tdao.save(home).get();
        home.getPlayers().size();
        away = tdao.save(away).get();
        away.getPlayers().size();

        Match m = new Match(Timestamp.valueOf(LocalDateTime.now()), 1, home, away);
        m.setPlayed(true);
        m.addEvent(new Event(EventKind.GOAL, home.getPlayers().get(0), home.getPlayers().get(1), 23));
        m.addEvent(new Event(EventKind.GOAL, home.getPlayers().get(0), home.getPlayers().get(1), 45));
        m.addEvent(new Event(EventKind.GOAL, home.getPlayers().get(1), 90));
        // Messi has 2 goals, Neymar 1 goal and 2 assists

        m.addEvent(new Event(EventKind.GOAL, away.getPlayers().get(1), away.getPlayers().get(0), 44));
        m.addEvent(new Event(EventKind.YELLOW_CARD, away.getPlayers().get(0), 22));
        m.addEvent(new Event(EventKind.RED_CARD, away.getPlayers().get(1), 85));
        // Mbappe has 1 goal and 1 red card, Ronaldo 1 assist 1 yellow card

        this.match = mdao.save(m).get();
    }

    @Test
    public void testPlayerStatsGenerator() {
        this.sg.updateLeague();
        Team home = tdao.findByName("Barcelona").get();
        home.getPlayers().size();
        Team away = tdao.findByName("Madrid").get();
        away.getPlayers().size();

        System.out.println(home);
        System.out.println(away);

        assertEquals(1, home.getWins());
        assertEquals(0, home.getDraws());
        assertEquals(0, home.getLosses());
        assertEquals(3, home.getGoalsScored());
        assertEquals(1, home.getGoalsReceived());

        assertEquals(0, away.getWins());
        assertEquals(0, away.getDraws());
        assertEquals(1, away.getLosses());
        assertEquals(1, away.getGoalsScored());
        assertEquals(3, away.getGoalsReceived());

        assertEquals(2, home.getPlayers().get(0).getGoals()); // Messi goals
        assertEquals(1, home.getPlayers().get(1).getGoals()); // Neymar goals
        assertEquals(2, home.getPlayers().get(1).getAssists()); // Neymar assists

        assertEquals(1, away.getPlayers().get(0).getAssists()); // Ronaldo assist
        assertEquals(1, away.getPlayers().get(0).getYellowCards()); // Ronaldo yellow card
        assertEquals(1, away.getPlayers().get(1).getGoals()); // Mbappé goal
        assertEquals(1, away.getPlayers().get(1).getRedCards()); // Mbappé red card

        testMatchReverting();
    }

    private void testMatchReverting() {
        this.sg.revertMatch(this.match);
        
        Team home = tdao.findByName("Barcelona").get();
        home.getPlayers().size();
        Team away = tdao.findByName("Madrid").get();
        away.getPlayers().size();
        
        System.out.println(home);
        System.out.println(away);

        assertEquals(0, home.getWins());
        assertEquals(0, home.getDraws());
        assertEquals(0, home.getLosses());
        assertEquals(0, home.getGoalsScored());
        assertEquals(0, home.getGoalsReceived());

        assertEquals(0, away.getWins());
        assertEquals(0, away.getDraws());
        assertEquals(0, away.getLosses());
        assertEquals(0, away.getGoalsScored());
        assertEquals(0, away.getGoalsReceived());

        assertEquals(0, home.getPlayers().get(0).getGoals());
        assertEquals(0, home.getPlayers().get(1).getGoals());
        assertEquals(0, home.getPlayers().get(1).getAssists());

        assertEquals(0, away.getPlayers().get(0).getAssists());
        assertEquals(0, away.getPlayers().get(0).getYellowCards());
        assertEquals(0, away.getPlayers().get(1).getGoals());
        assertEquals(0, away.getPlayers().get(1).getRedCards());
    }
}