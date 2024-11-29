package mx.edu.uaz.is.poo2.carger.controller;

import mx.edu.uaz.is.poo2.carger.view.Logger;
import mx.edu.uaz.is.poo2.carger.view.windows.consult.LeagueWindow;

public abstract class Controller {
    protected LeagueWindow leagueWindow;
    protected final Logger logger;

    public Controller() {
        this.logger = new Logger(this.getClass().getSimpleName());
    }

    public void startLeagueWindow() {
        this.leagueWindow.start();
    }

    public void setLeagueWindow(LeagueWindow window) {
        this.leagueWindow = window;
    }

    public void exit(int code) {
        System.exit(code);
    }
}