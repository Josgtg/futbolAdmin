package mx.edu.uaz.is.poo2.carger.view.windows.consult;

import java.util.List;

import mx.edu.uaz.is.poo2.carger.model.constants.CRUDTable;
import mx.edu.uaz.is.poo2.carger.model.constants.Messages;
import mx.edu.uaz.is.poo2.carger.model.constants.WindowMessages;
import mx.edu.uaz.is.poo2.carger.model.entities.Team;
import mx.edu.uaz.is.poo2.carger.services.formatters.LeagueFormatter;
import mx.edu.uaz.is.poo2.carger.services.formatters.PlayerFormatter;

public class TeamWindow extends ConsultWindow<Team> {
    private List<Team> teams;
    private final LeagueFormatter leagueFormatter;
    private final PlayerFormatter playerFormatter;

    public TeamWindow() {
        super("Team");
        this.leagueFormatter = new LeagueFormatter();
        this.playerFormatter = new PlayerFormatter();
    }

    @Override
    public void start() {
        this.controller.setTeamWindowTeams();
        this.start(this.teams.size(), false);
    }

    public void start(int amount, boolean showId) {
        // Muestra todos los equipos
        this.controller.setTeamWindowTeams();
        boolean conntinue = true;
        while (conntinue) {
            if (amount > this.teams.size())
                amount = this.teams.size();
            this.println(WindowMessages.windowTitle(this.TITLE));
            this.showTeams(amount, showId);
            this.println("");
            conntinue = this.askOptions();
        }
    }

    private void showTeams(int amount, boolean showId) {
        if (this.teams.subList(0, amount).isEmpty()) {
            this.println(Messages.NO_ELEMENTS);
            return;
        }
        this.leagueFormatter.reviewTeams(this.teams.subList(0, amount));
        this.println(this.leagueFormatter.format(this.teams.subList(0, amount), showId));
    }

    @Override
    public void start(Team team) {
        // Muestra a un equipo específico
        boolean conntinue = true;
        while(conntinue) {
            this.println(WindowMessages.windowTitle(this.TITLE));
            this.println(this.leagueFormatter.format(team));
            this.showPlayers(team, true);
            conntinue = this.askOptions(team);
        }
    }

    public void showPlayers(Team team, boolean showId) {
        if (team.getPlayers().isEmpty()) {
            this.println(Messages.NO_ELEMENTS);
            return;
        }
        this.playerFormatter.reviewPlayers(team.getPlayers());
        this.println("");
        this.println(this.playerFormatter.format(team.getPlayers(), showId, false));
    }

    private boolean askOptions() {
        int option = this.readInt(
            WindowMessages.consultOrReturn(CRUDTable.TEAM.getName()),
            0, this.teams.size()
        );
        if (option == 0)
            return false;
        this.controller.startTeamWindow(this.teams.get(--option));
        return true;
    }

    private boolean askOptions(Team team) {
        this.readInt(WindowMessages.RETURN_WINDOW, 0, team.getPlayers().size());
        return false;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
