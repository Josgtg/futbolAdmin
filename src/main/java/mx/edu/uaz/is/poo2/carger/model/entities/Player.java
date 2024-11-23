package mx.edu.uaz.is.poo2.carger.model.entities;

import java.io.Serializable;

import jakarta.persistence.*;
import static jakarta.persistence.CascadeType.*;

@Entity
public class Player implements IEntity, Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
    @Column
    private int age;
    @Column
    private int gamesPlayed;
    @Column
    private int goals;
    @Column
    private int assists;
    @Column
    private int redCards;
    @Column
    private int yellowCards;
    @ManyToOne(cascade={PERSIST, MERGE, REFRESH})
    private Team team;

    public Player() {
        id = null;
    }

    public Player(String name, int age, Team team) {
        this.name = name;
        this.age = age;
        this.team = team;
        this.gamesPlayed = 0;
        this.goals = 0;
        this.assists = 0;
        this.redCards = 0;
        this.yellowCards = 0;
        this.id = null;
    }

    public Player(
        String name, 
        int age, 
        Team team,
        int gamesPlayed,
        int goals, 
        int assists,
        int redCards, 
        int yellowCards
    ){
        this(name, age, team);
        this.gamesPlayed = gamesPlayed;
        this.goals = goals;
        this.assists = assists;
        this.redCards = redCards;
        this.yellowCards = yellowCards;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public int getage() {
        return age;
    }

    public void setage(int age) {
        this.age = age;
    }

    public int getgamesPlayed() {
        return gamesPlayed;
    }

    public void setgamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getgoals() {
        return goals;
    }

    public void setgoals(int goals) {
        this.goals = goals;
    }

    public int getassists() {
        return assists;
    }

    public void setassists(int assists) {
        this.assists = assists;
    }

    public int getredCards() {
        return redCards;
    }

    public void setredCards(int redCards) {
        this.redCards = redCards;
    }

    public int getyellowCards() {
        return yellowCards;
    }

    public void setyellowCards(int yellowCards) {
        this.yellowCards = yellowCards;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return String.format(
            "Player{ name: %s, team: %s, age: %d, gamesPlayed: %d, goals: %d, assists: %d, yellowCards: %d, redCards: %d }",
            this.name, this.team.getName(), this.age, this.gamesPlayed,
            this.goals, this.assists, this.yellowCards, this.redCards
        );
    }
}
