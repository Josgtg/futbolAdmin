package mx.edu.uaz.is.poo2.carger.controller.crud;

import java.util.List;
import java.util.Optional;

import mx.edu.uaz.is.poo2.carger.controller.*;
import mx.edu.uaz.is.poo2.carger.model.constants.CRUDTable;
import mx.edu.uaz.is.poo2.carger.services.dao.*;
import mx.edu.uaz.is.poo2.carger.model.entities.*;
import mx.edu.uaz.is.poo2.carger.view.windows.consult.*;
import mx.edu.uaz.is.poo2.carger.view.windows.crud.PlayerCRUDWindow;

public class CRUDController<T extends IEntity> extends Controller {

    private TeamWindow teamWindow;
    private MatchWindow matchWindow;
    private PlayerWindow playerWindow;

    private PlayerCRUDWindow playerCRUDWindow;

    private final DAOService<T> dao;

    public CRUDController(DAOService<T> dao) {
        super();
        this.dao = dao;
    }

    public Optional<T> save(T entity) {
        return this.dao.save(entity);
    }

    public Optional<T> update(T entity) {
        return this.dao.update(entity);
    }

    public boolean delete(T entity) {
        dao.delete(entity);
        return true;
    }

    public boolean deleteById(Long id) {
        Optional<T> toDelete = this.dao.findById(id);
        if (toDelete.isEmpty())
            return false;
        dao.delete(toDelete.get());
        return true;
    }

    public Optional<T> findByID(Long id) {
        return this.dao.findById(id);
    }

    public List<T> findAll() {
        return this.dao.findAll();
    }

    @SuppressWarnings("unchecked")
    public <E extends IEntity> Optional<E> getEntity(Long id, CRUDTable table) {
        return (Optional<E>) DAOServiceFactory.getDAOService(table).findById(id);
    }
    

    public void startMatchWindow() {
        this.matchWindow.start();
    }

    public void startMatchWindow(Match match) {
        this.matchWindow.start(match);
    }

    public void setMatchWindow(MatchWindow window) {
        this.matchWindow = window;
    }


    public void startPlayerWindow() {
        this.playerWindow.start();
    }

    public void startPlayerWindow(int amount, boolean showId) {
        this.playerWindow.start(amount, showId);
    }

    public void startPlayerWindow(Player player) {
        this.playerWindow.start(player);
    }

    public void setPlayerWindow(PlayerWindow window) {
        this.playerWindow = window;
    }


    public void startTeamWindow() {
        this.teamWindow.start();
    }

    public void startTeamWindow(boolean showId) {
        this.teamWindow.start(Integer.MAX_VALUE, showId);
    }

    public void startTeamWindow(Team team) {
        this.teamWindow.start(team);
    }

    public void setTeamWindow(TeamWindow window) {
        this.teamWindow = window;
    }


    public Player readPlayerFromUser() {
        return this.playerCRUDWindow.readEntity();
    }

    public Player readPlayerFromUser(Team team) {
        return this.playerCRUDWindow.readEntity(team);
    }

    public void setPlayerCRUDWindow(PlayerCRUDWindow window) {
        this.playerCRUDWindow = window;
    }

    public Optional<Player> savePlayer(Player player) {
        Optional<Team> team =  DAOServiceFactory.getTeamDAOService().find(player.getTeam());
        if (team.isPresent()) {
            team.get().addPlayer(player);
            if (DAOServiceFactory.getTeamDAOService().save(team.get()).isPresent())
                return Optional.of(player);
        } else {
            if (DAOServiceFactory.getPlayerDAOService().save(player).isPresent())
                return Optional.of(player);
        }
        return Optional.empty();
    }
}