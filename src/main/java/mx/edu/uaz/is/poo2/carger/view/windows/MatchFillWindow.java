package mx.edu.uaz.is.poo2.carger.view.windows;

import java.sql.Timestamp;
import java.util.List;

import mx.edu.uaz.is.poo2.carger.controller.MatchFillController;
import mx.edu.uaz.is.poo2.carger.model.constants.CRUDTable;
import mx.edu.uaz.is.poo2.carger.model.constants.ErrorKind;
import mx.edu.uaz.is.poo2.carger.model.constants.Messages;
import mx.edu.uaz.is.poo2.carger.model.constants.WindowMessages;
import mx.edu.uaz.is.poo2.carger.model.entities.Match;


public class MatchFillWindow extends Window<MatchFillController> {
    private List<Match> matches;

    public MatchFillWindow(){
        super("Rellenar Partido");
    }


    @Override
    public void start() {
        this.controller.setMatchFillWindowMatches();
        boolean conntinue = true;
        while (conntinue) {
            this.println(WindowMessages.windowTitle(this.TITLE));
            if (this.matches.isEmpty()) {
                this.println(Messages.NO_ELEMENTS);
                return;
            }
            this.println(this.basicListAsNumeratedStr(this.matches));
            conntinue = this.askOptions();
        }
    }

    public void start(Match match) {
        this.println(WindowMessages.windowTitle(this.TITLE));
        this.println(match);
        this.println("");
        this.updateEntity(match);  
    }

    

    private boolean askOptions() {
        int option = this.readInt(
            WindowMessages.selectToFillOrReturn(CRUDTable.MATCH.getName()),
            0, this.matches.size()
        );
        if (option == 0)
            return false;

        this.controller.startMatchFillWindow(this.matches.get(--option));
        return true;
    }


    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    //Codigo de CRUDWindow y MatchCRUDWindow
    private void updateEntity(Match match) {
        match = this.readEntityToUpdate(match);
        if (this.controller.updateMatch(match).isPresent())
            this.println(Messages.ENTITY_UPDATED);
        else
            this.printerr(ErrorKind.QUERY_NOT_EXECUTED, Messages.CANT_UPDATE_ENTITY);
    }
    
    private boolean askToUpdate(String attributeName, Object object) {
        return this.askYesOrNo(WindowMessages.askToUpdate(attributeName, object));
    }

    public Timestamp readToUpdateDate(String attributeName, Timestamp date) {
        if (!this.askToUpdate(attributeName, date))
            return date;
        return this.readDate();
    }

    public int readToUpdateInt(String attributeName, int value, int lo, int hi) {
        if (!this.askToUpdate(attributeName, value))
            return value;
        return this.readInt("Nuevo valor: ", lo, hi);
    }

    public String readToUpdateString(String attributeName, String str, int lo, int hi) {
        if (!this.askToUpdate(attributeName, str))
            return str;
        return this.readString("Nuevo valor: ", lo, hi);
    }

    public Match readEntityToUpdate(Match match) {

        Timestamp date = this.readToUpdateDate("Fecha", match.getDate());
        match.setDate(date);

        int homeTeamGoals = this.readToUpdateInt("Goles equipo local", match.getHomeTeamGoals(), 0, 50);
        match.setHomeTeamGoals(homeTeamGoals);

        int awayTeamGoals = this.readToUpdateInt("Goles equipo visitante", match.getAwayTeamGoals(), 0, 50);
        match.setAwayTeamGoals(awayTeamGoals);
        
        match.setPlayed(true);
        return match;
    }
    
}
