package mx.edu.uaz.is.poo2.carger.view.windows.consult;

import java.util.List;

import mx.edu.uaz.is.poo2.carger.model.constants.CRUDTable;
import mx.edu.uaz.is.poo2.carger.model.constants.Messages;
import mx.edu.uaz.is.poo2.carger.model.constants.WindowMessages;
import mx.edu.uaz.is.poo2.carger.model.entities.Player;

public class PlayerWindow extends ConsultWindow<Player> {
    private List<Player> players;

    public PlayerWindow() {
        super("Player");
    }

    @Override
    public void start() {
        // Muestra todos los jugadores
        this.controller.setPlayerWindowPlayers();
        boolean conntinue = true;
        while (conntinue) {
            this.println(WindowMessages.windowTitle(this.TITLE));
            if (this.players.isEmpty()) {
                this.println(Messages.NO_ELEMENTS);
                return;
            }
            this.println(this.listAsNumeratedStr(this.players));
            conntinue = this.askOptions();
        }
    }

    @Override
    public void start(Player player) {
        // Muestra un jugador particular
        boolean conntinue = true;
        while (conntinue) {
            this.println(WindowMessages.windowTitle(this.TITLE));
            this.println(player);
            this.println("");
            conntinue = this.askOptions(player);
        }
    }

    private boolean askOptions() {
        int option = this.readInt(
            WindowMessages.consultOrReturn(CRUDTable.PLAYER.getName()),
            0, this.players.size()
        );
        if (option == 0)
            return false;
        this.controller.startPlayerWindow(this.players.get(--option));
        return true;
    }

    private boolean askOptions(@SuppressWarnings("unused") Player player) {
        this.readInt(WindowMessages.RETURN_WINDOW, 0, 0);
        return false;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}