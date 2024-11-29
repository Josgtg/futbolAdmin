package mx.edu.uaz.is.poo2.carger.view.windows;

import java.util.ArrayList;
import java.util.List;

import mx.edu.uaz.is.poo2.carger.model.entities.*;
import mx.edu.uaz.is.poo2.carger.view.windows.consult.*;
import mx.edu.uaz.is.poo2.carger.view.windows.schedule.*;
import mx.edu.uaz.is.poo2.carger.view.windows.selection.*;
import mx.edu.uaz.is.poo2.carger.view.windows.crud.*;

public class WindowCreator {

    // Consult
    private static LeagueWindow leagueWindow;
    private static MatchWindow matchWindow;
    private static PlayerWindow playerWindow;
    private static TeamWindow teamWindow;
    private static EventWindow eventWindow;
    // Login
    private static LoginWindow loginWindow;
    private static AdminWindow adminWindow;
    // CRUDSelect
    private static CRUDSelectWindow crudSelectWindow;
    // CRUD
    private static MatchCRUDWindow matchCRUDWindow;
    private static PlayerCRUDWindow playerCRUDWindow;
    private static TeamCRUDWindow teamCRUDWindow;
    // Schedule
    private static ScheduleWindow scheduleWindow;
    private static MatchAdminWindow matchAdminWindow;
    // MatchFill
    private static MatchFillWindow matchFillWindow;

    // ConsultController

    public static LeagueWindow getLeagueWindow(List<Team> teams) {
        // Teams are already in order
        if (leagueWindow == null)
            leagueWindow = new LeagueWindow(teams);
        return leagueWindow;
    }

    public static LeagueWindow getLeagueWindow() {
        return getLeagueWindow(new ArrayList<>());
    }

    public static MatchWindow getMatchWindow(List<Match> matches) {
        if (matchWindow == null)
            matchWindow = new MatchWindow(matches);
        return matchWindow;
    }

    public static MatchWindow getMatchWindow() {
        return getMatchWindow(new ArrayList<>());
    }

    public static PlayerWindow getPlayerWindow() {
        if (playerWindow == null)
            playerWindow = new PlayerWindow();
        return playerWindow;
    }

    public static TeamWindow getTeamWindow() {
        if (teamWindow == null)
            teamWindow = new TeamWindow();
        return teamWindow;
    }

    public static EventWindow getEventWindow() {
        if (eventWindow == null)
            eventWindow = new EventWindow();
        return eventWindow;
    }

    // 

    public static LoginWindow getLoginWindow() {
        if (loginWindow == null)
            loginWindow = new LoginWindow();
        return loginWindow;
    }


    public static AdminWindow getAdminWindow() {
        if (adminWindow == null)
            adminWindow = new AdminWindow();
        return adminWindow;
    }

    // CRUDSelect

    public static CRUDSelectWindow getCRUDSelectWindow() {
        if (crudSelectWindow == null)
            crudSelectWindow = new CRUDSelectWindow();
        return crudSelectWindow;
    }

    // CRUD

    public static MatchCRUDWindow getMatchCRUDWindow() {
        if (matchCRUDWindow == null)
            matchCRUDWindow = new MatchCRUDWindow();
        return matchCRUDWindow;
    }

    public static PlayerCRUDWindow getPlayerCRUDWindow() {
        if (playerCRUDWindow == null)
            playerCRUDWindow = new PlayerCRUDWindow();
        return playerCRUDWindow;
    }

    public static TeamCRUDWindow getTeamCRUDWindow() {
        if (teamCRUDWindow == null)
            teamCRUDWindow = new TeamCRUDWindow();
        return teamCRUDWindow;
    }

    // Schedule
    public static ScheduleWindow getScheduleWindow() {
        if (scheduleWindow == null)
            scheduleWindow = new ScheduleWindow();
        return scheduleWindow;
    }

    public static MatchAdminWindow getMatchAdminWindow() {
        if (matchAdminWindow == null)
            matchAdminWindow = new MatchAdminWindow();
        return matchAdminWindow;
    }

    //MatchFill
    public static MatchFillWindow getMatchFillWindow() {
        if (matchFillWindow == null){
            matchFillWindow = new MatchFillWindow();
        }
        return matchFillWindow;
    }
}