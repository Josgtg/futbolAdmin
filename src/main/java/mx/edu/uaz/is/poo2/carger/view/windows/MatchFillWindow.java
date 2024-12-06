package mx.edu.uaz.is.poo2.carger.view.windows;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import mx.edu.uaz.is.poo2.carger.controller.MatchFillController;
import mx.edu.uaz.is.poo2.carger.model.constants.CRUDTable;
import mx.edu.uaz.is.poo2.carger.model.constants.ErrorKind;
import mx.edu.uaz.is.poo2.carger.model.constants.Messages;
import mx.edu.uaz.is.poo2.carger.model.constants.WindowMessages;
import mx.edu.uaz.is.poo2.carger.model.entities.Match;
import mx.edu.uaz.is.poo2.carger.model.entities.Player;
import mx.edu.uaz.is.poo2.carger.model.entities.Team;
import mx.edu.uaz.is.poo2.carger.model.entities.events.Event;
import mx.edu.uaz.is.poo2.carger.model.entities.events.EventKind;
import mx.edu.uaz.is.poo2.carger.services.EventFactory;
import mx.edu.uaz.is.poo2.carger.services.StatisticsGenerator;


public class MatchFillWindow extends Window<MatchFillController> {
    private List<Match> matches;
    private final EventKind[] eventKinds = EventKind.values() ;
    private Team homeTeam;
    private Team awayTeam;
    private boolean ShowMatchesPlayed;
    private List<Player> playersList;
    private final StatisticsGenerator statisticsGenerator;
    
    
        public MatchFillWindow(){
            super("Rellenar Partido");
            ShowMatchesPlayed = false;
            statisticsGenerator = StatisticsGenerator.getInstance();
        }
    
    
        @Override
        public void start() {
            this.controller.setMatchFillWindowMatches();
            if (this.matches.isEmpty()) {
                this.println(Messages.NO_ELEMENTS);
                return;
            }

            boolean conntinue = true;
            while (conntinue) {
                this.println(WindowMessages.windowTitle(this.TITLE));
                if (ShowMatchesPlayed){
                    this.println("Partidos YA jugados");
                    this.controller.setMatchFillWindowMatchesPlayed();
                    if (this.matches.isEmpty()) {
                        this.println(Messages.NO_MATCHES_PLAYED);
                    }
                }else{
                    this.println("Partidos NO Jugados");
                    this.controller.setMatchFillWindowMatchesNotPlayed();
                    if (this.matches.isEmpty()) {
                        this.println(Messages.NO_MATCHES_NOT_PLAYED);
                    }
                }
                this.println(this.basicListAsNumeratedStr(this.matches));
                conntinue = this.askOptions();
            }
        }
    
        public void start(Match match) {
            this.homeTeam = match.getHomeTeam();
            this.awayTeam = match.getAwayTeam();
            this.playersList = new ArrayList<>(homeTeam.getPlayers());
            this.playersList.addAll(awayTeam.getPlayers());

            this.println(WindowMessages.windowTitle(this.TITLE));
            this.println(match);
            this.println("");
            this.updateEntity(match);
        }
    
        private boolean askOptions() {
            int option = this.readInt(
                WindowMessages.selectToFillOrReturn(CRUDTable.MATCH.getName()),
                -1, this.matches.size()
            );
            if (option == 0){
                return false;
            }else if (option == -1){
                ShowMatchesPlayed = !ShowMatchesPlayed;
                return true;
            }

            this.controller.startMatchFillWindow(this.matches.get(--option));
            return true;
        }        

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    //Codigo de CRUDWindow y MatchCRUDWindow
    private void updateEntity(Match match) {
        match = this.readEntityToUpdate(match);
        if (this.controller.updateMatch(match).isPresent())
            this.println(Messages.ENTITY_UPDATED);
        else
            this.printerr(ErrorKind.QUERY_NOT_EXECUTED, Messages.CANT_UPDATE_ENTITY);
    }
    
    private boolean askToUpdate(String attributeName, Object object) {
        return this.askYesOrNo(WindowMessages.askToUpdate(attributeName, object));
    }

    public Timestamp readToUpdateDate(String attributeName, Timestamp date) {
        if (!this.askToUpdate(attributeName, date))
            return date;
        return this.readDate();
    }

    public int readToUpdateInt(String attributeName, int value, int lo, int hi) {
        if (!this.askToUpdate(attributeName, value))
            return value;
        return this.readInt("Nuevo valor: ", lo, hi);
    }

    public String readToUpdateString(String attributeName, String str, int lo, int hi) {
        if (!this.askToUpdate(attributeName, str))
            return str;
        return this.readString("Nuevo valor: ", lo, hi);
    }

    public Match readEntityToUpdate(Match match) {
        List<Event> events;
        if (match.isPlayed()){
            if (askYesOrNo("Quieres revertir el juego y dejarlo como no jugado? (s/n): ")){
                this.println(Messages.SEPARATOR);
                statisticsGenerator.revertMatch(match);
                match.setEvents(new ArrayList<>());
                match.setPlayed(false);
                return match;
            }
            events = match.getEvents();
        }else{
            events = new ArrayList<>();
        }

        Timestamp date = this.readToUpdateDate("Fecha", match.getDate());
        match.setDate(date);
        this.println(Messages.SEPARATOR);
        
        
        boolean continnue = true;
        while (continnue) {
            addEvent(events);
            modifyOrDeleteEvent(events, match);
            this.println("Los eventos quedaran asi");
            this.println(basicListAsNumeratedStr(events));
            continnue = !askYesOrNo(Messages.ASK_TO_CONTINUE);
            this.println(Messages.SEPARATOR);
        }

        match.setEvents(events);
        
        match.setPlayed(true);
        statisticsGenerator.updateLeague();
        return match;
    }
    private void modifyOrDeleteEvent(List<Event> events, Match match){
        this.println(Messages.SEPARATOR);
        if (events.isEmpty()) {
            this.println(Messages.NO_EVENTS_MODIFY);
            return;
        }
        for (int i = 0; i < events.size(); i++) {
            this.println(events.get(i).basicToString());
            this.println(listAsNumeratedStr(List.of("Modificar", "Eliminar")));
            int option = readInt(Messages.ASK_TO_MODIFY_DELETE_EVENT, 0, 2);
            this.println(Messages.SEPARATOR);
            switch (option) {
                case 0 -> {continue;}
                case 1 -> modifyEvent(events.get(i), match);
                case 2 -> {
                    statisticsGenerator.revertMatch(match);
                    events.remove(i);}
            }
        }
    }

    private void modifyEvent(Event event, Match match){
        this.statisticsGenerator.revertMatch(match);
        this.println(Messages.SEPARATOR);
        this.println(String.format("El tipo del evento es: %s", event.getEventKind()));
        if(askYesOrNo(Messages.ASK_TO_MODIFY)){
            this.println(listAsNumeratedStr(eventKinds));
            int option = this.readInt(Messages.SELECT_EVENT_KIND, 1, eventKinds.length);
            EventKind eventKind = eventKinds[option-1];
            event.setEventKind(eventKind);
        }

        this.println(Messages.SEPARATOR);
        this.println(String.format("El %s se dio en el minuto %d", event.getEventKind(), event.getMinute()));
        if(askYesOrNo(Messages.ASK_TO_MODIFY)){   
            event.setMinute(this.readInt(Messages.ASK_UPDATED_MINUTE, 1, 120));
        }

        this.println(Messages.SEPARATOR);
        this.println(String.format("El primer jugador del %s es %s", event.getEventKind(), event.getFirstPlayer().getName()));
        if(askYesOrNo(Messages.ASK_TO_MODIFY)){  
            
            this.println(basicListAsNumeratedStr((playersList)));
            int option = this.readInt("Selecciona el jugador actualizado", 1, playersList.size());
            event.setFirstPlayer(playersList.get(option-1));
        }

        this.println(Messages.SEPARATOR);
        if (event.getSecondPlayer().isPresent()){
            this.println(String.format("El segundo jugador del %s es %s", event.getEventKind(), event.getSecondPlayer().get().getName()));
        }else{
            this.println(String.format("No hay un segundo jugador para este %s", event.getEventKind()));
        }
        if(askYesOrNo(Messages.ASK_TO_MODIFY)){  
            this.println(Messages.SEPARATOR);
            this.println(basicListAsNumeratedStr((playersList)));
            int option = this.readInt("Selecciona el jugador actualizado o 0 para no colocar segundo jugador", 1, playersList.size());
            this.println(Messages.SEPARATOR);
            if (option == 0) {
                event.setSecondPlayer(Optional.ofNullable(null));
            }else{
                event.setSecondPlayer(Optional.of(playersList.get(option-1)));
            }

        }
    }
    private void addEvent(List<Event> events) {
        
        if (!askYesOrNo(Messages.ASK_TO_ADD_EVENTS)) {
            return;
        }
        while (true) {
            this.println(Messages.SEPARATOR);
            this.println(listAsNumeratedStr(eventKinds));
            int option;
            option = this.readInt(
                WindowMessages.selectEventOrReturn,
                0, this.eventKinds.length
            );
            if (option == 0){
                return;
            }
            EventKind evento = eventKinds[option-1];
            switch (evento) {
                case GOAL-> addGoal(events);
                case SUB -> addSubstitution(events);
                case YELLOW_CARD -> addYellowCard(events);
                case RED_CARD -> addRedCard(events);
                case INJURY -> addInjury(events);
                }
        }
    }

    public void addGoal(List<Event> events){
        Player scorer;
        Player assister = null;
        Event gol;
        int golMinute = readInt("En que minuto se realizo el gol: ",0,120);
        this.println(Messages.SEPARATOR);
        this.println(listAsNumeratedStr(List.of(homeTeam.getName(), awayTeam.getName())));
        int option = readInt("Que equipo marco el gol: ", 1, 2);
        this.println(Messages.SEPARATOR);
        //Marco el homeTeam
        if (option == 1){
            this.println(basicListAsNumeratedStr(homeTeam.getPlayers()));
            int scorerSelction = readInt("Que jugador marco el gol: ", 1, homeTeam.getPlayers().size());
            scorer = homeTeam.getPlayers().get(scorerSelction - 1);
            this.println(Messages.SEPARATOR);
            if (askYesOrNo("Alguien lo asistio? (s/n): ")){
                this.println(Messages.SEPARATOR);
                this.println(basicListAsNumeratedStr(homeTeam.getPlayers()));
                int assisterSelction = readInt("Que jugador lo asistio: ", 1, homeTeam.getPlayers().size());
                assister = homeTeam.getPlayers().get(assisterSelction-1);
            }
        //Marco el awayTeam
        }else {
            this.println(basicListAsNumeratedStr(awayTeam.getPlayers()));
            int scorerSelction = readInt("Que jugador marco el gol", 1, awayTeam.getPlayers().size());
            scorer = awayTeam.getPlayers().get(scorerSelction - 1);
            this.println(Messages.SEPARATOR);
            if (askYesOrNo("Alguien lo asistio? (s/n): ")){
                this.println(Messages.SEPARATOR);
                this.println(basicListAsNumeratedStr(awayTeam.getPlayers()));
                int assisterSelction = readInt("Que jugador lo asistio: ", 1, awayTeam.getPlayers().size());
                assister = awayTeam.getPlayers().get(assisterSelction - 1);
            }
        }
        gol = EventFactory.getEventGoal(scorer, assister, golMinute);
        events.add(gol);
        this.println(Messages.SEPARATOR);
    }

    public void addSubstitution(List<Event> events){
        Player playerIncoming;
        Player playerReplaced;
        Event subsitution;
        int subsitiutionMinute = readInt("En que minuto se realizo la sustitucion: ",0,120);
        this.println(Messages.SEPARATOR);
        this.println(listAsNumeratedStr(List.of(homeTeam.getName(), awayTeam.getName())));
        int option = readInt("En que equipo se realizo la sustitucion: ", 1, 2);
        this.println(Messages.SEPARATOR);
        //se realiza en el homeTeam
        if (option == 1){
            this.println(basicListAsNumeratedStr(homeTeam.getPlayers()));
            int replacedSelection = readInt("Que jugador se remplazo: ", 1, homeTeam.getPlayers().size());
            playerReplaced = homeTeam.getPlayers().get(replacedSelection - 1);
            this.println(Messages.SEPARATOR);
            this.println(basicListAsNumeratedStr(homeTeam.getPlayers()));
            int incomingSelection = readInt("Por quien fue reemplazado: ", 1, homeTeam.getPlayers().size());
            playerIncoming = homeTeam.getPlayers().get(incomingSelection - 1);
        //se realiza en el awayTeam
        }else {
            this.println(basicListAsNumeratedStr(awayTeam.getPlayers()));
            int replacedSelection = readInt("Que jugador se remplazo: ", 1, awayTeam.getPlayers().size());
            playerReplaced = awayTeam.getPlayers().get(replacedSelection - 1);
            this.println(Messages.SEPARATOR);
            this.println(basicListAsNumeratedStr(awayTeam.getPlayers()));
            int incomingSelection = readInt("Por quien fue reemplazado: ", 1, awayTeam.getPlayers().size());
            playerIncoming = awayTeam.getPlayers().get(incomingSelection - 1);
        }
        subsitution = EventFactory.getEventSubstitution(playerIncoming, playerReplaced, subsitiutionMinute);
        events.add(subsitution);
        this.println(Messages.SEPARATOR);
    }

    public void addYellowCard(List<Event> events){
        Player cautionedPlayer;
        Event yellowCard;
        int cardMinute = readInt("En que minuto ocurrio la amonestacion: ",0,120);
        this.println(Messages.SEPARATOR);
        this.println(listAsNumeratedStr(List.of(homeTeam.getName(), awayTeam.getName())));
        int option = readInt("De que equipo fue el jugador amonestado: ", 1, 2);
        this.println(Messages.SEPARATOR);
        //el jugador esta en el homeTeam
        if (option == 1){
            this.println(basicListAsNumeratedStr(homeTeam.getPlayers()));
            int cautionedSelection = readInt("Que jugador fue amonestado: ", 1, homeTeam.getPlayers().size());
            cautionedPlayer = homeTeam.getPlayers().get(cautionedSelection - 1);
        //el jugador esta en el awayTeam
        }else {
            this.println(basicListAsNumeratedStr(awayTeam.getPlayers()));
            int cautionedSelection = readInt("Que jugador fue amonestado: ", 1, awayTeam.getPlayers().size());
            cautionedPlayer = awayTeam.getPlayers().get(cautionedSelection - 1);
        }
        yellowCard = EventFactory.getEventYellowCard(cautionedPlayer, cardMinute);
        events.add(yellowCard);
        this.println(Messages.SEPARATOR);
    }

    public void addRedCard(List<Event> events){
        Player sentOffPlayer;
        Event redCard;
        int cardMinute = readInt("En que minuto ocurrio la expulsion: ",0,120);
        this.println(Messages.SEPARATOR);
        this.println(listAsNumeratedStr(List.of(homeTeam.getName(), awayTeam.getName())));
        int option = readInt("De que equipo fue el jugador expulsado: ", 1, 2);
        this.println(Messages.SEPARATOR);
        //el jugador esta en el homeTeam
        if (option == 1){
            this.println(basicListAsNumeratedStr(homeTeam.getPlayers()));
            int sentOffSelection = readInt("Que jugador fue expulsado: ", 1, homeTeam.getPlayers().size());
            sentOffPlayer = homeTeam.getPlayers().get(sentOffSelection - 1);
        //el jugador esta en el awayTeam
        }else {
            this.println(basicListAsNumeratedStr(awayTeam.getPlayers()));
            int sentOffSelection = readInt("Que jugador fue expulsado: ", 1, awayTeam.getPlayers().size());
            sentOffPlayer = awayTeam.getPlayers().get(sentOffSelection - 1);
        }
        redCard = EventFactory.getEventRedCard(sentOffPlayer, cardMinute);
        events.add(redCard);
        this.println(Messages.SEPARATOR);
    }
    
    public void addInjury (List<Event> events){
        Player injuredPlayer;
        Event injury;
        int injuryMinute = readInt("En que minuto ocurrio la lesion: ",0,120);
        this.println(Messages.SEPARATOR);
        this.println(listAsNumeratedStr(List.of(homeTeam.getName(), awayTeam.getName())));
        int option = readInt("De que equipo fue el jugador lesionado: ", 1, 2);
        this.println(Messages.SEPARATOR);
        //el jugador esta en el homeTeam
        if (option == 1){
            this.println(basicListAsNumeratedStr(homeTeam.getPlayers()));
            int injuriedSelction = readInt("Que jugador se lesiono: ", 1, homeTeam.getPlayers().size());
            injuredPlayer = homeTeam.getPlayers().get(injuriedSelction - 1);
        //el jugador esta en el awayTeam
        }else {
            this.println(basicListAsNumeratedStr(awayTeam.getPlayers()));
            int injuriedSelction = readInt("Que jugador se lesiono: ", 1, awayTeam.getPlayers().size());
            injuredPlayer = awayTeam.getPlayers().get(injuriedSelction - 1);
        }
        injury = EventFactory.getEventInjury(injuredPlayer, injuryMinute);
        events.add(injury);
        this.println(Messages.SEPARATOR);
    }
    
}
