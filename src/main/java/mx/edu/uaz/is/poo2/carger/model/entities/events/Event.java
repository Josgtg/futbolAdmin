package mx.edu.uaz.is.poo2.carger.model.entities.events;

import java.io.Serializable;
import java.util.Optional;

import jakarta.persistence.*;

import mx.edu.uaz.is.poo2.carger.model.entities.IEntity;
import mx.edu.uaz.is.poo2.carger.model.entities.Player;

@Entity
public class Event implements IEntity, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    private EventKind eventKind;
    @OneToOne(fetch = FetchType.EAGER)
    private Player firstPlayer;
    @OneToOne(fetch = FetchType.EAGER)
    private Player secondPlayer;
    @Column
    private int minute;

    public Event() {}

    public Event(EventKind eventKind, Player firstPlayer, int minute) {
        this.eventKind = eventKind;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = null;
        this.minute = minute;
    }

    public Event(EventKind eventKind, Player firstPlayer, Player secondPlayer, int minute) {
        this.eventKind = eventKind;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
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
        if (this.secondPlayer == null) {
            return Optional.empty();
        }
        return Optional.of(this.secondPlayer);
    }

    public void setSecondPlayer(Optional<Player> secondPlayer) {
        if (secondPlayer.isEmpty()) {
            this.secondPlayer = null;
        }
        this.secondPlayer = secondPlayer.get();
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    @Override
    public String basicToString() {
        return String.format("%s en el minuto %d", this.eventKind, this.minute);
    }
@   Override
    public String toString(){
        return String.format("Event{ id: %d, kind: %s, minute: %d, firstPlayer: %s, secondPlayer: %d",this.id, this.eventKind, this.minute, this.firstPlayer, this.secondPlayer);
    }
}
