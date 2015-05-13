package de.dis2015.jtcdbs.clients;

import de.dis2015.jtcdbs.Transaction;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-05-13
 */
public class Caesar extends AbstractClient {

    public Caesar() {
        super(3);
    }

    @Override
    public void takeActions() {
        Transaction tx = this.createTransaction();
        tx.write(randomPageId(), "Veni!");
        randomSleep();
        tx.write(randomPageId(), "Vidi!");
        randomSleep();
        tx.write(randomPageId(), "Vici!");
        randomSleep();

        tx.commit();
    }
}
