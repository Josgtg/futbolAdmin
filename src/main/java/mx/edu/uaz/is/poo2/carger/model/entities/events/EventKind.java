package mx.edu.uaz.is.poo2.carger.model.entities.events;

import jakarta.persistence.*;
import mx.edu.uaz.is.poo2.carger.model.entities.IEntity;

@Entity
public enum EventKind implements IEntity {
    Goal,
    Substitution,
    YellowCard,
    RedCard,
    Injury;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Override
    public Long getId() {
        return this.id;
    }
}
