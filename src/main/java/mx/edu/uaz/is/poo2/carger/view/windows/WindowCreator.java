package mx.edu.uaz.is.poo2.carger.view.windows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import mx.edu.uaz.is.poo2.carger.controller.Controller;
import mx.edu.uaz.is.poo2.carger.model.entities.Match;
import mx.edu.uaz.is.poo2.carger.model.entities.Team;

public class WindowCreator {

    private Optional<LeagueWindow> leagueWindow;
    private Optional<LoginWindow> loginWindow;
    private Optional<MatchWindow> matchWindow;
    private Optional<PlayerWindow> playerWindow;
    private Optional<TeamWindow> teamWindow;

    public WindowCreator() {
        this.leagueWindow = Optional.empty();
        this.loginWindow = Optional.empty();
        this.matchWindow = Optional.empty();
        this.playerWindow = Optional.empty();
        this.teamWindow = Optional.empty();
    }

    public LeagueWindow getLeagueWindow(Controller controller, List<Team> teams) {
        // Teams are already in order
        if (this.leagueWindow.isEmpty())
            this.leagueWindow = Optional.of(new LeagueWindow(controller, teams));
        return this.leagueWindow.get();
    }

    public LeagueWindow getLeagueWindow(Controller controller) {
        return this.getLeagueWindow(controller, new ArrayList<>());
    }

    public LoginWindow getLoginWindow(Controller controller) {
        if (this.loginWindow.isEmpty())
            this.loginWindow = Optional.of(new LoginWindow(controller));
        return this.loginWindow.get();
    }

    public MatchWindow getMatchWindow(Controller controller, List<Match> matches) {
        if (this.matchWindow.isEmpty())
            this.matchWindow = Optional.of(new MatchWindow(controller, matches));
        return this.matchWindow.get();
    }

    public MatchWindow getMatchWindow(Controller controller) {
        return this.getMatchWindow(controller, new ArrayList<>());
    }

    public PlayerWindow getPlayerWindow(Controller controller) {
        if (this.playerWindow.isEmpty())
            this.playerWindow = Optional.of(new PlayerWindow(controller));
        return this.playerWindow.get();
    }

    public TeamWindow getTeamWindow(Controller controller) {
        if (this.teamWindow.isEmpty())
            this.teamWindow = Optional.of(new TeamWindow(controller));
        return this.teamWindow.get();
    }
}