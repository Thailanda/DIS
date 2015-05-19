package de.dis2015.jtcdbs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-05-19
 */
public interface LogManager {

    void writeLogEntry(BufferedWriter writer, LogEntry logEntry) throws IOException;

    LogEntry readLogEntry(BufferedReader reader) throws IOException;

    boolean isRecoveryNeeded();

    void doRecovery();
}
