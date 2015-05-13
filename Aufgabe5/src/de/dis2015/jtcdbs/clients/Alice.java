package de.dis2015.jtcdbs.clients;

import de.dis2015.jtcdbs.Transaction;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-05-13
 */
public class Alice extends AbstractClient {

    public Alice() {
        super(1);
    }

    @Override
    public void takeActions() {
        Transaction tx = this.createTransaction();
        tx.write(randomPageId(), "hallo");
        randomSleep();
        tx.write(randomPageId(), "welt");
        randomSleep();
        tx.write(randomPageId(), "test");
        randomSleep();

        tx.commit();
    }
}
