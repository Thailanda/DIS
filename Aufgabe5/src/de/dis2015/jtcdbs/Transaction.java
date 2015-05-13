package de.dis2015.jtcdbs;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-05-13
 */
public class Transaction {

    private final int id;
    private final PersistenceManager persistenceManager;

    public Transaction(PersistenceManager persistenceManager) {
        this.id = persistenceManager.beginTransaction();
        this.persistenceManager = persistenceManager;
    }

    /**
     * Commits the transaction.
     */
    public void commit() {
        persistenceManager.commit(id);
    }

    /**
     * Writes the given data with the given page ID. If the given page already
     * exists, its content is replaced completely by the given data.
     *
     * @param pageId page to write
     * @param data data to write
     */
    public void write(int pageId, String data) {
        persistenceManager.write(id, pageId, data);
    }
}
