package de.dis2015.jtcdbs.clients;

import com.google.inject.Inject;
import de.dis2015.jtcdbs.Client;
import de.dis2015.jtcdbs.PersistenceManager;
import de.dis2015.jtcdbs.Transaction;
import java.util.Random;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-05-13
 */
public abstract class AbstractClient extends Thread implements Client {

    @Inject PersistenceManager persistenceManager;

    private boolean isShutdown = false;

    private final int[] namespace;

    /**
     * @param clientId id of this client.
     */
    public AbstractClient(int clientId) {
        namespace = new int[10];
        createNamespace(clientId);
    }

    /**
     * Shuts down the client.
     */
    public void shutdown() {
        this.isShutdown = true;
    }

    @Override
    public final void run() {
        while (!this.isShutdown) {
            takeActions();
        }
    }

    @Override
    public int[] getNamespace() {
        return namespace;
    }

    /**
     * Creates a new transaction.
     *
     * @return tx instance
     */
    protected Transaction createTransaction() {
        return new Transaction(persistenceManager);
    }

    /**
     * Lets the client sleep for a random while.
     */
    protected void randomSleep() {
        Random random = new Random();
        try {
            Thread.sleep(150 + random.nextInt(750));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return a random page ID where this client has write access to.
     */
    protected int randomPageId() {
        Random random = new Random();
        return namespace[random.nextInt(namespace.length)];
    }

    /**
     * Creates the namespace for the given client ID
     * by using some fibonacci magic :)
     *
     * @param clientId client ID for the calculation
     */
    private void createNamespace(int clientId) {
        for (int i = 0; i < namespace.length; ++i) {
            namespace[i] = (clientId + fibonacci(i)) % 5 + i * 5;
        }
    }

    private int fibonacci(int i) {
        if (i < 2) {
            return 1;
        }

        return fibonacci(i - 2) + fibonacci(i - 1);
    }
}
