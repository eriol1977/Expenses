/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fb.expenses.service;

import com.fb.expenses.entity.IEntity;
import java.io.Closeable;
import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.hibernate.engine.spi.SessionImplementor;

/**
 * Generic DAO
 * 
 * @author f.bertolino
 * @param <T> generic IEntity class
 */
public abstract class AbstractDAO<T extends IEntity> implements Closeable {

    protected final Class<T> entityClass;
    protected final EntityManagerFactory emfactory;
    protected final EntityManager em;
    protected final String queryFindAll;
    protected final String queryDeleteAll;

    public AbstractDAO(Class<T> entityClass, String queryFindAll, String queryDeleteAll) {
        this.entityClass = entityClass;
        this.queryFindAll = queryFindAll;
        this.queryDeleteAll = queryDeleteAll;
        this.emfactory = Persistence.createEntityManagerFactory("expenses_PU");
        this.em = emfactory.createEntityManager();
    }

    public Long persist(T obj) {
        em.getTransaction().begin();
        em.persist(obj);
        em.getTransaction().commit();
        return obj.getId();
    }

    public List<Long> persistAll(List<T> objs) {
        List<Long> ids = new ArrayList<>();
        objs.forEach((obj) -> {
            ids.add(persist(obj));
        });
        return ids;
    }

    public T find(Long id) {
        return em.find(this.entityClass, id);
    }

    public void update(T obj) {
        em.getTransaction().begin();
        T persisted = find(obj.getId());
        updateFields(persisted, obj);
        em.getTransaction().commit();
    }

    /**
     * Updates the entity specific fields in each specific DAO
     * 
     * @param persisted
     * @param updated 
     */
    protected abstract void updateFields(T persisted, T updated);

    public void delete(T obj) {
        em.getTransaction().begin();
        T persisted = find(obj.getId());
        em.remove(persisted);
        em.getTransaction().commit();
    }

    public List<T> findAll() {
        Query query = em.createNamedQuery(this.queryFindAll);
        return query.getResultList();
    }

    public void deleteAll() {
        em.getTransaction().begin();
        Query query = em.createNamedQuery(this.queryDeleteAll);
        query.executeUpdate();
        em.getTransaction().commit();
    }

    public DatabaseMetaData getDBMetaData() throws SQLException {
        SessionImplementor sessionImp = (SessionImplementor) em.getDelegate();
        return sessionImp.connection().getMetaData();
    }

    @Override
    public void close() throws IOException {
        em.close();
        emfactory.close();
    }

}
