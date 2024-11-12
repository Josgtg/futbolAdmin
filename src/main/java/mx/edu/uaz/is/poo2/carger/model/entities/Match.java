package mx.edu.uaz.is.poo2.carger.model.entities;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.*;
import static jakarta.persistence.CascadeType.*;
import mx.edu.uaz.is.poo2.carger.model.entities.events.Event;

@Entity
public class Match implements IEntity, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Date date;
    @OneToOne(cascade={PERSIST, MERGE, REFRESH})
    private Team homeTeam;
    @OneToOne(cascade={PERSIST, MERGE, REFRESH})
    private Team awayTeam;
    @Column
    private int gameWeek;
    @Column
    private int homeTeamGoals;
    @Column
    private int awayTeamGoals;
    @OneToMany(cascade=CascadeType.ALL)
    private List<Event> events;

    public Match() {}

    public Match(Date date, Team homeTeam, Team awayTeam, int gameWeek) {
        this.date = date;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.gameWeek = gameWeek;
        this.homeTeamGoals = 0;
        this.awayTeamGoals = 0;
        this.events = new ArrayList<>();
    }

    public Match(
        Date date,
        Team homeTeam,
        Team awayTeam,
        int gameWeek,
        int homeTeamGoals,
        int awayTeamGoals,
        List<Event> events
    ) {
        this(date, homeTeam, awayTeam, gameWeek);
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    public int getHomeTeamGoals() {
        return homeTeamGoals;
    }

    public void setHomeTeamGoals(int homeTeamGoals) {
        this.homeTeamGoals = homeTeamGoals;
    }

    public int getAwayTeamGoals() {
        return awayTeamGoals;
    }

    public void setAwayTeamGoals(int awayTeamGoals) {
        this.awayTeamGoals = awayTeamGoals;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public void addEvent(Event e) {
        this.events.add(e);
    }

    public void removeEvent(Event e) {
        this.events.remove(e);
    }
}
