package de.dis2015.jtcdbs.managers;

import de.dis2015.jtcdbs.Constants;
import de.dis2015.jtcdbs.LSNManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-05-19
 */
public class LSNManagerImpl implements LSNManager {

    private final AtomicInteger lsn;

    public LSNManagerImpl() {

        String lastLogPath = Constants.getLogPath() + Constants.getLogName() + Constants.getFileExtensionLogEntry();
        File lastLog = new File(lastLogPath);

        if(lastLog.exists())
        {
            lsn = getLastLsn(lastLog);
        }
        else {
            this.lsn = new AtomicInteger();
        }

    }

    @Override
    public int nextLSN() {
        return lsn.incrementAndGet();
    }

    /**
     * Reads the value of the last lsn in the logFile that was used before the system crashed
     */
    private AtomicInteger getLastLsn(File logFile) {

        String lastEntry = new String();

        try {
            BufferedReader rdr = new BufferedReader(new FileReader(logFile));

            String newEntry;

            while((newEntry = rdr.readLine()) != null) {
                lastEntry = newEntry;
            }
            rdr.close();

        } catch (Exception e) {
            System.out.println("Cannot read last lan from log at: " + logFile.getPath());
            e.printStackTrace();
        }

        String[] entryComps = lastEntry.split(Constants.getSeparator());

        String lastLogLsn = entryComps[0];
        int lsnToBeConverted = Integer.parseInt(lastLogLsn);
        AtomicInteger lastLsn = new AtomicInteger(lsnToBeConverted);

        return lastLsn;
    }
}
