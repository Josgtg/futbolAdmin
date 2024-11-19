package mx.edu.uaz.is.poo2.carger.view.windows;

import java.util.List;

import mx.edu.uaz.is.poo2.carger.controller.Controller;
import mx.edu.uaz.is.poo2.carger.model.constants.Messages;
import mx.edu.uaz.is.poo2.carger.model.entities.Team;

public class LeagueWindow extends Window {
    private final List<Team> teams;

    public LeagueWindow(Controller controller, List<Team> teams) {
        super(controller);
        this.teams = teams;
    }

    @Override
    public void mainView() {
        this.showLeague();
        this.askOptions();
    }

    public void showLeague() {
        if (this.teams.isEmpty()) {
            this.println(Messages.NO_ELEMENTS);
            return;
        }
        this.println(this.listAsNumeratedStr(this.teams));
    }

    public void askOptions() {
        int option = this.readInt(
            "Escribe el número del equipo a consultar o escribe 0 para ingresar como admin, -1 para salir: ",
            -1, this.teams.size()
        );
        if (option == -1) {
            this.controller.windowAction(new WindowSignal(WindowSignal.EXIT));
            return;
        } else if (option == 0) {
            this.controller.windowAction(new WindowSignal(WindowSignal.ENTER_AS_ADMIN));
            return;
        }

        this.controller.windowAction(new WindowSignal(WindowSignal.OPEN_TEAM, this.teams.get(--option)));
    }
}