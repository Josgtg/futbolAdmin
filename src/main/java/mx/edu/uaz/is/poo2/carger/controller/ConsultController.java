package mx.edu.uaz.is.poo2.carger.controller;

import mx.edu.uaz.is.poo2.carger.model.entities.Match;
import static mx.edu.uaz.is.poo2.carger.view.windows.WindowSignal.*;
import mx.edu.uaz.is.poo2.carger.model.entities.Player;
import mx.edu.uaz.is.poo2.carger.model.entities.Team;
import mx.edu.uaz.is.poo2.carger.view.windows.*;
import mx.edu.uaz.is.poo2.carger.view.windows.WindowSignal;

public class ConsultController extends Controller {
    private LeagueWindow leagueWindow;
    private TeamWindow teamWindow;
    private PlayerWindow playerWindow;
    private MatchWindow matchWindow;
    private LoginWindow loginWindow;

    public ConsultController() {
        super();
    }

    public void start() {
        this.leagueWindow.mainView();
    }

    @Override
    public void windowAction(WindowSignal signal) {
        switch (signal.actionCode) {
            case EXIT -> {
                this.close();
                System.exit(0);
            }
            case CLOSE -> this.close();
            case RETURN_TO_MAIN -> {
                this.close();
                this.leagueWindow.mainView();
            }
            case OPEN_TEAM -> this.teamWindow.showTeamPlayers((Team) signal.entities.get(0));
            case OPEN_PLAYER -> this.playerWindow.showPlayer((Player) signal.entities.get(0));
            case OPEN_MATCHES -> this.matchWindow.mainView();
            case OPEN_MATCH -> this.matchWindow.showMatch((Match) signal.entities.get(0));
            case ENTER_AS_ADMIN -> {
                this.close();
                this.loginWindow.mainView();
            }
            default -> {
                this.close();
                this.logger.warn(String.format("WindowSignal %s no válido para esta ventana", signal));
            }
        }
    }

    @Override
    public void close() {
        this.leagueWindow.close();
        this.teamWindow.close();
        this.playerWindow.close();
        this.matchWindow.close();
    }

    public void setLeagueWindow(LeagueWindow window) {
        this.leagueWindow = window;
    }

    public void setLoginWindow(LoginWindow window) {
        this.loginWindow = window;
    }

    public void setMatchWindow(MatchWindow window) {
        this.matchWindow = window;
    }

    public void setPlayerWindow(PlayerWindow window) {
        this.playerWindow = window;
    }

    public void setTeamWindow(TeamWindow window) {
        this.teamWindow = window;
    }
}