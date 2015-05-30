package de.dis2015.jtcdbs.managers;

import de.dis2015.jtcdbs.PersistenceManager;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-05-13
 */
public class DummyPersistenceManager implements PersistenceManager {
    @Override
    public int beginTransaction() {
        return 0;
    }

    @Override
    public void commit(int tx) {

    }

    @Override
    public void write(int tx, int pageId, String data) {

    }

    @Override
    public void redoWrite(int pageId, int lsn, String data) {

    }
}
