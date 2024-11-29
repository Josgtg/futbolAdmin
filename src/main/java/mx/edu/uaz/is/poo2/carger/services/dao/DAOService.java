package mx.edu.uaz.is.poo2.carger.services.dao;

import java.util.Optional;
import java.util.List;

import mx.edu.uaz.is.poo2.carger.model.daos.DAO;
import mx.edu.uaz.is.poo2.carger.model.entities.IEntity;

public abstract class DAOService<E extends IEntity> {
    protected DAO<E> dao;

    public Optional<E> find(E entity) {
        if (entity == null)
            return Optional.empty();
        return this.dao.find(entity);
    }

    public Optional<E> findById(Long id) {
        return this.dao.findByID(id);   
    }

    public List<E> findAll() {
        return this.dao.findAll();
    }

    public Optional<E> save(E entity) {
        return this.dao.save(entity);
    }

    public Optional<E> update(E entity) {
        return this.dao.update(entity);
    }

    public void delete(E entity) {
        this.dao.delete(entity);
    }
} 