package mx.edu.uaz.is.poo2.carger.services.formatters;

import java.util.List;

import mx.edu.uaz.is.poo2.carger.model.entities.Player;

public class PlayerFormatter extends Formatter<Player> {
    
    private int MAX_ID_LEN;
    private int MAX_NAME_LEN;
    private int MAX_TEAM_NAME_LEN;
    private int MAX_GOALS_DIGITS;
    private int MAX_ASSISTS_DIGITS;
    private int MAX_AGE_DIGITS;
    private int MAX_YELLOW_CARDS_DIGITS;
    private int MAX_RED_CARDS_DIGITS;

    public PlayerFormatter() {
        this.resetMaxs();
    }

    private void resetMaxs() {
        this.MAX_ID_LEN = 0;
        this.MAX_NAME_LEN = 0;
        this.MAX_TEAM_NAME_LEN = 0;
        this.MAX_GOALS_DIGITS = 0;
        this.MAX_AGE_DIGITS = 0;
        this.MAX_ASSISTS_DIGITS = 0;
        this.MAX_YELLOW_CARDS_DIGITS = 0;
        this.MAX_RED_CARDS_DIGITS = 0;
    }

    @Override
    public String format(Player player, boolean showId) {
        return this.format(player, showId, true);
    }

    @Override
    public String format(List<Player> players) {
        return this.format(players, true, false);
    }

    public String format(List<Player> players, boolean showId, boolean showTeam) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < players.size(); i++) {
            sb.append(String.format("%d ", i + 1));
            if (i + 1 < 10) sb.append(' ');
            sb.append(this.format(players.get(i), showId, showTeam));
            sb.append('\n');
        }
        return sb.toString();
    }

    public String format(Player player, boolean showId, boolean showTeam) {
        return String.format(
            "%s|  %s  |%s  %s  |  %s  |  %s  |  %s  |  %s  |", 
            showId ? String.format("|  %s  ", this.getFormatted("ID: ", MAX_ID_LEN, player.getId())) : "",
            this.getFormatted("", this.MAX_NAME_LEN, player.getName()),
            showTeam ? String.format("  %s  |", this.getFormatted(
                    "Equipo: ", this.MAX_TEAM_NAME_LEN,
                    player.getTeam() != null ? player.getTeam().getName() : "N/A"
            )) : "",
            this.getFormatted("Edad: ", this.MAX_AGE_DIGITS, player.getAge()),
            this.getFormatted("G: ", this.MAX_GOALS_DIGITS, player.getGoals()),
            this.getFormatted("A: ", this.MAX_ASSISTS_DIGITS, player.getAssists()),
            this.getFormatted("TA: ", this.MAX_YELLOW_CARDS_DIGITS, player.getYellowCards()),
            this.getFormatted("TR: ", this.MAX_RED_CARDS_DIGITS, player.getRedCards())
        );
    }

    public void reviewPlayers(List<Player> players) {
        this.resetMaxs();
        int i, j;
        for (Player p : players) {
            if (p.getId().toString().length() > this.MAX_ID_LEN)
                this.MAX_ID_LEN = p.getId().toString().length();

            if (p.getName().length() > this.MAX_NAME_LEN)
                this.MAX_NAME_LEN = p.getName().length();

            if (p.getTeam() != null && p.getTeam().getName().length() > this.MAX_TEAM_NAME_LEN)
                this.MAX_TEAM_NAME_LEN = p.getTeam().getName().length();
            
            i = p.getGoals(); j = 1;
            while (i >= 10) { j++; i /= 10; }
            if (j > this.MAX_GOALS_DIGITS) this.MAX_GOALS_DIGITS = j;

            i = p.getAssists(); j = 1;
            while (i >= 10) { j++; i /= 10; }
            if (j > this.MAX_ASSISTS_DIGITS) this.MAX_ASSISTS_DIGITS = j;
            
            i = p.getAge(); j = 1;
            while (i >= 10) { j++; i /= 10; }
            if (j > this.MAX_AGE_DIGITS) this.MAX_AGE_DIGITS = j;

            i = p.getYellowCards(); j = 1;
            while (i >= 10) { j++; i /= 10; }
            if (j > this.MAX_YELLOW_CARDS_DIGITS) this.MAX_YELLOW_CARDS_DIGITS = j;

            i = p.getRedCards(); j = 1;
            while (i >= 10) { j++; i /= 10; }
            if (j > this.MAX_RED_CARDS_DIGITS) this.MAX_RED_CARDS_DIGITS = j;
        }
    }
}
