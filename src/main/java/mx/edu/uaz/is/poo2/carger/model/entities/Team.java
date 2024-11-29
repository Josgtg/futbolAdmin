package mx.edu.uaz.is.poo2.carger.model.entities;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.*;
import static jakarta.persistence.CascadeType.*;
import static mx.edu.uaz.is.poo2.carger.model.constants.LeagueConstants.*;

@Entity
public class Team implements IEntity, Serializable {
    private static final long serialVersionUID = 1L;

    public static final int MIN_NAME_LEN = 0;
    public static final int MAX_NAME_LEN = 70;
    public static final int MIN_PLAYERS = 9;
    public static final int MAX_PLAYERS = 25;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private int wins;
    @Column
    private int losses;
    @Column
    private int draws;

    @Column
    private int goalsScored;
    @Column
    private int goalsReceived;

    @OneToMany(mappedBy="team", cascade=ALL)
    private List<Player> players;

    public Team() {}
    
    public Team(String name){
        this.name = name;
        this.wins = 0;
        this.losses = 0;
        this.draws = 0;
        this.goalsScored = 0;
        this.goalsReceived = 0;
        this.players = new ArrayList<>();
    }

    public Team(String name, List<Player> players){
        this(name);
        this.players = players;
    }

    public Team(String name, List<Player> players, int v, int l, int d, int gs, int gr){
        this(name, players);
        this.wins = v;
        this.losses = l;
        this.draws = d;
        this.goalsScored = gs;
        this.goalsReceived = gr;
    }
    
    @Override
    public Long getId(){
        return this.id;
    }
    
    public void setId(Long id){
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public List<Player> getPlayers() {
        if (this.players == null)
            this.players = new ArrayList<>();
        return this.players;
    }

    public void setPlayers(List<Player> players) {
        for (Player p : players) p.setTeam(this);
        this.players = players;
    }

    public void addPlayer(Player player) {
        this.getPlayers();
        player.setTeam(this);
        this.players.add(player);
    }

    public void removePlayer(Player player) {
        this.getPlayers();
        if (this.players.remove(player)) {
            player.setTeam(null);
        }
    }

    @Override
    public String basicToString() {
        return String.format(
            "%s, W: %d, D: %d, L: %d",
            this.name, this.wins, this.draws, this.losses
        );
    }

    @Override
    public String toString() {
        return String.format(
            "Team{ id: %d, name: %s, wins: %d, draws: %d, losses: %d, goalsScored: %d, goalsReceived: %d }",
            this.id, this.name, this.wins, this.draws, this.losses, this.goalsScored, this.goalsReceived
        );
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public void sumWins(int toSum) {
        this.wins += toSum;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public void sumLosses(int toSum) {
        this.losses += toSum;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public void sumDraws(int toSum) {
        this.draws += toSum;
    }

    public int getGoalsScored() {
        return goalsScored;
    }

    public void setGoalsScored(int goalsScored) {
        this.goalsScored = goalsScored;
    }

    public void sumGoalsScored(int goalSum) {
        this.goalsScored += goalSum;
    }

    public int getGoalsReceived() {
        return goalsReceived;
    }

    public void setGoalsReceived(int goalsReceived) {
        this.goalsReceived = goalsReceived;
    }

    public void sumGoalsReceived(int goalSum) {
        this.goalsReceived += goalSum;
    }

    public int getPoints() {
        return this.getWins() * WIN_VALUE + this.getDraws() * DRAW_VALUE + this.getLosses() * LOSS_VALUE;
    }
}
