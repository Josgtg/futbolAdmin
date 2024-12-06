package mx.edu.uaz.is.poo2.carger.services.formatters;

import java.util.List;

import static mx.edu.uaz.is.poo2.carger.model.constants.AnsiEscapeCodes.*;
import mx.edu.uaz.is.poo2.carger.model.entities.Team;

public class LeagueFormatter extends Formatter<Team> {

    private int MAX_ID_LEN;
    private int MAX_TEAM_NAME_LEN;
    private int MAX_WIN_DIGITS;
    private int MAX_DRAW_DIGITS;
    private int MAX_LOSS_DIGITS;
    private int MAX_GF_DIGITS;
    private int MAX_GA_DIGITS;
    private int MAX_GD_DIGITS;
    private int MAX_POINTS_DIGITS;

    public LeagueFormatter() {
        this.resetMaxs();
    }

    private void resetMaxs() {
        this.MAX_ID_LEN = 0;
        this.MAX_TEAM_NAME_LEN = 0;
        this.MAX_WIN_DIGITS = 0;
        this.MAX_DRAW_DIGITS = 0;
        this.MAX_LOSS_DIGITS = 0;
        this.MAX_GF_DIGITS = 0;
        this.MAX_GA_DIGITS = 0;
        this.MAX_GD_DIGITS = 0;
        this.MAX_POINTS_DIGITS = 0;
    }

    @Override
    public String format(Team t, boolean showId) {
        int diffInt = t.getGoalsScored() - t.getGoalsReceived();
        String diffStr = String.valueOf(diffInt);
        if (diffInt > 0)
            diffStr = "+" + diffStr;
        return String.format(
            "%s|  %s  |  %s  |  %s  |  %s  |  %s  |  %s  |  %s  |  %s  |",
            showId ? String.format("|  %s  ", this.getFormatted("ID: ", MAX_ID_LEN, t.getId())) : "",
            this.getFormatted("", this.MAX_TEAM_NAME_LEN, t.getName()), 
            this.getFormatted(String.format("%sV: %s", ANSI_GREEN, ANSI_RESET), this.MAX_WIN_DIGITS, t.getWins()),
            this.getFormatted(String.format("%sE: %s", ANSI_YELLOW, ANSI_RESET), this.MAX_DRAW_DIGITS, t.getDraws()),
            this.getFormatted(String.format("%sD: %s", ANSI_RED, ANSI_RESET), this.MAX_LOSS_DIGITS, t.getLosses()),
            this.getFormatted("GF: ", this.MAX_GF_DIGITS, t.getGoalsScored()),
            this.getFormatted("GC: ", this.MAX_GA_DIGITS, t.getGoalsReceived()),
            this.getFormatted("DG: ", this.MAX_GD_DIGITS, diffStr),
            this.getFormatted("Pts: ", this.MAX_POINTS_DIGITS, t.getPoints())
        );
    }

    public void reviewTeams(List<Team> teams) {
        this.resetMaxs();
        int i, j;
        for (Team t : teams) {
            if (t.getId().toString().length() > this.MAX_ID_LEN)
                this.MAX_ID_LEN = t.getId().toString().length();

            if (t.getName().length() > this.MAX_TEAM_NAME_LEN)
                this.MAX_TEAM_NAME_LEN = t.getName().length();
            
            i = t.getWins(); j = 1;
            while (i >= 10) { j++; i /= 10; }
            if (j > this.MAX_WIN_DIGITS) this.MAX_WIN_DIGITS = j;

            i = t.getLosses(); j = 1;
            while (i >= 10) { j++; i /= 10; }
            if (j > this.MAX_LOSS_DIGITS) this.MAX_LOSS_DIGITS = j;
            
            i = t.getDraws(); j = 1;
            while (i >= 10) { j++; i /= 10; }
            if (j > this.MAX_DRAW_DIGITS) this.MAX_DRAW_DIGITS = j;

            i = t.getGoalsScored(); j = 1;
            while (i >= 10) { j++; i /= 10; }
            if (j > this.MAX_GF_DIGITS) this.MAX_GF_DIGITS = j;

            i = t.getGoalsReceived(); j = 1;
            while (i >= 10) { j++; i /= 10; }
            if (j > this.MAX_GA_DIGITS) this.MAX_GA_DIGITS = j;

            i = t.getGoalsScored() - t.getGoalsReceived(); j = 1;
            while (i >= 10) { j++; i /= 10; }
            if (j + 1 > this.MAX_GD_DIGITS) this.MAX_GD_DIGITS = j + 1;

            i = t.getPoints(); j = 1;
            while (i >= 10) { j++; i /= 10; }
            if (j > this.MAX_POINTS_DIGITS) this.MAX_POINTS_DIGITS = j;
        }
    }
}
