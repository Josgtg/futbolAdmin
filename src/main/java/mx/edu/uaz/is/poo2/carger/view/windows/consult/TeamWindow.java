package mx.edu.uaz.is.poo2.carger.view.windows.consult;

import java.util.List;

import mx.edu.uaz.is.poo2.carger.model.constants.CRUDTable;
import mx.edu.uaz.is.poo2.carger.model.constants.Messages;
import mx.edu.uaz.is.poo2.carger.model.constants.WindowMessages;
import mx.edu.uaz.is.poo2.carger.model.entities.Team;

public class TeamWindow extends ConsultWindow<Team> {
    private List<Team> teams;

    public TeamWindow() {
        super("Team");
    }

    @Override
    public void start() {
        // Muestra todos los equipos
        this.controller.setTeamWindowTeams();
        boolean conntinue = true;
        while (conntinue) {
            this.println(WindowMessages.windowTitle(this.TITLE));
            if (this.teams.isEmpty()) {
                this.println(Messages.NO_ELEMENTS);
                return;
            }
            this.println(this.listAsNumeratedStr(this.teams));
            conntinue = this.askOptions();
        }
    }

    @Override
    public void start(Team team) {
        // Muestra a un equipo específico
        boolean conntinue = true;
        while(conntinue) {
            this.println(WindowMessages.windowTitle(this.TITLE));
            this.println(team);
            if (team.getPlayers().isEmpty()) {
                this.println(Messages.NO_ELEMENTS);
                return;
            }
            this.println("");
            this.println(this.basicListAsNumeratedStr(team.getPlayers()));
            conntinue = this.askOptions(team);
        }
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
        int option = this.readInt(
            WindowMessages.consultOrReturn(CRUDTable.PLAYER.getName()),
            0, team.getPlayers().size()
        );
        if (option == 0)
            return false;
        this.controller.startPlayerWindow(team.getPlayers().get(--option));
        return true;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
