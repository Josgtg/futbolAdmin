package mx.edu.uaz.is.poo2.carger.services;

import java.util.Comparator;
import java.util.List;
import java.util.HashMap;
import java.util.function.BiFunction;

import mx.edu.uaz.is.poo2.carger.model.constants.Errors;
import mx.edu.uaz.is.poo2.carger.model.constants.columns.*;
import mx.edu.uaz.is.poo2.carger.services.dao.*;
import mx.edu.uaz.is.poo2.carger.model.entities.*;
import mx.edu.uaz.is.poo2.carger.model.entities.events.Event;
import mx.edu.uaz.is.poo2.carger.view.Logger;

public class StatisticsGenerator {
    private static StatisticsGenerator instance;

    private final TeamDAOService teamDAOService;
    private final MatchDAOService matchDAOService;
    private final PlayerDAOService playerDAOService;
    private final Logger logger;

    private StatisticsGenerator() {
        this.logger = new Logger(this.getClass().getSimpleName());
        this.teamDAOService = DAOServiceFactory.getTeamDAOService();
        this.matchDAOService = DAOServiceFactory.getMatchDAOService();
        this.playerDAOService = DAOServiceFactory.getPlayerDAOService();
    }

    public void updateLeague() {
        List<Match> matches = this.matchDAOService.findAll();
        Team home;
        Team away;
        for (Match match : matches) {
            if (match.isAnalized() || !match.isPlayed())
                continue;

            home = match.getHomeTeam();
            if (home == null) {
                this.logger.warn(Errors.entityNotFound(match.getHomeTeam())); continue;
            }
            away = match.getAwayTeam();
            if (away == null) {
                this.logger.warn(Errors.entityNotFound(match.getAwayTeam())); continue;
            }

            for (Event event : match.getEvents()) {
                switch (event.getEventKind()) {
                    case GOAL -> {
                        event.getFirstPlayer().sumGoals(1);
                        if (event.getSecondPlayer().isPresent()) {
                            event.getSecondPlayer().get().sumAssists(1);
                            this.playerDAOService.update(event.getSecondPlayer().get());
                        }
                        if (event.getFirstPlayer().getTeam().getId().equals(home.getId()))
                            match.sumHomeTeamGoals(1);
                        else
                            match.sumAwayTeamGoals(1);
                    }
                    case YELLOW_CARD -> event.getFirstPlayer().sumYellowCards(1);
                    case RED_CARD -> event.getFirstPlayer().sumRedCards(1);
                    case SUB -> {}
                    case INJURY -> {}
                }
                this.playerDAOService.update(event.getFirstPlayer());
            }

            home.sumGoalsScored(match.getHomeTeamGoals()); home.sumGoalsReceived(match.getAwayTeamGoals());
            away.sumGoalsScored(match.getAwayTeamGoals()); away.sumGoalsReceived(match.getHomeTeamGoals());

            if (match.getHomeTeamGoals() > match.getAwayTeamGoals()) {
                home.sumWins(1); away.sumLosses(1);
            } else if (match.getHomeTeamGoals() < match.getAwayTeamGoals()) {
                home.sumLosses(1); away.sumWins(1);
            } else {
                home.sumDraws(1); away.sumDraws(1);
            }

            home.getPlayers().size(); away.getPlayers().size();
            for (Player p : home.getPlayers())
                p.sumGamesPlayed(1);
            for (Player p : away.getPlayers())
                p.sumGamesPlayed(1);

            teamDAOService.update(home);
            teamDAOService.update(away);
            match.setAnalized(true);
            matchDAOService.update(match);
        }
    }

    public List<Team> getTeamsBy(LeagueColumns column) {
        return this.getTeamsBy(column, false);
    }

    public List<Team> getTeamsBy(LeagueColumns column, boolean reversed) {
        return this.getTeamsBy(switch (column) {
            case POINTS -> (Team a, Team b) -> { return b.getPoints() - a.getPoints(); };
            case NAME -> (Team a, Team b) -> { return a.getName().compareTo(b.getName()); };
            case WINS -> (Team a, Team b) -> { return b.getWins() - a.getWins(); };
            case DRAWS -> (Team a, Team b) -> { return b.getDraws() - a.getDraws(); };
            case LOSSES -> (Team a, Team b) -> { return b.getLosses() - a.getLosses(); };
            case GF -> (Team a, Team b) -> { return b.getGoalsScored() - a.getGoalsScored(); };
            case GA -> (Team a, Team b) -> { return b.getGoalsReceived() - a.getGoalsReceived(); };
            case GD -> (Team a, Team b) -> { return b.getGoalDifference() - a.getGoalDifference(); };
        }, reversed);
    }

    private List<Team> getTeamsBy(BiFunction<Team, Team, Integer> comparator, boolean reversed) {
        List<Team> teams = this.teamDAOService.findAll();
        teams.sort(new SortBy(comparator, reversed));
        return teams;
    }

    public List<Player> getPlayersBy(PlayerColumns column) {
        return this.getPlayersBy(column, false);
    }

    public List<Player> getPlayersBy(PlayerColumns column, boolean reversed) {
        return this.getPlayersBy(switch (column) {
            case GOALS -> (Player a, Player b) -> { return b.getGoals() - a.getGoals(); };
            case NAME -> (Player a, Player b) -> { return a.getName().compareTo(b.getName()); };
            case ASSISTS -> (Player a, Player b) -> { return b.getAssists() - a.getAssists(); };
            case AGE -> (Player a, Player b) -> { return b.getAge() - a.getAge(); };
            case YELLOW_CARDS -> (Player a, Player b) -> { return b.getYellowCards() - a.getYellowCards(); };
            case RED_CARDS -> (Player a, Player b) -> { return b.getRedCards() - a.getRedCards(); };
        }, reversed);
    }

    private List<Player> getPlayersBy(BiFunction<Player, Player, Integer> comparator, boolean reversed) {
        List<Player> players = this.playerDAOService.findAll();
        players.sort(new SortBy(comparator, reversed));
        return players;
    }

    public static StatisticsGenerator getInstance() {
        if (instance == null)
            instance = new StatisticsGenerator();
        return instance;
    }

    public <T extends IEntity> HashMap<Long, T> hashMapFromList(List<T> list) {
        HashMap map = new HashMap<>();
        for (T t : list)
            map.put(t.getId(), t);
        return map;
    }

    public void revertMatch(Match match) {
        if (!match.isAnalized()) return;

        Team home = match.getHomeTeam();
        if (home == null) {
            this.logger.warn(Errors.entityNotFound(match.getHomeTeam())); return;
        }
        Team away = match.getAwayTeam();
        if (away == null) {
            this.logger.warn(Errors.entityNotFound(match.getAwayTeam())); return;
        }

        if (match.getHomeTeamGoals() > match.getAwayTeamGoals()) {
            home.sumWins(-1); away.sumLosses(-1);
        } else if (match.getHomeTeamGoals() < match.getAwayTeamGoals()) {
            home.sumLosses(-1); away.sumWins(-1);
        } else {
            home.sumDraws(-1); away.sumDraws(-1);
        }

        home.sumGoalsScored(-match.getHomeTeamGoals()); home.sumGoalsReceived(-match.getAwayTeamGoals());
        away.sumGoalsScored(-match.getAwayTeamGoals()); away.sumGoalsReceived(-match.getHomeTeamGoals());

        for (Event event : match.getEvents()) {
            switch (event.getEventKind()) {
                case GOAL -> {
                    event.getFirstPlayer().sumGoals(-1);
                    if (event.getSecondPlayer().isPresent()) {
                        event.getSecondPlayer().get().sumAssists(-1);
                        this.playerDAOService.update(event.getSecondPlayer().get());
                    }
                    if (event.getFirstPlayer().getTeam().getId().equals(home.getId()))
                        match.sumHomeTeamGoals(-1);
                    else
                        match.sumAwayTeamGoals(-1);
                }
                case YELLOW_CARD -> event.getFirstPlayer().sumYellowCards(-1);
                case RED_CARD -> event.getFirstPlayer().sumRedCards(-1);
                case SUB -> {}
                case INJURY -> {}
            }
            this.playerDAOService.update(event.getFirstPlayer());
        }

        home.getPlayers().size(); away.getPlayers().size();
        for (Player p : home.getPlayers())
            p.sumGamesPlayed(-1);
        for (Player p : away.getPlayers())
            p.sumGamesPlayed(-1);

        teamDAOService.update(home);
        teamDAOService.update(away);
        match.setAnalized(false);
        matchDAOService.update(match);
    }
}


// Comparators

class SortBy<T extends IEntity> implements Comparator<T> {
    BiFunction<T, T, Integer> comparator;
    boolean reversed;

    public SortBy(BiFunction<T, T, Integer> function) {
        this(function, false);
    }

    public SortBy(BiFunction<T, T, Integer> function, boolean reversed) {
        this.comparator = function;
        this.reversed = reversed;
    }

    @Override
    public int compare(T a, T b) {
        if (this.reversed)
            return this.comparator.apply(b, a);
        return this.comparator.apply(a, b);
    }
}