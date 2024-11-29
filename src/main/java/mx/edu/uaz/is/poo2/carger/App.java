package mx.edu.uaz.is.poo2.carger;

import mx.edu.uaz.is.poo2.carger.controller.ControllerCreator;
import mx.edu.uaz.is.poo2.carger.services.CSVImport;

public class App {

    public static void assembleAll() {
        ControllerCreator.getConsultController();
        ControllerCreator.getLoginController();
        ControllerCreator.getScheduleController();
        ControllerCreator.getCRUDSelectController();
        ControllerCreator.getMatchFillController();
        ControllerCreator.setAllCRUDController();
    }

    public static void startMainWindow() {
        ControllerCreator.getConsultController().startLeagueWindow();
    }

    public static void fillAllTables() {
        new CSVImport().fillAllTables();
    }

    public static void main(String[] args) {
        fillAllTables();
        assembleAll();
        startMainWindow();
    }
}
