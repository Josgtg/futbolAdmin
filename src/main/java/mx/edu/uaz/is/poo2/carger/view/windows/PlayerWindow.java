package mx.edu.uaz.is.poo2.carger.view.windows;

import mx.edu.uaz.is.poo2.carger.controller.Controller;
import mx.edu.uaz.is.poo2.carger.model.entities.Player;

public class PlayerWindow extends Window {
    public PlayerWindow(Controller controller) {
        super(controller);
    }

    @Override
    public void mainView() {
        this.askOptions();
    }

    public void showPlayer(Player player) {
        this.println(player);
        this.askOptions();
    }

    public void askOptions() {
        this.readInt(
            "Escribe 0 para regresar a la vista principal: ",
            0, 0
        );
        this.controller.windowAction(new WindowSignal(WindowSignal.RETURN_TO_MAIN));
    }
}