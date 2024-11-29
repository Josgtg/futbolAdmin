package mx.edu.uaz.is.poo2.carger.view.windows.consult;

import java.util.ArrayList;
import java.util.List;

import mx.edu.uaz.is.poo2.carger.model.constants.CRUDTable;
import mx.edu.uaz.is.poo2.carger.model.constants.Messages;
import mx.edu.uaz.is.poo2.carger.model.constants.WindowMessages;
import mx.edu.uaz.is.poo2.carger.model.entities.Match;

public class MatchWindow extends ConsultWindow<Match> {
    private List<Match> matches;

    public MatchWindow(List<Match> matches) {
        super("Match");
        this.matches = matches;
    }

    public MatchWindow() {
        super("Match");
        this.matches = new ArrayList<>();
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
    
    @Override
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

    protected boolean askOptions() {
        int option = this.readInt(
            WindowMessages.consultOrReturn(CRUDTable.MATCH.getName()),
            0, this.matches.size()
        );
        if (option == 0)
            return false;

        this.controller.startMatchWindow(this.matches.get(--option));
        return true;
    }

    protected boolean askOptions(Match match) {
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
        return this.matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }
}