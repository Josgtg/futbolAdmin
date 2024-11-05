package mx.edu.uaz.is.poo2.carger.model.entities;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.*;

@Entity
public class Team implements IEntity, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
    @Column
    @OneToMany
    private List<Player> players;

    public Team() {}
    
    public Team(String name){
        this.name = name;
        this.players = new ArrayList<>();
    }
    
    @Override
    public Long getId(){
        return this.id;
    }
    
    public void setId(Long id){
        this.id = id;
    }

    public String getname() {
        return this.name;
    }

    public void setname(String name){
        this.name = name;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public void removePlayer(Player player) {
        this.players.remove(player);
    }
}
