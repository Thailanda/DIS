package de.dis2015.jtcdbs.clients;

import de.dis2015.jtcdbs.Transaction;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-05-13
 */
public class Bob extends AbstractClient {

    public Bob() {
        super(2);
    }

    @Override
    public void takeActions() {
        Transaction tx = this.createTransaction();
        tx.write(randomPageId(), "My name is Bob!");
        randomSleep();
        tx.write(randomPageId(), "I work in security");
        randomSleep();
        tx.write(randomPageId(), "I like it!");
        randomSleep();

        tx.commit();
    }
}
