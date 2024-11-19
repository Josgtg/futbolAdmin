package mx.edu.uaz.is.poo2.carger.view.windows;

import java.util.List;

import mx.edu.uaz.is.poo2.carger.controller.Controller;
import mx.edu.uaz.is.poo2.carger.model.entities.Match;

public class MatchWindow extends Window {
    private final List<Match> matches;

    public MatchWindow(Controller controller, List<Match> matches) {
        super(controller);
        this.matches = matches;
    }

    @Override
    public void mainView() {
        this.showMatches();
        this.askOptions();
    }

    public void showMatches() {
        this.println(this.listAsNumeratedStr(this.matches));
    }

    public void showMatch(Match match) {
        this.println(match);
    }

    public void askOptions() {
        int option = this.readInt(
            "Escribe el número del partido a consultar o escribe 0 para regresar a la pantalla principal: ",
            0, this.matches.size()
        );
        if (option == 0) {
            this.controller.windowAction(new WindowSignal(WindowSignal.RETURN_TO_MAIN));
            return;
        }

        this.controller.windowAction(new WindowSignal(WindowSignal.OPEN_MATCH, this.matches.get(--option)));
    }
}