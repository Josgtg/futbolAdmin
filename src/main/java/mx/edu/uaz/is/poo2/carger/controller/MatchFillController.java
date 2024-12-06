package mx.edu.uaz.is.poo2.carger.controller;

import java.util.Optional;

import mx.edu.uaz.is.poo2.carger.services.dao.MatchDAOService;
import mx.edu.uaz.is.poo2.carger.services.dao.TeamDAOService;
import mx.edu.uaz.is.poo2.carger.model.entities.Match;
import mx.edu.uaz.is.poo2.carger.view.windows.MatchFillWindow;

public class MatchFillController extends Controller{
    private MatchFillWindow matchFillWindow;
    private final MatchDAOService daoM;

    public MatchFillController(MatchDAOService matchDAOService, TeamDAOService teamDAOService) {
        super();
        this.daoM = matchDAOService;
    }
    public void setMatchFillWindow(MatchFillWindow window){
        this.matchFillWindow = window;
    }
    
    public void startMatchFillWindow(){
        this.matchFillWindow.start();
    }

    public void startMatchFillWindow(Match match){
        this.matchFillWindow.start(match);
    }
    public void setMatchFillWindowMatches() {
        var matches = this.daoM.findAll();
        this.matchFillWindow.setMatches(matches);
    }
    public void setMatchFillWindowMatchesNotPlayed() {
        var matches = this.daoM.findNotPlayed();
        this.matchFillWindow.setMatches(matches);
    }
    public void setMatchFillWindowMatchesPlayed() {
        var matches = this.daoM.findPlayed();
        this.matchFillWindow.setMatches(matches);
    }
    public Optional<Match> updateMatch (Match match){
        return this.daoM.update(match);
        
    }
    
}
