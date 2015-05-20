package de.dis2015.jtcdbs;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-05-19
 */
public interface LogManager {

    void writeLogEntry(Writer writer, LogEntry logEntry) throws IOException;

    LogEntry readLogEntry(Reader reader) throws IOException;

    boolean isRecoveryNeeded();

    void doRecovery();
    
    LogEntry createLogEntry(int lsn, String className);
}
