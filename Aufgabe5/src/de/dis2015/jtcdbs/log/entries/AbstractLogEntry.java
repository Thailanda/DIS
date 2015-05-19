package de.dis2015.jtcdbs.log.entries;

import de.dis2015.jtcdbs.LogEntry;

abstract class AbstractLogEntry implements LogEntry {

    private int lsn;

    @Override
    public int getLSN() {
        return lsn;
    }

    @Override
    public void setLSN(int lsn) {
        this.lsn = lsn;
    }
}
