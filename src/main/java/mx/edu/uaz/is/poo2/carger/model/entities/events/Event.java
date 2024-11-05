package mx.edu.uaz.is.poo2.carger.model.entities.events;

import java.io.Serializable;
import java.util.Optional;

import jakarta.persistence.*;
import mx.edu.uaz.is.poo2.carger.model.entities.IEntity;
import mx.edu.uaz.is.poo2.carger.model.entities.Player;

@Entity
public class Event implements Serializable, IEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @OneToOne
    private EventKind eventKind;
    @Column
    private Player firstPlayer;
    @Column
    private Optional<Player> secondPlayer;
    @Column
    private int minute;

    public Event() {}

    public Event(EventKind eventKind, Player firstPlayer, int minute) {
        this.eventKind = eventKind;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = Optional.empty();
        this.minute = minute;
    }

    public Event(EventKind eventKind, Player firstPlayer, Player secondPlayer, int minute) {
        this.eventKind = eventKind;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = Optional.of(secondPlayer);
        this.minute = minute;
    }

    
    // Getters y setters

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventKind getEventKind() {
        return eventKind;
    }

    public void setEventKind(EventKind eventKind) {
        this.eventKind = eventKind;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public Optional<Player> getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(Optional<Player> secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
