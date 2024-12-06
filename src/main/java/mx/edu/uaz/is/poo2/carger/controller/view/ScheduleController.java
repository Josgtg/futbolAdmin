package mx.edu.uaz.is.poo2.carger.controller.view;

import java.util.List;

import mx.edu.uaz.is.poo2.carger.controller.*;
import mx.edu.uaz.is.poo2.carger.services.dao.*;
import mx.edu.uaz.is.poo2.carger.model.entities.*;
import mx.edu.uaz.is.poo2.carger.model.entities.events.Event;
import mx.edu.uaz.is.poo2.carger.services.MatchGenerator;
import mx.edu.uaz.is.poo2.carger.services.StatisticsGenerator;
import mx.edu.uaz.is.poo2.carger.view.windows.consult.EventWindow;
import mx.edu.uaz.is.poo2.carger.view.windows.schedule.MatchAdminWindow;

public class ScheduleController extends Controller {
    private final MatchDAOService daoM;
    private final TeamDAOService daoT;

    private MatchAdminWindow matchWindow;
    private final MatchGenerator matchGenerator;
    private EventWindow eventWindow;
    private final StatisticsGenerator statisticsGenerator;

    public ScheduleController(MatchDAOService matchDAOService, TeamDAOService teamDAOService) {
        super();
        this.daoM = matchDAOService;
        this.daoT = teamDAOService;
        this.matchGenerator = new MatchGenerator(daoM);
        this.statisticsGenerator = StatisticsGenerator.getInstance();
    }

    public void generateMatches(){
        this.deleteMatches();
        this.logger.info("Generando nuevos partidos...");
        List<Team> teams = this.daoT.findAll();
        matchGenerator.generateMatches(teams);
    }

    public void deleteMatches(){
        this.logger.info("Borrando todos los partidos...");
        List<Match> matches = this.daoM.findAll();
        for (Match match : matches) {
            statisticsGenerator.revertMatch(match);
        }

        this.daoM.deleteAll();
    }

    public void startMatchWindow() {
        this.matchWindow.start();
    }

    public void startMatchWindow(Match match) {
        this.matchWindow.start(match);
    }

    public void setMatchWindow(MatchAdminWindow window) {
        this.matchWindow = window;
    }
    public void setMatchWindowMatches(){
        var matches = this.daoM.findAll();
        this.matchWindow.setMatches(matches);
    }

    public void startEventWindow(List<Event> events) {
        this.eventWindow.setEvents(events);
        this.eventWindow.start();
    }

    public void startEventWindow(Event event) {
        this.eventWindow.start(event);
    }

    public void setEventWindow(EventWindow window) {
        this.eventWindow = window;
    }

    public List<Team> getTeams() {
        return this.daoT.findAll();
    }
}