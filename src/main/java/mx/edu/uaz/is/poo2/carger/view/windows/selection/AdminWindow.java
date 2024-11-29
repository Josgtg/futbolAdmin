package mx.edu.uaz.is.poo2.carger.view.windows.selection;

import java.util.Arrays;

import mx.edu.uaz.is.poo2.carger.controller.view.LoginController;
import mx.edu.uaz.is.poo2.carger.view.windows.*;
import mx.edu.uaz.is.poo2.carger.model.constants.WindowMessages;

public class AdminWindow extends Window<LoginController> {

    public AdminWindow() {
        super("Admin");
    }

    @Override
    public void start() {
        boolean conntinue = true;
        while(conntinue) {
            this.println(WindowMessages.windowTitle(this.TITLE));
            conntinue = this.askOptions();
        }
    }

    private boolean askOptions() {
        String[] options = {"Gestionar catalogos", "Gestionar calendarización", "Gestional ronda final", "Vaciar Partido Jugado"};
        this.println(WindowMessages.ASK_FOR_ACTION);
        this.println(this.listAsNumeratedStr(Arrays.asList(options)));
        int option = this.readInt(WindowMessages.ASK_FOR_OPTION_OR_RETURN, 0, options.length);
        boolean conntinue = true;
        switch (option) {
            case 0 -> conntinue = false;
            case 1 -> this.controller.startCRUDSelectWindow();
            case 2 -> this.controller.startScheduleWindow();
            case 3 -> {
                this.println("aun no implementado");
                conntinue = false;
            }
            case 4 -> this.controller.startMatchFillWindow();
            default -> {
                this.println("aun no implementado");
                conntinue = false;
            }
        }
        return conntinue;
    }
}