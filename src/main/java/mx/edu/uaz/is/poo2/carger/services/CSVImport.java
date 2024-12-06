package mx.edu.uaz.is.poo2.carger.services;

import java.io.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import static java.util.Map.entry;
import java.util.Optional;
import java.util.function.Function;

import com.opencsv.CSVReader;

import mx.edu.uaz.is.poo2.carger.model.constants.*;
import mx.edu.uaz.is.poo2.carger.model.entities.*;
import mx.edu.uaz.is.poo2.carger.services.dao.*;
import mx.edu.uaz.is.poo2.carger.view.Logger;
import mx.edu.uaz.is.poo2.carger.services.simulator.*;

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

    private CSVReader playerReader;
    private CSVReader teamReader;
    private CSVReader matchReader;

    private Map<String, Team> teamsMap;
    private final Map<String, List<Player>> playerListMap;

    public CSVImport() {
        this.logger = new Logger(this.getClass().getSimpleName());
        this.teamsMap = new HashMap<>();
        this.playerListMap = new HashMap<>();
    
        this.matchCols = Map.ofEntries(
            entry("date", 0),
            entry("homeTeam", 4),
            entry("awayTeam", 5),
            entry("gameWeek", 7),
            entry("homeTeamGoals", 12),
            entry("awayTeamGoals", 13)
        );
        this.teamCols = Map.ofEntries(
            entry("name", 1)
        );
        this.playerCols = Map.ofEntries(
            entry("name", 0),
            entry("age", 1),
            entry("team", 7)
        );

        this.matchTablePath = "media/matchTable.csv";
        this.teamTablePath = "media/teamTable.csv";
        this.playerTablePath = "media/playerTable.csv";

        try {
            this.matchReader = new CSVReader(new FileReader(this.matchTablePath));
        } catch (FileNotFoundException e) {
            this.logger.fatal(ErrorKind.IO_ERROR, Errors.fileNotFound(this.matchTablePath), e);
        }
        try {
            this.teamReader = new CSVReader(new FileReader(this.teamTablePath));
        } catch (FileNotFoundException e) {
            this.logger.fatal(ErrorKind.IO_ERROR, Errors.fileNotFound(this.teamTablePath), e);
        }
        try {
            this.playerReader = new CSVReader(new FileReader(this.playerTablePath));
        } catch (FileNotFoundException e) {
            this.logger.fatal(ErrorKind.IO_ERROR, Errors.fileNotFound(this.playerTablePath), e);
        }

        this.matchDAOService = DAOServiceFactory.getMatchDAOService();
        this.teamDAOService = DAOServiceFactory.getTeamDAOService();
        this.playerDAOService = DAOServiceFactory.getPlayerDAOService();
    }

    private int saveTeam(String[] data) {
        String teamName = data[this.teamCols.get("name")];
        Team team = new Team(teamName);
        this.teamsMap.put(teamName, team);
        return 0;
    }

    private void getTeamList() {
        this.doEachCSVLine(this.teamReader, this::saveTeam);
    }

    public void fillTeamTable() {
        this.getTeamList();
        for (Entry<String, Team> entry : this.teamsMap.entrySet())
            this.teamDAOService.save(entry.getValue());
        this.teamsMap = new HashMap<>();
        for (Team team : this.teamDAOService.findAll())
            this.teamsMap.put(team.getName(), team);
    }

    private int savePlayer(String[] data) {
        Player player = new Player(
            data[this.playerCols.get("name")],
            Integer.parseInt(data[this.playerCols.get("age")])
        );
        player.setGoals(PlayerSimulator.getRandomGoals());
        player.setAssists(PlayerSimulator.getRandomAssists());
        player.setYellowCards(PlayerSimulator.getRandomYellowCards());
        player.setRedCards(PlayerSimulator.getRandomRedCards());
        player.setGamesPlayed(PlayerSimulator.getRandomGamesPlayed());
        String teamName = data[this.playerCols.get("team")];
        if (this.playerListMap.get(teamName) == null)
            this.playerListMap.put(teamName, new ArrayList<>());
        this.playerListMap.get(teamName).add(player);

        return 0;
    }

    private void getPlayerLists() {
        this.doEachCSVLine(this.playerReader, this::savePlayer);
    }

    public void fillPlayerTable() {
        this.getPlayerLists();
        for (Entry<String, List<Player>> entry : this.playerListMap.entrySet()) {
            Optional<Team> team = this.teamDAOService.findByName(entry.getKey());
            if (team.isPresent()) {
                team.get().setPlayers(entry.getValue());
                this.teamDAOService.update(team.get());
            } else {
                for (Player p : entry.getValue())
                    this.playerDAOService.save(p);
            }
        }
    }

    private int saveMatch(String[] data) {
        Timestamp ts = Timestamp.valueOf(LocalDateTime.now());
        // Parsear un número como 8232738 a un timestamp, que es lo que está en el csv

        Team homeTeam = this.teamsMap.get(data[this.matchCols.get("homeTeam")]);
        Team awayTeam = this.teamsMap.get(data[this.matchCols.get("awayTeam")]);
        Match match = new Match(
            ts,
            Integer.parseInt(data[this.matchCols.get("gameWeek")]),
            homeTeam,
            awayTeam,
            Integer.parseInt(data[this.matchCols.get("homeTeamGoals")]),
            Integer.parseInt(data[this.matchCols.get("awayTeamGoals")]),
            null    
        );
        match.setPlayed(true);
        matchDAOService.save(match);

        return 0;
    }

    public void fillMatchTable() {
        this.doEachCSVLine(this.matchReader, this::saveMatch);
    }

    public void fillAllTables() {
        if (
            !this.teamDAOService.findAll().isEmpty() ||
            !this.playerDAOService.findAll().isEmpty() ||
            !this.matchDAOService.findAll().isEmpty()
        ) return;
        this.logger.info(Messages.importingTable(CRUDTable.TEAM.getName()));
        this.fillTeamTable();
        // this.logger.info(Messages.tableImported(CRUDTable.TEAM.getName()));

        this.logger.info(Messages.importingTable(CRUDTable.PLAYER.getName()));
        this.fillPlayerTable();
        // this.logger.info(Messages.tableImported(CRUDTable.PLAYER.getName()));

        this.logger.info(Messages.importingTable(CRUDTable.MATCH.getName()));
        this.fillMatchTable();
        // this.logger.info(Messages.tableImported(CRUDTable.MATCH.getName()));
    }

    public void doEachCSVLine(CSVReader csv, Function<String[], Integer> function) {
        try {
            String[] nextLine;
            csv.readNext(); // Títulos de columnas
            while ((nextLine = csv.readNext()) != null) { 
                function.apply(nextLine);
            }
        } catch (IOException e) {
            this.logger.fatal(ErrorKind.IO_ERROR, Errors.IO_ERROR, e);
        }
    }
}