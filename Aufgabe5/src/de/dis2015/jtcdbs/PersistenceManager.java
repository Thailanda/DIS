package de.dis2015.jtcdbs;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-05-12
 */
public interface PersistenceManager {

    /**
     * Starts a new transaction. The persistence manager creates a
     * unique transaction ID and returns it to the client.
     *
     * @return unique transaction ID
     */
    int beginTransaction();

    /**
     * Commits the transaction specified by the given transaction ID.
     *
     * @param tx unique transaction ID
     */
    void commit(int tx);

    /**
     * Writes the given data with the given page ID on behalf of the
     * given transaction to the buffer. If the given page already
     * exists, its content is replaced completely by the given data.
     *
     * @param tx unique transaction ID
     * @param pageId page to write
     * @param data data to write
     */
    void write(int tx, int pageId, String data);
}
