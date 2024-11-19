package mx.edu.uaz.is.poo2.carger.view.windows;

import mx.edu.uaz.is.poo2.carger.controller.Controller;

public class LoginWindow extends Window {
    public LoginWindow(Controller controller) {
        super(controller);
    }

    @Override
    public void mainView() {
        this.println("No hay login xd");
        this.controller.windowAction(new WindowSignal(WindowSignal.RETURN_TO_MAIN));
    }
}