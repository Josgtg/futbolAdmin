package mx.edu.uaz.is.poo2.carger.view.windows.crud;

import java.util.Optional;

import mx.edu.uaz.is.poo2.carger.model.constants.CRUDTable;
import mx.edu.uaz.is.poo2.carger.model.constants.Messages;
import mx.edu.uaz.is.poo2.carger.model.entities.Player;
import mx.edu.uaz.is.poo2.carger.model.entities.Team;

public class TeamCRUDWindow extends CRUDWindow<Team> {
    public TeamCRUDWindow() {
        super("TeamCRUD", CRUDTable.TEAM);
    }

    @Override
    public void showWindow() {
        this.controller.startTeamWindow();
    }

    @Override
    public Team readEntity() {
        Team team = new Team();
        String name = this.readString("Nombre del equipo: ", Team.MIN_NAME_LEN, Team.MAX_NAME_LEN);
        this.askToAddPlayers(team);
        team.setName(name);
        return team;
    }

    private void askToAddPlayers(Team team) {
        if (!this.askYesOrNo("¿Quieres añadir jugadores al equipo? (s/n): "))
            return;
        
        this.println("Escribe -1 en cualquier momento para salir.\n");
        for (int i = 0; i < Team.MAX_PLAYERS; i++) {
            if (this.addPlayer(team))
                return;
        }
    }

    private boolean addPlayer(Team team) {
        Long id = this.readId("ID del jugador, 0 para crear uno nuevo: ", -1L, Long.MAX_VALUE);
        if (id == -1L)
            return true;

        Player player;
        if (id == 0) {
            player = this.controller.readPlayerFromUser();
        } else {
            Optional<Player> playerOpt = this.controller.getEntity(id, CRUDTable.PLAYER);
            if (playerOpt.isPresent()) {
                player = playerOpt.get();
            } else {
                this.println(Messages.ID_NOT_FOUND);
                return false;
            }
        }
            
        if (player == null)
            this.println(Messages.ID_NOT_FOUND);
        else
            team.addPlayer(player);
        
        this.println("");
        return false;
    }

    @Override
    public Team readEntityToUpdate(Team team) {
        String name = this.readToUpdateString("nombre", team.getName(), Team.MIN_NAME_LEN, Team.MAX_NAME_LEN);
        this.askToUpadtePlayers(team);
        team.setName(name);
        return team;
    }

    private void askToUpadtePlayers(Team team) {
        if (!this.askYesOrNo("¿Quieres actualizar los jugadores al equipo? (s/n): "))
            return;
    
        team.getPlayers().size();

        this.println("Escribe 0 en cualquier momento para salir.\n");
        boolean conntinue = true;
        
        OUTER:
        while (conntinue) {
            int action = this.readInt("Escribe 1 para añadir un jugador, 2 para eliminar: ", 0, 2);
            switch (action) {
                case 0 -> { break OUTER; }
                case 1 -> this.addPlayer(team);
                default -> {
                    Long id = this.readId("ID del jugador a eliminar: ");
                    Optional<Player> player = this.controller.getEntity(id, CRUDTable.PLAYER);
                    if (player.isPresent())
                        team.removePlayer(player.get());
                    else
                        this.println(Messages.ID_NOT_FOUND);
                }
            }
        }
    }
}