package mx.edu.uaz.is.poo2.carger.controller;

import static mx.edu.uaz.is.poo2.carger.view.windows.WindowCreator.*;
import mx.edu.uaz.is.poo2.carger.model.entities.*;
import mx.edu.uaz.is.poo2.carger.controller.crud.*;
import mx.edu.uaz.is.poo2.carger.controller.view.*;
import mx.edu.uaz.is.poo2.carger.model.constants.CRUDTable;
import static mx.edu.uaz.is.poo2.carger.services.dao.DAOServiceFactory.*;
import mx.edu.uaz.is.poo2.carger.view.Logger;

public class ControllerCreator {
    private static ConsultController consultController;
    private static LoginController loginController;
    private static CRUDSelectController crudSelectController;
    private static ScheduleController scheduleController;
    private static MatchFillController matchFillController;

    private static final Logger logger = new Logger(ControllerCreator.class.getSimpleName());

    public static ConsultController getConsultController() {
        if (consultController == null) {
            consultController = new ConsultController(getTeamDAOService(), getMatchDAOService(), getPlayerDAOService());

            consultController.setLeagueWindow(getLeagueWindow());
            getLeagueWindow().setController(consultController);

            consultController.setMatchWindow(getMatchWindow());
            getMatchWindow().setController(consultController);

            consultController.setPlayerWindow(getPlayerWindow());
            getPlayerWindow().setController(consultController);

            consultController.setTeamWindow(getTeamWindow());
            getTeamWindow().setController(consultController);

            consultController.setEventWindow(getEventWindow());
            getEventWindow().setController(consultController);

            consultController.setLoginWindow(getLoginWindow());


        }
        return consultController;
    }

    public static LoginController getLoginController() {
        if (loginController == null) {
            loginController = new LoginController();
            
            loginController.setLeagueWindow(getLeagueWindow());
            
            loginController.setLoginWindow(getLoginWindow());
            getLoginWindow().setController(loginController);
            
            loginController.setAdminWindow(getAdminWindow());
            getAdminWindow().setController(loginController);

            loginController.setScheduleWindow(getScheduleWindow());

            loginController.setCRUDSelectWindow(getCRUDSelectWindow());

            loginController.setMatchFillWindow(getMatchFillWindow());
        }
        return loginController;
    }

    public static CRUDSelectController getCRUDSelectController() {
        if (crudSelectController == null) {
            crudSelectController = new CRUDSelectController();

            crudSelectController.setLeagueWindow(getLeagueWindow());
            crudSelectController.setAdminWindow(getAdminWindow());

            crudSelectController.setTeamWindow(getTeamCRUDWindow());
            crudSelectController.setPlayerWindow(getPlayerCRUDWindow());
            crudSelectController.setMatchWindow(getMatchCRUDWindow());
            crudSelectController.setEventWindow(getEventWindow());

            crudSelectController.setCRUDSelectWindow(getCRUDSelectWindow());
            getCRUDSelectWindow().setController(crudSelectController);
        }
        return crudSelectController;
    }

    public static <E extends IEntity> CRUDController<E> getCRUDController(CRUDTable table) {
        CRUDController<E> crudController = new CRUDController<>(getDAOService(table));
        crudController.setLeagueWindow(getLeagueWindow());
        crudController.setMatchWindow(getMatchWindow());
        crudController.setPlayerWindow(getPlayerWindow());
        crudController.setTeamWindow(getTeamWindow());
        crudController.setPlayerCRUDWindow(getPlayerCRUDWindow());
        return crudController;
    }

    public static ScheduleController getScheduleController() {
        if (scheduleController == null) {
            scheduleController = new ScheduleController(getMatchDAOService(), getTeamDAOService());

            scheduleController.setMatchWindow(getMatchAdminWindow());
            scheduleController.setEventWindow(getEventWindow());

            getMatchAdminWindow().setController(scheduleController);
            getScheduleWindow().setController(scheduleController);
        }
        return scheduleController;
    }

    public static MatchFillController getMatchFillController() {
        if (matchFillController == null) {
            matchFillController = new MatchFillController(getMatchDAOService(), getTeamDAOService());

            matchFillController.setMatchFillWindow(getMatchFillWindow());
            
            getMatchFillWindow().setController(matchFillController);
        }
        return matchFillController;
    }

    public static void setAllCRUDController() {
        getTeamCRUDWindow().setController(getCRUDController(CRUDTable.TEAM));
        getPlayerCRUDWindow().setController(getCRUDController(CRUDTable.PLAYER));
        getMatchCRUDWindow().setController(getCRUDController(CRUDTable.MATCH));
    }

    public static void assembleAll() {
        logger.info("Armando controladores y ventanas...");
        getConsultController();
        getLoginController();
        getScheduleController();
        getCRUDSelectController();
        getMatchFillController();
        setAllCRUDController();
    }
}
