package de.dis2015.jtcdbs;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-05-19
 */
public interface LogManager {

    /**
     * Write a log entry with following information:
     * lsn;class;pageId;transactionId;data followed by a new line
     * (also using the separator from the Constants class)
     *
     * example: 1;/de.dis2015.jtcdbs.log.entries.PageWriteLogEntry;999;-564834653;BOT
     */
    void writeLogEntry(Writer writer, LogEntry logEntry) throws IOException;

    LogEntry readLogEntry(Reader reader) throws IOException;

    //boolean isRecoveryNeeded();

    //void doRecovery();
    
    LogEntry createLogEntry(int lsn, String className);
}
