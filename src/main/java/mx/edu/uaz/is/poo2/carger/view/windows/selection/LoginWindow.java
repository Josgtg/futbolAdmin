package mx.edu.uaz.is.poo2.carger.view.windows.selection;

import mx.edu.uaz.is.poo2.carger.controller.view.LoginController;
import mx.edu.uaz.is.poo2.carger.view.windows.*;
import mx.edu.uaz.is.poo2.carger.model.constants.WindowMessages;

public class LoginWindow extends Window<LoginController> {
    public LoginWindow() {
        super("Login");
    }

    @Override
    public void start() {
        this.println(WindowMessages.windowTitle(this.TITLE));
        this.controller.startAdminWindow();
    }
}