package mx.edu.uaz.is.poo2.carger.view.windows.consult;

import java.util.ArrayList;
import java.util.List;

import mx.edu.uaz.is.poo2.carger.controller.view.ConsultController;
import static mx.edu.uaz.is.poo2.carger.model.constants.AnsiEscapeCodes.*;
import mx.edu.uaz.is.poo2.carger.view.windows.*;
import mx.edu.uaz.is.poo2.carger.model.constants.Messages;
import mx.edu.uaz.is.poo2.carger.model.constants.WindowMessages;
import mx.edu.uaz.is.poo2.carger.model.entities.Team;

public class LeagueWindow extends Window<ConsultController> {
    protected List<Team> teams;

    public LeagueWindow() {
        super("League");
        this.teams = new ArrayList<>();
    }

    public LeagueWindow(List<Team> teams) {
        super("League");
        this.teams = teams;
    }

    @Override
    public void start() {
        this.controller.setLeagueWindowTeams();
        boolean conntinue = true;
        while (conntinue) { 
            this.println(WindowMessages.windowTitle(this.TITLE));
            conntinue = this.askOptions();   
        }
    }

    public void showLeague() {
        if (this.teams.isEmpty()) {
            this.println(Messages.NO_ELEMENTS);
            return;
        }
        for (int i = 0; i < this.teams.size(); i++) {
            this.println(String.format("%d: %s", i + 1, this.formatTeam(teams.get(i))));
        }
    }

    private boolean askOptions() {
        this.showLeague();
        int option = this.readInt(WindowMessages.LEAGUE_OPTIONS, -1, this.teams.size());
        switch (option) {
            case -1 -> this.controller.startLoginWindow();
            case 0 -> this.controller.exit(0);
            default -> this.controller.startTeamWindow(this.teams.get(--option));
        }
        return true;
    }

    public List<Team> getTeams() {
        return this.teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public String formatTeam(Team t) {
        int diffInt = t.getGoalsScored() - t.getGoalsReceived();
        String diffStr = String.valueOf(diffInt);
        if (diffInt > 0)
            diffStr = "+" + diffStr;
        return String.format(
            "%s | %sW:%s %d | %sD:%s %d | %sL:%s %d | GF: %d | GA: %d | GD: %s | Pts: %d", 
            t.getName(), ANSI_GREEN,  ANSI_RESET, t.getWins(), ANSI_YELLOW, ANSI_RESET, t.getDraws(),
            ANSI_RED, ANSI_RESET, t.getLosses(), t.getGoalsScored(),
            t.getGoalsReceived(), diffStr, t.getPoints()
        );
    }
}