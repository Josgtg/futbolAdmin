package mx.edu.uaz.is.poo2.carger.services;

import java.io.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;
import static java.util.Map.entry;
import java.util.Optional;
import java.util.function.Function;

import com.opencsv.CSVReader;

import mx.edu.uaz.is.poo2.carger.model.constants.*;
import mx.edu.uaz.is.poo2.carger.model.entities.*;
import mx.edu.uaz.is.poo2.carger.services.dao.*;
import mx.edu.uaz.is.poo2.carger.view.Logger;

public class CSVImport {

    private final String matchTablePath;
    private final String teamTablePath;
    private final String playerTablePath;

    private final Map<String, Integer> matchCols;
    private final Map<String, Integer> teamCols;
    private final Map<String, Integer> playerCols;

    private final MatchDAOService matchDAOService;
    private final TeamDAOService teamDAOService;
    private final PlayerDAOService playerDAOService;
    private final Logger logger;

    public CSVImport() {
        this.logger = new Logger(this.getClass().getSimpleName());
    
        this.matchCols = Map.ofEntries(
            entry("date", 0),
            entry("homeTeam", 4),
            entry("awayTeam", 5),
            entry("gameWeek", 7),
            entry("homeTeamGoals", 12),
            entry("awayTeamGoals", 13)
        );
        this.teamCols = Map.ofEntries(entry("name", 1));
        this.playerCols = Map.ofEntries(
            entry("name", 0),
            entry("age", 1),
            entry("team", 7)
        );

        this.matchTablePath = "media/matchTable.csv";
        this.teamTablePath = "media/teamTable.csv";
        this.playerTablePath = "media/playerTable.csv";

        this.matchDAOService = DAOServiceFactory.getMatchDAOService();
        this.teamDAOService = DAOServiceFactory.getTeamDAOService();
        this.playerDAOService = DAOServiceFactory.getPlayerDAOService();
    }

    private int saveTeam(String[] data) {
        Team team = new Team(data[this.teamCols.get("name")]);
        teamDAOService.save(team);
        return 0;
    }

    public void fillTeamTable() {
        this.doEachCSVLine(this.teamTablePath, this::saveTeam);
    }

    private int savePlayer(String[] data) {
        Optional<Team> team = this.teamDAOService.findByName(data[this.playerCols.get("team")]);
        Player player = new Player(
            data[this.playerCols.get("name")],
            Integer.parseInt(data[this.playerCols.get("age")])
        );
        if (team.isPresent()) {
            team.get().getPlayers().size();
            team.get().addPlayer(player);
            this.teamDAOService.update(team.get());
        } else {
            this.playerDAOService.save(player);
        }
        return 0;
    }

    public void fillPlayerTable() {
        this.doEachCSVLine(this.playerTablePath, this::savePlayer);
    }

    private int saveMatch(String[] data) {
        var ld = LocalDateTime.now();
        Optional<Team> homeTeam = this.teamDAOService.findByName(data[this.matchCols.get("homeTeam")]);
        Optional<Team> awayTeam = this.teamDAOService.findByName(data[this.matchCols.get("awayTeam")]);
        Match match = new Match(
            Timestamp.valueOf(ld),  // Parsear un número como 8232738 a un timestamp, que es lo que está en el csv
            Integer.parseInt(data[this.matchCols.get("gameWeek")]),
            homeTeam.orElse(null),
            awayTeam.orElse(null),
            Integer.parseInt(data[this.matchCols.get("homeTeamGoals")]),
            Integer.parseInt(data[this.matchCols.get("awayTeamGoals")]),
            null    
        );
        matchDAOService.save(match);
        return 0;
    }

    public void fillMatchTable() {
        this.doEachCSVLine(this.matchTablePath, this::saveMatch);
    }

    public void fillAllTables() {
        this.fillTeamTable();
        this.fillPlayerTable();
        this.fillMatchTable();
    }

    public void doEachCSVLine(String path, Function<String[], Integer> function) {
        try (CSVReader csv = new CSVReader(new FileReader(path))) {
            String[] nextLine;
            csv.readNext(); // Títulos de columnas
            while ((nextLine = csv.readNext()) != null) { 
                function.apply(nextLine);
            }
        } catch (FileNotFoundException e) {
            this.logger.fatal(ErrorKind.IO_ERROR, Errors.fileNotFound(path), e);
        } catch (IOException e) {
            this.logger.fatal(ErrorKind.IO_ERROR, Errors.IO_ERROR, e);
        }
    }
}