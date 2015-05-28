package de.dis2015.jtcdbs.managers;

import java.io.File;
import java.io.IOException;

import de.dis2015.jtcdbs.Constants;
import de.dis2015.jtcdbs.LogEntry;
import de.dis2015.jtcdbs.LogManager;
import java.io.Reader;
import java.io.Writer;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-05-19
 */
public class LogManagerImpl implements LogManager {

    @Override
    public void writeLogEntry(Writer writer, LogEntry logEntry) throws IOException {
        // Write LSN.
        String lsn = logEntry.getLSNToStore();
        writer.write(lsn + Constants.getSeparator());

        // Write log entry class name.
        /** String className = logEntry.getClass().getName();
        writer.write(className.length());
        writer.write(className.toCharArray());
         */

        // Write rest of log entry content.
        logEntry.write(writer);

        // New line to end it.
        //writer.write("\n");
        writer.write(System.getProperty("line.separator"));
    }

    @Override
    public LogEntry readLogEntry(Reader reader) throws IOException {
        // Read LSN.
        int lsn = reader.read();

        // Read entry class name.
        char[] classNameArray = new char[reader.read()];
        reader.read(classNameArray);
        String className = new String(classNameArray);

        // Create log entry and read rest of content.
        LogEntry logEntry = createLogEntry(lsn, className);
        logEntry.read(reader);

        // Skip new line.
        reader.skip(1);

        return logEntry;
    }

    @Override
    public boolean isRecoveryNeeded() {
        // TODO
        boolean recovery = true;
        String logPath = Constants.getLogPath()+Constants.getLogName()+Constants.getFileExtensionLogEntry();
        File logFile = new File(logPath);
        if(!logFile.exists())
        {
            recovery = false;
        }
        return recovery;
    }

    @Override
    public void doRecovery() {
        // TODO
    }

    /**
     * Creates a log entry for a LSN and a class name.
     */
    @Override
    public LogEntry createLogEntry(int lsn, String className) {
        try {
            Class<?> clazz = Class.forName(className);
            LogEntry entry = (LogEntry) clazz.newInstance();
            entry.setLSN(lsn);
            return entry;
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
