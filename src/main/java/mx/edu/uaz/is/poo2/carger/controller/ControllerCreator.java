package mx.edu.uaz.is.poo2.carger.controller;

import java.util.ArrayList;
import java.util.List;

import mx.edu.uaz.is.poo2.carger.model.entities.Match;
import mx.edu.uaz.is.poo2.carger.model.entities.Team;
import mx.edu.uaz.is.poo2.carger.view.windows.*;

public class ControllerCreator {
    private final PrivateController<ConsultController> consultController;
    private final PrivateController<LoginController> loginController;
    private final WindowCreator windowCreator;

    public ControllerCreator() {
        this.consultController = new PrivateController(new ConsultController());
        this.loginController = new PrivateController(new LoginController());
        this.windowCreator = new WindowCreator();
    }

    public ConsultController getConsultController(List<Team> leagueTeams, List<Match> matches) {
        if (!this.consultController.isInitialized) {
            this.consultController.ctrl.setLeagueWindow(this.windowCreator.getLeagueWindow(this.consultController.ctrl, leagueTeams));
            this.consultController.ctrl.setMatchWindow(this.windowCreator.getMatchWindow(this.consultController.ctrl, matches));
            this.consultController.ctrl.setPlayerWindow(this.windowCreator.getPlayerWindow(this.consultController.ctrl));
            this.consultController.ctrl.setTeamWindow(this.windowCreator.getTeamWindow(this.consultController.ctrl));
            this.consultController.ctrl.setLoginWindow(this.windowCreator.getLoginWindow(this.getLoginController()));
            this.consultController.isInitialized = true;
        }
        return this.consultController.ctrl;
    }

    public ConsultController getConsultController() {
        return this.getConsultController(new ArrayList<>(), new ArrayList<>());
    }

    public LoginController getLoginController() {
        if (!this.loginController.isInitialized) {
            this.loginController.ctrl.setLoginWindow(this.windowCreator.getLoginWindow(this.loginController.ctrl));
            this.loginController.ctrl.setLeagueWindow(this.windowCreator.getLeagueWindow(this.consultController.ctrl));
            this.loginController.isInitialized = true;
        }
        return this.loginController.ctrl;
    }

    public void initializeAll(List<Team> leagueTeams, List<Match> matches) {
        this.getConsultController(leagueTeams, matches);
        this.getLoginController();
    }
}

class PrivateController<C extends Controller> {
    public C ctrl;
    public Boolean isInitialized;

    public PrivateController(C ctrl) {
        this.ctrl = ctrl;
        this.isInitialized = false;
    }
}