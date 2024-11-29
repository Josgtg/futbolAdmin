package mx.edu.uaz.is.poo2.carger.services.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import mx.edu.uaz.is.poo2.carger.model.constants.CRUDTable;
import mx.edu.uaz.is.poo2.carger.model.entities.IEntity;

public class DAOServiceFactory {
    private static final EntityManager em =
    Persistence.createEntityManagerFactory("UPFutbolAdmin").createEntityManager();
    
    private static TeamDAOService teamDAO;
    private static PlayerDAOService playerDAO;
    private static MatchDAOService matchDAO;
    private static EventDAOService eventDAO;

    @SuppressWarnings("unchecked")
    public static <T extends IEntity> DAOService<T> getDAOService(CRUDTable table) {
        return switch (table) {
            case MATCH -> (DAOService<T>) getMatchDAOService();
            case PLAYER -> (DAOService<T>) getPlayerDAOService();
            case TEAM -> (DAOService<T>) getTeamDAOService();
            case EVENT -> (DAOService<T>) getEventDAOService();
        };
    }

    public static MatchDAOService getMatchDAOService() {
        if (matchDAO == null)
            matchDAO = new MatchDAOService(em);
        return matchDAO;
    }

    public static PlayerDAOService getPlayerDAOService() {
        if (playerDAO == null)
            playerDAO = new PlayerDAOService(em);
        return playerDAO;
    }

    public static TeamDAOService getTeamDAOService() {
        if (teamDAO == null)
            teamDAO = new TeamDAOService(em);
        return teamDAO;
    }

    public static EventDAOService getEventDAOService() {
        if (eventDAO == null)
            eventDAO = new EventDAOService(em);
        return eventDAO;
    }
}