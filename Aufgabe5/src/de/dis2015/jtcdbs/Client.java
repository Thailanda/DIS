package de.dis2015.jtcdbs;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-05-12
 */
public interface Client extends Runnable {

    /**
     * Lets the client take some actions.
     */
    void takeActions();

    /**
     * @return the namespace of this client.
     */
    int[] getNamespace();
}
