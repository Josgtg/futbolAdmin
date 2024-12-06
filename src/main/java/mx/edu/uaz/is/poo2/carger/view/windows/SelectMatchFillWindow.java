package mx.edu.uaz.is.poo2.carger.view.windows;

import java.util.List;

import mx.edu.uaz.is.poo2.carger.controller.MatchFillController;
import mx.edu.uaz.is.poo2.carger.model.constants.CRUDTable;
import mx.edu.uaz.is.poo2.carger.model.constants.Messages;
import mx.edu.uaz.is.poo2.carger.model.constants.WindowMessages;
import mx.edu.uaz.is.poo2.carger.model.entities.Match;

public class SelectMatchFillWindow extends Window<MatchFillController> {
    private boolean ShowMatchesPlayed;
    private List<Match> matches;
    
    public SelectMatchFillWindow(){
        super("Rellenar Partido");
        ShowMatchesPlayed = false;
    }

    public void start() {
            this.controller.setMatchFillWindowMatches();
            if (this.matches.isEmpty()) {
                this.println(Messages.NO_ELEMENTS);
                return;
            }

            boolean conntinue = true;
            while (conntinue) {
                this.println(WindowMessages.windowTitle(this.TITLE));
                if (ShowMatchesPlayed){
                    this.println("Partidos YA jugados");
                    this.controller.setMatchFillWindowMatchesPlayed();
                    if (this.matches.isEmpty()) {
                        this.println(Messages.NO_MATCHES_PLAYED);
                    }
                }else{
                    this.println("Partidos NO Jugados");
                    this.controller.setMatchFillWindowMatchesNotPlayed();
                    if (this.matches.isEmpty()) {
                        this.println(Messages.NO_MATCHES_NOT_PLAYED);
                    }
                }
                this.println(this.basicListAsNumeratedStr(this.matches));
                conntinue = this.askOptions();
            }
        }

        private boolean askOptions() {
            int option = this.readInt(
                WindowMessages.selectToFillOrReturn(CRUDTable.MATCH.getName()),
                -1, this.matches.size()
            );
            if (option == 0){
                return false;
            }else if (option == -1){
                if (ShowMatchesPlayed){
                    ShowMatchesPlayed = false;
                }else{
                    ShowMatchesPlayed = true;
                }
                return true;
            }

            this.controller.startMatchFillWindow(this.matches.get(--option));
            return true;
        }        
}
