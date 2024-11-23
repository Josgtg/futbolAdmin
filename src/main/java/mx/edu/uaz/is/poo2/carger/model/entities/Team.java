package mx.edu.uaz.is.poo2.carger.model.entities;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.*;
import static jakarta.persistence.CascadeType.*;

@Entity
public class Team implements IEntity, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
    @OneToMany(mappedBy="team", cascade=ALL)
    private List<Player> players;

    public Team() {}
    
    public Team(String name){
        this.name = name;
        this.players = new ArrayList<>();
    }

    public Team(String name, List<Player> players){
        this(name);
        this.players = players;
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
