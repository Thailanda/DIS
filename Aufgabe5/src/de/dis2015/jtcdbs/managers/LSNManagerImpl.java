package de.dis2015.jtcdbs.managers;

import de.dis2015.jtcdbs.LSNManager;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-05-19
 */
public class LSNManagerImpl implements LSNManager {

    private AtomicInteger lsn;

    @Override
    public int nextLSN() {
        return lsn.incrementAndGet();
    }
}
