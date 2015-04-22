package de.dis2011.data.dao;

import de.dis2011.data.Entity;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-22
 */
public abstract class Dao<EntityType extends Entity> {

    final private SessionFactory factory;
    final protected Class<EntityType> prototype;

    public Dao(SessionFactory factory, Class<EntityType> prototype) {
        this.factory = factory;
        this.prototype = prototype;
    }

    /**
     * Finds all entities.
     */
    public List<EntityType> findAll() {
        List<EntityType> entities = null;
        Session session = openSession();
        try {
            Transaction tx = session.beginTransaction();

            Query query = session.createQuery("from " + prototype.getName());
            entities = (List<EntityType>) query.list();

            tx.commit();
        } catch (HibernateException | ClassCastException e) {
            handleException(e);
        } finally {
            session.close();
        }

        return entities;
    }

    protected Session openSession() {
        return factory.openSession();
    }

    /**
     * Finds an entity by its ID.
     *
     * @param id to look for.
     * @return null or instance
     */
    public EntityType find(Long id) {
        EntityType entity = null;
        Session session = openSession();
        try {
            Transaction tx = session.beginTransaction();

            entity = (EntityType) session.load(prototype, id);

            tx.commit();
        } catch (HibernateException | ClassCastException e) {
            handleException(e);
        } finally {
            session.close();
        }

        return entity;
    }

    /**
     * Inserts or updates an entity in the database.
     */
    public boolean save(EntityType entity) {
        boolean success = false;
        Session session = openSession();
        try {
            Transaction tx = session.beginTransaction();

            session.saveOrUpdate(entity);

            tx.commit();
            success = true;
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            session.close();
        }

        return success;
    }

    /**
     * Drops an entity from the database.
     */
    public boolean delete(EntityType entity) {
        boolean success = false;
        Session session = openSession();
        try {
            Transaction tx = session.beginTransaction();

            session.delete(entity);

            tx.commit();
            success = true;
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            session.close();
        }

        return success;
    }

    protected void handleException(RuntimeException e) {
        e.printStackTrace();
    }
}
