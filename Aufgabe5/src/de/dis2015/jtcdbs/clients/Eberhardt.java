package de.dis2015.jtcdbs.clients;

import de.dis2015.jtcdbs.Transaction;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-05-13
 */
public class Eberhardt extends AbstractClient {

    public Eberhardt() {
        super(5);
    }

    @Override
    public void takeActions() {
        Transaction tx = this.createTransaction();
        tx.write(randomPageId(), "Isch hab luscht auf a Waiswuascht!");
        randomSleep();

        tx.commit();
    }
}
