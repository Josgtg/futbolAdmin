package mx.edu.uaz.is.poo2.carger.model.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.*;
import static jakarta.persistence.CascadeType.*;
import mx.edu.uaz.is.poo2.carger.model.entities.events.Event;

@Entity
public class Match implements IEntity, Serializable {
    private static final long serialVersionUID = 1L;
    public static final int MIN_GAME_WEEK = 0;
    public static final int MAX_GAME_WEEK = 128;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Timestamp date;
    @ManyToOne(cascade={PERSIST, MERGE, REFRESH})
    private Team homeTeam;
    @ManyToOne(cascade={PERSIST, MERGE, REFRESH})
    private Team awayTeam;
    @Column
    private int gameWeek;
    @Column
    private boolean isPlayed;
    @Column
    private boolean isAnalized;
    @Column
    private int homeTeamGoals;
    @Column
    private int awayTeamGoals;
    @OneToMany(cascade=ALL)
    private List<Event> events;

    public Match() {}

    public Match(Timestamp date, int gameWeek) {
        this.date = date;
        this.gameWeek = gameWeek;
        this.isPlayed = false;
        this.isAnalized = false;
    } 

    public Match(Timestamp date, int gameWeek, Team homeTeam, Team awayTeam) {
        this(date, gameWeek);
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamGoals = 0;
        this.awayTeamGoals = 0;
        this.isPlayed = false;
        this.isAnalized = false;
        this.events = new ArrayList<>();
    }

    public Match(
        Timestamp date,
        int gameWeek,
        Team homeTeam,
        Team awayTeam,
        int homeTeamGoals,
        int awayTeamGoals,
        List<Event> events
    ) {
        this(date, gameWeek, homeTeam, awayTeam);
        this.homeTeamGoals = homeTeamGoals;
        this.awayTeamGoals = awayTeamGoals;
        this.events = events;
    }

    // Getters y setters

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public int getGameWeek() {
        return gameWeek;
    }

    public void setGameWeek(int gameWeek) {
        this.gameWeek = gameWeek;
    }

    public boolean isPlayed() {
        return isPlayed;
    }

    public void setPlayed(boolean isPlayed) {
        this.isPlayed = isPlayed;
    }
    
    public int getHomeTeamGoals() {
        return homeTeamGoals;
    }

    public void setHomeTeamGoals(int homeTeamGoals) {
        this.homeTeamGoals = homeTeamGoals;
    }

    public void sumHomeTeamGoals(int sum) {
        this.homeTeamGoals += sum;
    }

    public int getAwayTeamGoals() {
        return awayTeamGoals;
    }

    public void setAwayTeamGoals(int awayTeamGoals) {
        this.awayTeamGoals = awayTeamGoals;
    }

    public void sumAwayTeamGoals(int sum) {
        this.awayTeamGoals += sum;
    }

    public List<Event> getEvents() {
        if (this.events == null)
            this.events = new ArrayList<>();
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public void addEvent(Event e) {
        this.getEvents();
        this.events.add(e);
    }

    public void removeEvent(Event e) {
        this.getEvents();
        this.events.remove(e);
    }

    @Override
    public String basicToString() {
        return String.format(
            "Week %d: %s %d - %d %s",
            this.gameWeek, 
            this.homeTeam != null ? this.homeTeam.getName() : "NOTEAM", this.homeTeamGoals, 
            this.awayTeamGoals , this.awayTeam != null ? this.awayTeam.getName() : "NOTEAM"
        );
    }

    @Override
    public String toString() {
        return String.format(
            "Match{ id: %d, date: %s, gameWeek: %d, homeTeam: %s, homeTeamGoals: %d, awayTeam: %s, awayTeamGoals: %d, %s }",
            this.id, this.date, this.gameWeek, this.homeTeam != null ? this.homeTeam.getName() : "NOTEAM",
            this.homeTeamGoals, this.awayTeam != null ? this.awayTeam.getName() : "NOTEAM", this.awayTeamGoals, this.isPlayed ? "Ya jugado" : "Aun no jugado"
        );
    }

    public boolean isAnalized() {
        return isAnalized;
    }

    public void setAnalized(boolean isAnalized) {
        this.isAnalized = isAnalized;
    }
}
