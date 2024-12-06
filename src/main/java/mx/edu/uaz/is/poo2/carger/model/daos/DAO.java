package mx.edu.uaz.is.poo2.carger.model.daos;

import java.util.ArrayList;

import mx.edu.uaz.is.poo2.carger.model.constants.Errors;
import mx.edu.uaz.is.poo2.carger.model.constants.ErrorKind;
import mx.edu.uaz.is.poo2.carger.model.entities.IEntity;
import mx.edu.uaz.is.poo2.carger.view.Logger;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.Query;

public class DAO<T extends IEntity> {
    protected final Class<T> entityClass;
    protected final EntityManager entityManager;
    protected final Logger log;

    public DAO(Class<T> entityClass, EntityManager entityManager) {
        this.entityClass = entityClass;
        this.entityManager = entityManager;
        this.log = new Logger(this.getClass().getSimpleName());
    }

    public Optional<T> find(T entity) {
        return this.executeTransactionWithResult( () -> this.entityManager.find(entityClass, entity.getId()) );
    }

    public List<T> findAll(){
        CriteriaQuery<T> criteriaQuery = this.entityManager.getCriteriaBuilder().createQuery(entityClass);
        criteriaQuery.select(criteriaQuery.from(entityClass));
        return this.entityManager.createQuery(criteriaQuery).getResultList();
    }

    public Optional<T> findByID(Long id){
        return this.executeTransactionWithResult( () -> this.entityManager.find(entityClass, id) );
    }

    public void delete(T entidad){
        this.executeTransaction( () -> this.entityManager.remove(entidad) );
    }

    public boolean exists(Long id){
        return this.executeTransactionWithResult( () -> this.entityManager.find(entityClass, id) ) != null;
    }

    public Optional<T> save(T entidad){
        return this.executeTransactionWithResult(() -> {
            this.entityManager.persist(entidad);
            return entidad;
        });
    }

    public Optional<T> update(T entidad){
        return this.executeTransactionWithResult(() -> entityManager.merge(entidad));
    }

    private Optional<T> executeTransactionWithResult(DBExecute<T> comando){
        try{
            this.entityManager.getTransaction().begin();
            T salida = comando.executeDBCommand();
            this.entityManager.getTransaction().commit();
            return Optional.ofNullable(salida);
        } catch (Exception e) {
            log.warnf(ErrorKind.QUERY_NOT_EXECUTED, Errors.QUERY_NOT_EXECUTED, e);
            this.entityManager.getTransaction().rollback();
            return null;
        }
    }

    private boolean executeTransaction(DBJustExecute comando){
        try{
            this.entityManager.getTransaction().begin();
            comando.executeDBCommand();
            this.entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            log.warnf(ErrorKind.QUERY_NOT_EXECUTED, Errors.QUERY_NOT_EXECUTED, e);
            this.entityManager.getTransaction().rollback();
            return false;
        }
    }

    public List<T> multipleResultQuery(String queryStr, Object... parameters) {
        Query query = this.entityManager.createNativeQuery(queryStr);
        for (int i = 1; i <= parameters.length; i++)
            query.setParameter(i, parameters[i - 1]);
        List<Object[]> results =  query.getResultList();
        List<T> entities = new ArrayList<>();
        Optional<T> entity;
        for (Object[] entityData : results) {
            entity = this.findByID(Integer.toUnsignedLong((int) entityData[0]));
            if (entity.isPresent())
                entities.add(entity.get());
        }
        return entities;
    } 

    public Optional<T> resultQuery(String queryStr, Object... parameters) {
        Query query = this.entityManager.createNativeQuery(queryStr);
        for (int i = 1; i <= parameters.length; i++)
            query.setParameter(i, parameters[i - 1]);
        List<Object[]> results = query.getResultList();
        if (results.isEmpty())
            return Optional.empty();
        Object[] entityData = results.get(0);
        return this.findByID(Integer.toUnsignedLong((int) entityData[0]));
    } 
}

interface DBJustExecute {
    void executeDBCommand();
}

interface DBExecute<T> {
    T executeDBCommand();
}
