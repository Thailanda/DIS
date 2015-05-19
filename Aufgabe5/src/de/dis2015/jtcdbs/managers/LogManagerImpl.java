package de.dis2015.jtcdbs.managers;

import de.dis2015.jtcdbs.LogEntry;
import de.dis2015.jtcdbs.LogManager;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-05-19
 */
public class LogManagerImpl implements LogManager {
    private static String LOG_FILE_NAME = "jtc.log";

    @Override
    public void writeLogEntry(BufferedWriter writer, LogEntry logEntry) throws IOException {
        // Write LSN.
        writer.write(logEntry.getLSN());
        writer.write(";");

        // Write log entry class name.
        writer.write(logEntry.getClass().getName());
        writer.write(";");

        // Write rest of log entry content.
        logEntry.write(writer);

        // New line to end it.
        writer.newLine();
    }

    @Override
    public LogEntry readLogEntry(BufferedReader reader) throws IOException {
        // Resolve line for entry.
        String line = reader.readLine();

        // Read LSN.
        int lsnEnd = line.indexOf(";");
        String lsnStr = line.substring(0, lsnEnd);

        // Read entry class name.
        int classNameEnd = line.indexOf(";", lsnEnd + 1);
        String className = line.substring(lsnEnd + 1, classNameEnd);

        // Skip read information.
        reader.skip(classNameEnd + 1);

        // Create log entry and read rest of content.
        LogEntry logEntry = createLogEntry(Integer.parseInt(lsnStr), className);
        logEntry.read(reader);

        return logEntry;
    }

    @Override
    public boolean isRecoveryNeeded() {
        // TODO
        return true;
    }

    @Override
    public void doRecovery() {
        // TODO
    }

    /**
     * Creates a log entry for a LSN and a class name.
     */
    private LogEntry createLogEntry(int lsn, String className) {
        try {
            Class<?> clazz = Class.forName(className);
            LogEntry entry = (LogEntry) clazz.newInstance();
            entry.setLSN(lsn);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
