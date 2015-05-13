package de.dis2015.jtcdbs.clients;

import de.dis2015.jtcdbs.Transaction;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-05-13
 */
public class Dimitri extends AbstractClient {

    public Dimitri() {
        super(4);
    }

    @Override
    public void takeActions() {
        Transaction tx = this.createTransaction();
        tx.write(randomPageId(), "Da!");
        randomSleep();
        tx.write(randomPageId(), "Sto!");
        randomSleep();

        tx.commit();
    }
}
