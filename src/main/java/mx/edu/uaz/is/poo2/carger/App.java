package mx.edu.uaz.is.poo2.carger;

import mx.edu.uaz.is.poo2.carger.controller.Controller;
import mx.edu.uaz.is.poo2.carger.controller.ControllerCreator;
import mx.edu.uaz.is.poo2.carger.services.CSVImport;
import mx.edu.uaz.is.poo2.carger.view.windows.Window;

public class App {
    public static void startMainWindow() {
        ControllerCreator.getConsultController().startLeagueWindow();
    }

    public static void main(String[] args) {
        ControllerCreator.assembleAll();
        new InitWindow().start();
        startMainWindow();
    }
}

class InitWindow extends Window<Controller> {
    public InitWindow() {
        super("Futbol Admin");
    }

    @Override
    public void start() {
        this.println("Las tablas de ejemplo sólo sirve paran testear la funcionalidad de las pantallas y funcionalidades en la consulta.");
        if (this.askYesOrNo("¿Usar una tabla de ejemplo? (s/n): "))
            new CSVImport().fillAllTables();
    }
}

