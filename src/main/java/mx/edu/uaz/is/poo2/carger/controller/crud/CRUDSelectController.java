package mx.edu.uaz.is.poo2.carger.controller.crud;

import mx.edu.uaz.is.poo2.carger.controller.*;
import mx.edu.uaz.is.poo2.carger.model.constants.CRUDTable;
import mx.edu.uaz.is.poo2.carger.view.windows.consult.EventWindow;
import mx.edu.uaz.is.poo2.carger.view.windows.crud.MatchCRUDWindow;
import mx.edu.uaz.is.poo2.carger.view.windows.crud.PlayerCRUDWindow;
import mx.edu.uaz.is.poo2.carger.view.windows.crud.TeamCRUDWindow;
import mx.edu.uaz.is.poo2.carger.view.windows.selection.AdminWindow;
import mx.edu.uaz.is.poo2.carger.view.windows.selection.CRUDSelectWindow;

public class CRUDSelectController extends Controller {
    private CRUDSelectWindow crudSelectWindow;
    private AdminWindow adminWindow;

    private TeamCRUDWindow teamWindow;
    private PlayerCRUDWindow playerWindow;
    private MatchCRUDWindow matchWindow;
    private EventWindow eventWindow;

    public CRUDSelectController() {
        super();
    }

    public void startCRUDSelectWindow() {
        this.crudSelectWindow.start();
    }

    public void setCRUDSelectWindow(CRUDSelectWindow window) {
        this.crudSelectWindow = window;
    }

    public void startAdminWindow() {
        this.adminWindow.start();
    }

    public void setAdminWindow(AdminWindow window) {
        this.adminWindow = window;
    }

    public void startCRUDWindow(CRUDTable table) {
        switch (table) {
            case TEAM -> this.teamWindow.start();
            case PLAYER -> this.playerWindow.start();
            case MATCH -> this.matchWindow.start();
            case EVENT -> this.eventWindow.start();
        }
    }

    public void setTeamWindow(TeamCRUDWindow window) {
        this.teamWindow = window;
    }

    public void setPlayerWindow(PlayerCRUDWindow window) {
        this.playerWindow = window;
    }

    public void setMatchWindow(MatchCRUDWindow window) {
        this.matchWindow = window;
    }

    public void setEventWindow(EventWindow window) {
        this.eventWindow = window;
    }
}