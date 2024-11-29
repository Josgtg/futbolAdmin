package mx.edu.uaz.is.poo2.carger.controller.view;

import mx.edu.uaz.is.poo2.carger.controller.*;
import mx.edu.uaz.is.poo2.carger.view.windows.MatchFillWindow;
import mx.edu.uaz.is.poo2.carger.view.windows.schedule.ScheduleWindow;
import mx.edu.uaz.is.poo2.carger.view.windows.selection.*;

public class LoginController extends Controller {
    LoginWindow loginWindow;
    AdminWindow adminWindow;
    CRUDSelectWindow crudSelectWindow;
    ScheduleWindow scheduleWindow;
    MatchFillWindow matchFillWindow;

    public LoginController() {
        super();
    }

    public void startLoginWindow() {
        this.loginWindow.start();
    }

    public void setLoginWindow(LoginWindow window) {
        this.loginWindow = window;
    }

    public void startAdminWindow() {
        this.adminWindow.start();
    }

    public void setAdminWindow(AdminWindow window) {
        this.adminWindow = window;
    }

    public void startCRUDSelectWindow() {
        this.crudSelectWindow.start();
    }

    public void setCRUDSelectWindow(CRUDSelectWindow window) {
        this.crudSelectWindow = window;
    }

    public void startScheduleWindow() {
        this.scheduleWindow.start();
    }

    public void setScheduleWindow(ScheduleWindow window) {
        this.scheduleWindow = window;
    }

    public void startMatchFillWindow() {
        this.matchFillWindow.start();
    }

    public void setMatchFillWindow(MatchFillWindow window) {
        this.matchFillWindow = window;
    }
}