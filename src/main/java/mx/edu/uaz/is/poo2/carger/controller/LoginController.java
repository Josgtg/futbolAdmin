package mx.edu.uaz.is.poo2.carger.controller;

import mx.edu.uaz.is.poo2.carger.view.windows.LeagueWindow;
import mx.edu.uaz.is.poo2.carger.view.windows.LoginWindow;
import mx.edu.uaz.is.poo2.carger.view.windows.WindowSignal;

public class LoginController extends Controller {
    LeagueWindow leagueWindow;
    LoginWindow loginWindow;

    public LoginController() {
        super();
    }

    @Override
    public void windowAction(WindowSignal signal) {
        switch (signal.actionCode) {
            case WindowSignal.CLOSE -> this.close();
            case WindowSignal.RETURN_TO_MAIN -> {
                this.close();
                this.leagueWindow.mainView();
            }
            default -> {
                this.close();
                this.logger.warn(String.format("WindowSignal %s no válido para esta ventana", signal));
            }
        }
    }

    @Override
    public void close() {
        this.loginWindow.close();  // Ocultar las ventanas en GUI
    }

    public void setLeagueWindow(LeagueWindow window) {
        this.leagueWindow = window;
    }

    public void setLoginWindow(LoginWindow window) {
        this.loginWindow = window;
    }
}