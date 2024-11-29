package mx.edu.uaz.is.poo2.carger.view.windows.crud;

import java.sql.Timestamp;
import java.util.Optional;

import mx.edu.uaz.is.poo2.carger.model.constants.CRUDTable;
import mx.edu.uaz.is.poo2.carger.model.entities.Match;
import mx.edu.uaz.is.poo2.carger.model.entities.Team;

public class MatchCRUDWindow extends CRUDWindow<Match> {

    public MatchCRUDWindow() {
        super("MacthCRUD", CRUDTable.MATCH);
    }

    @Override
    public void showWindow() {
        this.controller.startMatchWindow();
    }

    @Override
    public Match readEntity() {
        int gameWeek = this.readInt("Número de jornada: ", Match.MIN_GAME_WEEK, Match.MAX_GAME_WEEK);
        Timestamp date = this.readDate();

        Optional<Team> home = Optional.empty();
        Long homeId;
        while (home.isEmpty()) {
            homeId = this.readId("ID del equipo local (Escribe 0 para no añadir equipo local): ");
            if (homeId == 0)
                break;
            home = this.controller.getEntity(homeId, CRUDTable.TEAM);
        }

        Optional<Team> away = Optional.empty();
        Long awayId;
        while (away.isEmpty()) {
            awayId = this.readId("ID del equipo visitante (Escribe 0 para no añadir equipo visitante): ");
            if (awayId == 0)
                break;
            away = this.controller.getEntity(awayId, CRUDTable.TEAM);
        }
        
        return new Match(date, gameWeek, home.get(), away.get());
    }

    @Override
    public Match readEntityToUpdate(Match match) {
        int gameWeek = this.readToUpdateInt("Número de jornada", match.getGameWeek(), Match.MIN_GAME_WEEK, Match.MAX_GAME_WEEK);
        match.setGameWeek(gameWeek);

        Timestamp date = this.readToUpdateDate("Fecha", match.getDate());
        match.setDate(date);

        Optional<Team> home = Optional.empty();
        Long homeId;
        while (home.isEmpty()) {
            if (match.getHomeTeam() != null) {
                this.print("(0 para remover) ");
                homeId = this.readToUpdateId("ID del equipo local", match.getHomeTeam().getId());
            } else {
                homeId = this.readId(
                    "ID del equipo local (Escribe 0 para no añadir equipo loca): ",
                    -1L, Long.MAX_VALUE
                );
            }
            if (homeId == 0) break;

            home = this.controller.getEntity(homeId, CRUDTable.TEAM);
        }
        match.setHomeTeam(home.get());

        Optional<Team> away = Optional.empty();
        Long awayId;
        while (away.isEmpty()) {
            if (match.getAwayTeam() != null) {
                this.print("(0 para remover) ");
                awayId = this.readToUpdateId("ID del equipo visitante", match.getAwayTeam().getId());
            } else {
                awayId = this.readId(
                    "ID del equipo visitante (Escribe 0 para no añadir equipo visitante): ",
                    -1L, Long.MAX_VALUE
                );
            }
            if (awayId == 0) break;

            away = this.controller.getEntity(awayId, CRUDTable.TEAM);
        }
        match.setAwayTeam(away.get());

        return match;
    }
}