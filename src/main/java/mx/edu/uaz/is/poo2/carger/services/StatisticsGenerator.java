package mx.edu.uaz.is.poo2.carger.services;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import mx.edu.uaz.is.poo2.carger.model.constants.Errors;
import mx.edu.uaz.is.poo2.carger.services.dao.*;
import mx.edu.uaz.is.poo2.carger.model.entities.*;
import mx.edu.uaz.is.poo2.carger.view.Logger;

public class StatisticsGenerator {
    private static StatisticsGenerator instance;

    private final TeamDAOService teamDAOService;
    private final MatchDAOService matchDAOService;
    // private final PlayerDAOService playerDAOService;
    private final Logger logger;

    private StatisticsGenerator() {
        this.logger = new Logger(this.getClass().getSimpleName());
        this.teamDAOService = DAOServiceFactory.getTeamDAOService();
        this.matchDAOService = DAOServiceFactory.getMatchDAOService();
        // this.playerDAOService = DAOServiceFactory.getPlayerDAOService();
    }

    public void updateLeague() {
        List<Match> matches = this.matchDAOService.findAll();
        Optional<Team> optHome;
        Optional<Team> optAway;
        Team home;
        Team away;
        for (Match match : matches) {
            optHome = teamDAOService.find(match.getHomeTeam());
            if (optHome.isEmpty()) {
                this.logger.warn(Errors.entityNotFound(match.getHomeTeam()));
                continue;
            }

            optAway = teamDAOService.find(match.getAwayTeam());
            if (optAway.isEmpty()) {
                this.logger.warn(Errors.entityNotFound(match.getAwayTeam()));
                continue;
            }

            home = optHome.get();
            away = optAway.get();
            
            home.sumGoalsScored(match.getHomeTeamGoals());
            home.sumGoalsReceived(match.getAwayTeamGoals());

            away.sumGoalsScored(match.getAwayTeamGoals());
            away.sumGoalsReceived(match.getHomeTeamGoals());

            if (match.getHomeTeamGoals() > match.getAwayTeamGoals()) {
                home.sumWins(1);
                away.sumLosses(1);
            } else if (match.getHomeTeamGoals() < match.getAwayTeamGoals()) {
                home.sumLosses(1);
                away.sumWins(1);
            } else {
                home.sumDraws(1);
                away.sumDraws(1);
            }

            teamDAOService.update(home);
            teamDAOService.update(away);
        }
    }

    public List<Team> getTeamsByPoints() {
        List<Team> teams = this.teamDAOService.findAll();
        teams.sort(new SortByPoints());
        return teams;
    }

    public static StatisticsGenerator getInstance() {
        if (instance == null)
            instance = new StatisticsGenerator();
        return instance;
    }
}

class SortByPoints implements Comparator<Team> {
    @Override
    public int compare(Team a, Team b) {
        return b.getPoints() - a.getPoints();
    }
}