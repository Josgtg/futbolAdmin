package mx.edu.uaz.is.poo2.carger.view.windows.schedule;

import java.util.Arrays;

import mx.edu.uaz.is.poo2.carger.controller.view.ScheduleController;
import mx.edu.uaz.is.poo2.carger.model.constants.WindowMessages;
import mx.edu.uaz.is.poo2.carger.view.windows.Window;

public class ScheduleWindow extends Window<ScheduleController>{

    public ScheduleWindow (){
        super("Calendarizacion");
    }

    @Override
    public void start() {
        boolean conntinue = true;
        while(conntinue) {
            this.println(WindowMessages.windowTitle(this.TITLE));
            conntinue = this.askOptions();
        }
        
    }
    private boolean askOptions(){
        String[] options = {"Generar Calendarizacion", "Ver Calendarizacion"};
        this.println(WindowMessages.ASK_FOR_ACTION);
        this.println(this.listAsNumeratedStr(Arrays.asList(options)));
        int option = this.readInt(WindowMessages.ASK_FOR_OPTION_OR_RETURN, 0, options.length);
        boolean conntinue = true;
        switch (option) {
            case 0 -> conntinue = false;
            case 1 -> { 
                this.controller.generateMatches();
                this.controller.startMatchWindow();
            }
            case 2 -> this.controller.startMatchWindow();
            default -> conntinue = false;
        }
        return conntinue;
    }
}
