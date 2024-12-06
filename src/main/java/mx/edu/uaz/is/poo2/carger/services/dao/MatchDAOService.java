package mx.edu.uaz.is.poo2.carger.services.dao;



import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import mx.edu.uaz.is.poo2.carger.model.daos.MatchDAO;
import mx.edu.uaz.is.poo2.carger.model.entities.Match;

public class MatchDAOService extends DAOService<Match> {
    public MatchDAOService(EntityManager em) {
        this.dao = new MatchDAO(em);
    }

    public List<Match> findNotPlayed(){
        List<Match> matches = dao.findAll();
        List<Match> matchesNotPlayed = new ArrayList<>();
        for (var match : matches){
            if (!match.isPlayed()) {
                matchesNotPlayed.add(match);
            }
        }
        return matchesNotPlayed;
        
    }

    public List<Match> findPlayed(){
        List<Match> matches = dao.findAll();
        List<Match> matchesPlayed = new ArrayList<>();
        for (var match : matches){
            if (match.isPlayed()) {
                matchesPlayed.add(match);
            }
        }
        return matchesPlayed;   
    }

    public void deleteAll(){
        List<Match> matches = dao.findAll();
        for (Match match : matches) {
            dao.delete(match);
        }
    }
}