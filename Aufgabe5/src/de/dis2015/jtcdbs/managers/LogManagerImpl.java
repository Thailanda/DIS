package de.dis2015.jtcdbs.managers;

import de.dis2015.jtcdbs.LogEntry;
import de.dis2015.jtcdbs.LogManager;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-05-19
 */
public class LogManagerImpl implements LogManager {
    private static String LOG_FILE_NAME = "jtc.log";

    protected void writeLogEntry(LogEntry logEntry) {
        try (FileWriter fw = new FileWriter(LOG_FILE_NAME)) {
            fw.write(logEntry.toContent());
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isRecoveryNeeded() {
        return true;
    }

    @Override
    public void doRecovery() {
        // TODO
    }
}
