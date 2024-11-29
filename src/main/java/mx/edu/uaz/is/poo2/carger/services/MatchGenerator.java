package mx.edu.uaz.is.poo2.carger.services;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import mx.edu.uaz.is.poo2.carger.services.dao.DAOService;
import mx.edu.uaz.is.poo2.carger.model.entities.Match;
import mx.edu.uaz.is.poo2.carger.model.entities.Team;

public class MatchGenerator {
    private List<Team> teams;
    private final DAOService<Match> dao;
    private final LocalDateTime firstDate;
    private final int MAX_MATCHES_PER_DAY = 3;
    private final int INITIAL_HOUR=19;
    private final int INITIAL_MINUTE=0;
    private final String REST_TEAM = "REST";

    private int numMatches;
    private int numRounds;
    private int numMatchesPerRound;
    private int numTeams;

    public MatchGenerator(DAOService<Match> dao){
        this.teams = new ArrayList<>();
        this.dao = dao;
        this.numMatches=0;
        this.numRounds=0;
        this.numMatchesPerRound=0;
        this.numTeams=0;
        //Calcula la fecha inicial
        LocalDate actualDate = LocalDate.now();
        LocalDate nextFriday = actualDate.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
        this.firstDate = LocalDateTime.of(nextFriday, LocalTime.of(INITIAL_HOUR, INITIAL_MINUTE));
    }


    public void generateMatches(List<Team> teamss){
        this.teams = teamss;
        boolean oddflag=false;
        if (this.teams.size()%2 == 1) {
            this.teams.add(new Team(REST_TEAM));
            oddflag=true;
        }
        this.numTeams = this.teams.size();
        this.numMatches = (numTeams*(numTeams--))/2;
        this.numRounds = numTeams--;

        this.numMatchesPerRound = numMatches/numRounds;
        if (oddflag) {
            numMatchesPerRound--;
        }
        //generar las fechas de los partidos
        ArrayList<Timestamp> dates = generateMatchDays();
        
        ArrayList<Team> rotationalTeams = new ArrayList<>(this.teams);
        Team constantTeam = rotationalTeams.remove(0);
        int datesIterator = 0;
        //Emparejamientos
        for (int i = 0; i < numRounds; i++) {
            Match match;
            Team t1;
            Team t2;
            //emparejamiento equipo fijo y ultimo de los equipos que rotan
            t1 = constantTeam;
            t2 = rotationalTeams.get(rotationalTeams.size() - 1);
            if (!(t1.getName().equals(REST_TEAM) || t2.getName().equals(REST_TEAM))) {
                match = new Match(dates.get(datesIterator++), i + 1, t1, t2);
                dao.save(match);
            }
            //emparejamiento de equipos que rotan restantes
            for (int j = 0; j < rotationalTeams.size() / 2; j++) {
                t1 = rotationalTeams.get(j);
                t2 = rotationalTeams.get(rotationalTeams.size() - 2  - j);
                if (!(t1.getName().equals(REST_TEAM) || t2.getName().equals(REST_TEAM))) {
                    match = new Match(dates.get(datesIterator++), i + 1, t1, t2);
                    dao.save(match);
                }
            }
            //rotacion de equipos
            rotationalTeams.add(0, rotationalTeams.remove(rotationalTeams.size() - 1));
        }
    }

    private ArrayList<Timestamp> generateMatchDays(){
        ArrayList<Timestamp> dates = new ArrayList<>();
        int daySum = -1;
        LocalDateTime firstDateRound;
        LocalDateTime date;
        for(int j = 0; j < numRounds; j++){
            //La primer fecha de la jornada x se obtiene sumando x semanas a la fecha inicial
            firstDateRound = firstDate.plusWeeks(j);
            for (int i = 0; i < numMatchesPerRound; i++) {
                if (i%MAX_MATCHES_PER_DAY == 0) {
                    daySum++;
                }
                date = firstDateRound.plusDays(daySum);
                date = date.plusMinutes((i%MAX_MATCHES_PER_DAY)*40);
                dates.add(Timestamp.valueOf(date));
            }
        }
        return dates;
    }
}
