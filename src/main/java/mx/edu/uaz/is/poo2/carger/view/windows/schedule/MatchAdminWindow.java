package mx.edu.uaz.is.poo2.carger.view.windows.schedule;

import java.util.List;

import mx.edu.uaz.is.poo2.carger.controller.view.ScheduleController;
import mx.edu.uaz.is.poo2.carger.model.constants.CRUDTable;
import mx.edu.uaz.is.poo2.carger.model.constants.Errors;
import mx.edu.uaz.is.poo2.carger.model.constants.Messages;
import mx.edu.uaz.is.poo2.carger.model.constants.WindowMessages;
import mx.edu.uaz.is.poo2.carger.model.entities.Match;
import mx.edu.uaz.is.poo2.carger.view.windows.Window;

public class MatchAdminWindow extends Window<ScheduleController> {
    private List<Match> matches;
        
    public MatchAdminWindow(){
        super("MatchAdmin");
    }
    

    @Override
    public void start() {
        this.controller.setMatchWindowMatches();
        boolean conntinue = true;
        while (conntinue) {
            this.println(WindowMessages.windowTitle(this.TITLE));
            if (this.matches.isEmpty()) {
                this.println(Messages.NO_ELEMENTS);
                return;
            }
            this.println(this.listAsNumeratedStr(this.matches));
            conntinue = this.askOptions();
        }
    }

    public void start(Match match) {
        boolean conntinue = true;
        while (conntinue) {
            this.println(WindowMessages.windowTitle(this.TITLE));
            this.println(match);
            this.println("");
            if (match.getEvents().isEmpty()) {
                this.println(Messages.NO_EVENTS);
                return;
            }
            this.println(this.basicListAsNumeratedStr(match.getEvents()));
            conntinue = this.askOptions(match);
        }
    }

    private boolean askOptions() {
        int option = this.readInt(
            WindowMessages.consultOrReturnOrModify(CRUDTable.MATCH.getName()),
            -1, this.matches.size()
        );
        if (option == 0)
            return false;
        if (option == -1){
            this.println(Errors.NOT_IMPLEMENTED);
            return false;
        }

        this.controller.startMatchWindow(this.matches.get(--option));
        return true;
    }

    private boolean askOptions(Match match) {
        int option = this.readInt(
            WindowMessages.consultOrReturn(CRUDTable.EVENT.getName()),
            0, match.getEvents().size()
        );
        if (option == 0)
            return false;

        this.controller.startEventWindow(match.getEvents());
        return true;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    
}
