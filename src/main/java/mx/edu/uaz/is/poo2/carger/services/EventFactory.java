package mx.edu.uaz.is.poo2.carger.services;

import java.util.Optional;

import mx.edu.uaz.is.poo2.carger.model.entities.Player;
import mx.edu.uaz.is.poo2.carger.model.entities.events.Event;
import mx.edu.uaz.is.poo2.carger.model.entities.events.EventKind;

public class EventFactory {
    public static Event getEventGoal(Player scorer, Player assister, int minute){
        Event goal;
        Optional<Player> opAssister = Optional.ofNullable(assister);
        if (opAssister.isPresent()){
            goal = new Event(EventKind.GOAL, scorer, opAssister.get(), minute);
        }else{
            goal = new Event(EventKind.GOAL, scorer, minute);
        }
        return goal;
    }
    public static Event getEventSubstitution(Player playerIncoming, Player playerReplaced, int minute){
        Event substitution = new Event(EventKind.SUB, playerIncoming, playerReplaced, minute);
        return substitution;
    }
    public static Event getEventYellowCard(Player cautionedPlayer, int minute){
        Event yellowCard = new Event(EventKind.YELLOW_CARD, cautionedPlayer, minute);
        return yellowCard;
    }
    public static Event getEventRedCard(Player sentOffPlayer, int minute){
        Event redCard = new Event(EventKind.RED_CARD, sentOffPlayer, minute);
        return redCard;
    }
    public static Event getEventInjury(Player injuriedPlayer, int minute){
        Event injury = new Event(EventKind.INJURY, injuriedPlayer, minute);
        return injury;
    }
}
