package de.dis2011.data.dao;

import de.dis2011.data.EstateAgent;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-22
 */
final public class EstateAgentDao extends Dao<EstateAgent> {

    public EstateAgentDao() {
        super(EstateAgent.class);
    }

    public EstateAgent verifyLogin(String login, String password) {
        EstateAgent estateAgent = null;
        Session session = openSession();
        try {
            Transaction tx = session.beginTransaction();

            Query query = session.createQuery("from EstateAgent where login=:login AND password=:password");
            query.setString("login", login);
            query.setString("password", password);
            estateAgent = (EstateAgent) query.uniqueResult();

            tx.commit();
        } catch (HibernateException e) {
            handleException(e);
        }

        return estateAgent;
    }
}
