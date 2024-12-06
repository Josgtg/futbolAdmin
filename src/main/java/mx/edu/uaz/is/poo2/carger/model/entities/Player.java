package mx.edu.uaz.is.poo2.carger.model.entities;

import java.io.Serializable;

import jakarta.persistence.*;
import static jakarta.persistence.CascadeType.*;

@Entity
public class Player implements IEntity, Serializable {
    private static final long serialVersionUID = 1L;
    public static final int MIN_AGE = 0;
    public static final int MAX_AGE = 128;
    public static final int MIN_NAME_LEN = 0;
    public static final int MAX_NAME_LEN= 70;
    
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

    public Player() { }

    public Player(String name, int age) {
        this.name = name;
        this.age = age;
        this.team = null;
        this.gamesPlayed = 0;
        this.goals = 0;
        this.assists = 0;
        this.redCards = 0;
        this.yellowCards = 0;
    }

    public Player(String name, int age, Team team) {
        this(name, age);
        this.team = team;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public void sumGamesPlayed(int sum) {
        this.gamesPlayed += sum;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public void sumGoals(int sum) {
        this.goals += sum;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public void sumAssists(int sum) {
        this.assists += sum;
    }

    public int getRedCards() {
        return redCards;
    }

    public void setRedCards(int redCards) {
        this.redCards = redCards;
    }

    public void sumRedCards(int sum) {
        this.redCards += sum;
    }

    public int getYellowCards() {
        return yellowCards;
    }

    public void setYellowCards(int yellowCards) {
        this.yellowCards = yellowCards;
    }

    public void sumYellowCards(int sum) {
        this.yellowCards += sum;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public String basicToString() {
        return String.format("%s", this.getName());
    }

    @Override
    public String toString() {
        return String.format(
            "Player{ id: %d, name: %s, team: %s, age: %d, gamesPlayed: %d, goals: %d, assists: %d, yellowCards: %d, redCards: %d }",
            this.id, this.name, this.team != null ? this.team.getName() : "NOTEAM", this.age, this.gamesPlayed,
            this.goals, this.assists, this.yellowCards, this.redCards
        );
    }
}
