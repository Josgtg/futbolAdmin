package mx.edu.uaz.is.poo2.carger.view.windows;

import mx.edu.uaz.is.poo2.carger.controller.Controller;
import mx.edu.uaz.is.poo2.carger.model.entities.Team;

public class TeamWindow extends Window {
    private Team team;

    public TeamWindow(Controller controller) {
        super(controller);
    }

    @Override
    public void mainView() {
        this.askOptions();
    }

    public void showTeamPlayers(Team team) {
        this.team = team;
        this.println(this.listAsNumeratedStr(team.getPlayers()));
        this.askOptions();
    }

    public void askOptions() {
        int option = this.readInt(
            "Escribe el número del jugador a consultar o escribe 0 para regresar a la vista principal: ",
            0, this.team.getPlayers().size()
        );
        if (option == 0) {
            this.controller.windowAction(new WindowSignal(WindowSignal.RETURN_TO_MAIN));
            return;
        }

        this.controller.windowAction(new WindowSignal(WindowSignal.OPEN_PLAYER, this.team.getPlayers().get(--option)));
    }
}