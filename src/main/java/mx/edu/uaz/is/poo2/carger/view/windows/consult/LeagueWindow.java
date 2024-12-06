package mx.edu.uaz.is.poo2.carger.view.windows.consult;

import java.util.ArrayList;
import java.util.List;

import mx.edu.uaz.is.poo2.carger.services.formatters.LeagueFormatter;
import mx.edu.uaz.is.poo2.carger.controller.view.ConsultController;
import mx.edu.uaz.is.poo2.carger.model.constants.ErrorKind;
import mx.edu.uaz.is.poo2.carger.model.constants.Errors;
import mx.edu.uaz.is.poo2.carger.model.constants.columns.LeagueColumns;
import mx.edu.uaz.is.poo2.carger.view.windows.*;
import mx.edu.uaz.is.poo2.carger.model.constants.Messages;
import mx.edu.uaz.is.poo2.carger.model.constants.WindowMessages;
import mx.edu.uaz.is.poo2.carger.model.entities.Team;

public class LeagueWindow extends Window<ConsultController> {
    protected List<Team> teams;
    private final LeagueFormatter leagueFormatter;

    public LeagueWindow() {
        super("League");
        this.leagueFormatter = new LeagueFormatter();
        this.teams = new ArrayList<>();
    }

    public LeagueWindow(List<Team> teams) {
        super("League");
        this.leagueFormatter = new LeagueFormatter();
        this.teams = teams;
    }

    @Override
    public void start() {
        boolean conntinue = true;
        while (conntinue) {
            this.controller.updateLeague();
            this.controller.setLeagueWindowTeams();
            this.leagueFormatter.reviewTeams(this.teams);
            this.println(WindowMessages.windowTitle(this.TITLE));
            this.showLeague();
            this.println("");
            conntinue = this.askOptions();
        }
    }

    public void showLeague() {
        if (this.teams.isEmpty()) {
            this.println(Messages.NO_ELEMENTS);
            return;
        }
        this.println(WindowMessages.LEAGUE_COLUMN_SORT_OPTIONS);
        this.print(this.leagueFormatter.format(this.teams));
    }

    private boolean askOptions() {
        String optionStr = this.readString(WindowMessages.LEAGUE_OPTIONS, 1, 100);
        try {
            int option = Integer.parseInt(optionStr);
            switch (option) {
                case -2 -> this.controller.startPlayerWindow(20, false);
                case -1 -> this.controller.startLoginWindow();
                case 0 -> this.controller.exit(0);
                default -> {
                    if (option <= this.teams.size())
                        this.controller.startTeamWindow(this.teams.get(--option));
                    else
                        this.printerr(ErrorKind.INPUT_ERROR, Errors.INPUT_ERROR);
                }
            }
        } catch (NumberFormatException e) {
            optionStr += "     ";
            switch (optionStr.charAt(0)) {
                case 'p' -> this.controller.setTeamsBy(LeagueColumns.POINTS, optionStr.charAt(1) == '-');
                case 'n' -> this.controller.setTeamsBy(LeagueColumns.NAME, optionStr.charAt(1) == '-');
                case 'v' -> this.controller.setTeamsBy(LeagueColumns.WINS, optionStr.charAt(1) == '-');
                case 'e' -> this.controller.setTeamsBy(LeagueColumns.DRAWS, optionStr.charAt(1) == '-');
                case 'd' -> {
                    if (optionStr.charAt(1) == 'g') this.controller.setTeamsBy(LeagueColumns.GD, optionStr.charAt(2) == '-');
                    else this.controller.setTeamsBy(LeagueColumns.LOSSES, optionStr.charAt(1) == '-');
                }
                case 'g' -> {
                    switch (optionStr.charAt(1)) {
                        case 'f' -> this.controller.setTeamsBy(LeagueColumns.GF, optionStr.charAt(2) == '-');
                        case 'c' -> this.controller.setTeamsBy(LeagueColumns.GA, optionStr.charAt(2) == '-');
                        default -> this.printerr(ErrorKind.INPUT_ERROR, Messages.INVALID_INPUT);
                    }
                }
                default -> this.printerr(ErrorKind.INPUT_ERROR, Messages.INVALID_INPUT);
            }
        }
        return true;
    }

    public List<Team> getTeams() {
        return this.teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}