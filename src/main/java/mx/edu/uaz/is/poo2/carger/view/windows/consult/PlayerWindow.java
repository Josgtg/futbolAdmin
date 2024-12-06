package mx.edu.uaz.is.poo2.carger.view.windows.consult;

import java.util.List;

import mx.edu.uaz.is.poo2.carger.services.formatters.PlayerFormatter;
import mx.edu.uaz.is.poo2.carger.model.constants.ErrorKind;
import mx.edu.uaz.is.poo2.carger.model.constants.Messages;
import mx.edu.uaz.is.poo2.carger.model.constants.WindowMessages;
import mx.edu.uaz.is.poo2.carger.model.constants.columns.PlayerColumns;
import mx.edu.uaz.is.poo2.carger.model.entities.Player;

public class PlayerWindow extends ConsultWindow<Player> {
    private List<Player> players;
    private final PlayerFormatter formatter;

    public PlayerWindow() {
        super("Player");
        this.formatter = new PlayerFormatter();
    }

    @Override
    public void start() {
        this.controller.setPlayerWindowPlayers();
        this.start(this.players.size(), false);
    }

    public void start(int amount, boolean showId) {
        this.controller.setPlayerWindowPlayers();
        boolean conntinue = true;
        while (conntinue) {
            if (amount > this.players.size())
                amount = this.players.size();
            this.println(WindowMessages.windowTitle(this.TITLE));
            this.showPlayers(amount, showId);
            conntinue = this.askOptions();
        }
    }

    public void showPlayers(int amount, boolean showId) {
        if (this.players.subList(0, amount).isEmpty()) {
            this.println(Messages.NO_ELEMENTS);
            return;
        }
        this.println(WindowMessages.PLAYER_COLUMN_SORT_OPTIONS);
        this.formatter.reviewPlayers(this.players.subList(0, amount));
        this.println(this.formatter.format(this.players.subList(0, amount), showId));
    }

    @Override
    public void start(Player player) {
        // Muestra un jugador particular (No ocurre nunca pero es para la interfaz)
        boolean conntinue = true;
        while (conntinue) {
            this.println(WindowMessages.windowTitle(this.TITLE));
            this.println(this.formatter.format(player));
            this.println("");
            break;
        }
    }

    private boolean askOptions() {
        String optionStr = this.readString(WindowMessages.RETURN_WINDOW, 0, 10);
        if (optionStr.equals("0"))
            return false;
        
        optionStr += "     ";

        switch (optionStr.charAt(0)) {
            case 'n' -> this.controller.setPlayersBy(PlayerColumns.NAME, optionStr.charAt(1) == '-');
            case 'e' -> this.controller.setPlayersBy(PlayerColumns.AGE, optionStr.charAt(1) == '-');
            case 'g' -> this.controller.setPlayersBy(PlayerColumns.GOALS, optionStr.charAt(1) == '-');
            case 'a' -> this.controller.setPlayersBy(PlayerColumns.ASSISTS, optionStr.charAt(1) == '-');
            case 't' -> {
                switch (optionStr.charAt(1)) {
                    case 'a' -> this.controller.setPlayersBy(PlayerColumns.YELLOW_CARDS, optionStr.charAt(2) == '-');
                    case 'r' -> this.controller.setPlayersBy(PlayerColumns.RED_CARDS, optionStr.charAt(2) == '-');
                }
            }
            default -> this.printerr(ErrorKind.INPUT_ERROR, Messages.INVALID_INPUT);
        }
        return true;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}