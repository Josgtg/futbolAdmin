package mx.edu.uaz.is.poo2.carger.view.windows.crud;

import java.util.Optional;

import mx.edu.uaz.is.poo2.carger.model.constants.CRUDTable;
import mx.edu.uaz.is.poo2.carger.model.entities.Player;
import mx.edu.uaz.is.poo2.carger.model.entities.Team;

public class PlayerCRUDWindow extends CRUDWindow<Player> {
    public PlayerCRUDWindow() {
        super("PlayerCRUD", CRUDTable.PLAYER);
    }

    @Override
    public void showWindow() {
        this.controller.startPlayerWindow();
    }

    @Override
    public Player readEntity() {
        String name = this.readString("Nombre: ", Player.MIN_NAME_LEN, Player.MAX_NAME_LEN);

        int age = this.readInt("Edad: ", Player.MIN_AGE, Player.MAX_AGE);

        Optional<Team> team = Optional.empty();
        Long teamId = 0L;
        while (team.isEmpty()) {
            teamId = this.readId("ID del equipo (Escribe 0 para no añadir equipo al jugador): ");
            if (teamId == 0L) break;
            team = this.controller.getEntity(teamId, CRUDTable.TEAM);
        }

        return new Player(name, age, teamId == 0L ? null : team.get());
    }

    @Override
    public Player readEntityToUpdate(Player player) {
        String name = this.readToUpdateString("Nombre", player.getName(), Player.MIN_NAME_LEN, Player.MAX_NAME_LEN);
        player.setName(name);

        int age = this.readToUpdateInt("Edad", player.getAge(), Player.MIN_AGE, Player.MAX_AGE);
        player.setAge(age);

        Optional<Team> team = Optional.empty();
        Long teamId;
        while (team.isEmpty()) {
            if (player.getTeam() == null)
                teamId = this.readId("ID del equipo (Escribe 0 para no añadir equipo al jugador): ");
            else {
                this.print("(0 para remover) ");
                teamId = this.readToUpdateId("ID del equipo", player.getTeam().getId());
            }
            if (teamId == 0) break;

            team = this.controller.getEntity(teamId, CRUDTable.TEAM);
        }

        player.setTeam(team.get());

        return player;
    }
}