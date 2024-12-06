package mx.edu.uaz.is.poo2.carger.controller.view;

import java.util.List;

import mx.edu.uaz.is.poo2.carger.controller.*;
import mx.edu.uaz.is.poo2.carger.model.constants.columns.*;
import mx.edu.uaz.is.poo2.carger.model.constants.Messages;
import mx.edu.uaz.is.poo2.carger.services.dao.*;
import mx.edu.uaz.is.poo2.carger.model.entities.*;
import mx.edu.uaz.is.poo2.carger.model.entities.events.Event;
import mx.edu.uaz.is.poo2.carger.services.StatisticsGenerator;
import mx.edu.uaz.is.poo2.carger.view.windows.consult.*;
import mx.edu.uaz.is.poo2.carger.view.windows.selection.LoginWindow;

public class ConsultController extends Controller {
    private PlayerWindow playerWindow;
    private MatchWindow matchWindow;
    private TeamWindow teamWindow;
    private LoginWindow loginWindow;
    private EventWindow eventWindow;

    private final TeamDAOService teamDAOService;
    private final MatchDAOService matchDAOService;
    // private final PlayerDAOService playerDAOService;

    private boolean customLeagueWindowTeams;

    private final StatisticsGenerator statsGen;

    public ConsultController(TeamDAOService teamDAOService, MatchDAOService matchDAOService, PlayerDAOService playerDAOService) {
        super();
        this.teamDAOService = teamDAOService;
        this.matchDAOService = matchDAOService;
        // this.playerDAOService = playerDAOService;
        this.statsGen = StatisticsGenerator.getInstance();
        this.customLeagueWindowTeams = false;
    }

    @Override
    public void startLeagueWindow() {
        this.initUpdateLeague();
        this.leagueWindow.start();
    }

    // Window entities setters

    public void setLeagueWindowTeams() {
        if (!this.customLeagueWindowTeams)
            this.leagueWindow.setTeams(this.statsGen.getTeamsBy(LeagueColumns.POINTS));
        this.customLeagueWindowTeams = false;
    }

    public void setTeamsBy(LeagueColumns column, boolean reversed) {
        this.leagueWindow.setTeams(this.statsGen.getTeamsBy(column, reversed));
        this.customLeagueWindowTeams = true;
    }

    public void setMatchWindowMatches() {
        var matches = this.matchDAOService.findAll();
        this.matchWindow.setMatches(matches);
    }

    public void setTeamWindowTeams() {
        var teams = this.teamDAOService.findAll();
        this.teamWindow.setTeams(teams);
    }

    public void setPlayerWindowPlayers() {
        var players = this.statsGen.getPlayersBy(PlayerColumns.GOALS);
        this.playerWindow.setPlayers(players);
    }

    public void setPlayersBy(PlayerColumns column, boolean reversed) {
        var players = this.statsGen.getPlayersBy(column, reversed);
        this.playerWindow.setPlayers(players);
    }

    // LoginWindow

    public void startLoginWindow() {
        this.loginWindow.start();
    }

    public void setLoginWindow(LoginWindow window) {
        this.loginWindow = window;
    }

    // MatchWindow

    public void startMatchWindow() {
        this.matchWindow.start();
    }

    public void startMatchWindow(Match match) {
        this.matchWindow.start(match);
    }

    public void setMatchWindow(MatchWindow window) {
        this.matchWindow = window;
    }

    // EventWindow

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

    // PlayerWindow

    public void startPlayerWindow() {
        this.playerWindow.start();
    }

    public void startPlayerWindow(int amount, boolean showId) {
        this.playerWindow.start(amount, showId);
    }

    public void startPlayerWindow(Player player) {
        this.playerWindow.start(player);
    }

    public void setPlayerWindow(PlayerWindow window) {
        this.playerWindow = window;
    }

    // TeamWindow

    public void startTeamWindow() {
        this.teamWindow.start();
    }

    public void startTeamWindow(Team team) {
        this.teamWindow.start(team);
    }

    public void setTeamWindow(TeamWindow window) {
        this.teamWindow = window;
    }
    
    private void initUpdateLeague() {
        this.logger.info("Calculando estadísticas...");
        statsGen.updateLeague();
        this.logger.info(Messages.READY);
    }

    public void updateLeague() {
        statsGen.updateLeague();
    }
}