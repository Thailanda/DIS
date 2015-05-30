package de.dis2015.jtcdbs.recovery;

import java.io.*;
import java.util.LinkedList;

import com.google.inject.Inject;

import de.dis2015.jtcdbs.Constants;
import de.dis2015.jtcdbs.LogEntry;
import de.dis2015.jtcdbs.LogManager;
import de.dis2015.jtcdbs.PersistenceManager;
import de.dis2015.jtcdbs.log.entries.PageWriteLogEntry;
import de.dis2015.jtcdbs.managers.PersistenceManagerImpl;
import de.dis2015.jtcdbs.page.Page;

public class RecoveryManager {
	@Inject
	PersistenceManager manager;

    @Inject
    LogManager logManager;

	public RecoveryManager() {
		System.out.println("Recovery Manager created");
	}

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
	/**
	 * Perform a recovery. Includes reading all logs and deciding, whether a recovery is necessary for each page
	 */
	public void recover() {
		System.out.println("Starting recovery of pages...");
		//readLogs();
        //TODO recovery
        String logPath = Constants.getLogPath() + Constants.getLogName() + Constants.getFileExtensionLogEntry();
        File log = new File(logPath);
        LinkedList<String> allLogEntries = readLogFile(log);
        LinkedList<String[]> winnerEntries = determineWinnerTransactions(allLogEntries);
        decideRedo(winnerEntries); // also performs the redo

        //backup the log and drop it
        String oldPath = logPath + Constants.getFileExtensionBackup();
        File oldLog = new File(oldPath);
        if (oldLog.exists()) {
            oldLog.delete();
        }
        log.renameTo(oldLog);
		System.out.println("Recovery completed!");
	}

	/**
	 * Decides, whether a recovery is necessary of the page given
	 * @param winnerContents has to be ordered descending by the lsn
	 */
	private void decideRedo(LinkedList<String[]> winnerContents) {

        while(!winnerContents.isEmpty()){
            String[] entry = winnerContents.removeLast(); //newest winner transaction
            String lsn = entry[0];
            String clasName = entry[1];
            String pageNo = entry[2];
            String transactionNo = entry[3];
            String content = entry[4];

            if(isWriteEntry(content) && isBetterLSN(lsn, pageNo)) {
                redo(Integer.parseInt(transactionNo), Integer.parseInt(pageNo), Integer.parseInt(lsn), content, clasName);
            }
        }
	}

    /**
     *
     * @param entry the content of a log entry (data)
     * @return true if log entry can be used for a redo
     */
    private boolean isWriteEntry(String entry) {

        String beginTransactionEntry = Constants.getBeginTransactionMessage();
        String commitEntry = Constants.getCommitMessage();
        boolean entryType = !(entry.equals(beginTransactionEntry) || entry.equals(commitEntry));

        return entryType;
    }

    /**
     * Returns true if the lsn in the log is newer (bigger) than the one in the page
     */
    private boolean isBetterLSN(String logLSN, String pageNo) {

        boolean isBetter = false;
        String path = Constants.getPersistenceStoragePath() + pageNo + Constants.getFileExtensionPage();
        File page = new File(path);

        if(page.exists()) {

            try {

                BufferedReader rdr = new BufferedReader(new FileReader(page));

                String entry = rdr.readLine();
                String[] comp = entry.split(Constants.getSeparator());
                String pageLsn = comp[0];
                rdr.close();

                isBetter = (new Integer(logLSN) > new Integer(pageLsn));

            } catch (Exception e) {
                System.out.println("Page could not be read at: " + page.getPath());
                e.printStackTrace();
            }
        }
        else if (!page.exists()) { isBetter = true; }; //if no such page exists a redo is needed

        return isBetter;
    }

    /**
     * Finds out what data belongs to committed transactions
     *
     * @param fileContents in ascending order (by lsn)
     * @return data of the winner transaction in descending order (lsn),
     * the list entry is a String[] with the components of each line from fileContents
     */
    private LinkedList<String[]> determineWinnerTransactions(LinkedList<String> fileContents){

        System.out.println("Determining winner transactions");

        LinkedList<String> winnerTransactions = new LinkedList<>(); //transaction id of all committed transactions
        LinkedList<String[]> winnerData = new LinkedList<>(); //data of all committed transactions

        while(fileContents.size() > 1) {

            String entry = fileContents.removeFirst();
            String[] comp = entry.split(Constants.getSeparator());

            String content = comp[4];
            String transaction = comp[3];
            if (content.equals(Constants.getCommitMessage()))  {
                winnerTransactions.add(transaction);
                winnerData.add(comp);
            }
            else if(winnerTransactions.contains(transaction)) {
                winnerData.add(comp);
            }

        }

        return winnerData;
    }

	/**
	 * Redoes a transaction in the buffer
	 * @param txId
	 * @param pageId
	 * @param lsn
	 * @param data
	 */
	private void redo(int txId, int pageId, int lsn, String data, String cName) {

        manager.redoWrite(pageId, lsn, data);
        System.out.println("redo write on page " + pageId);

        /**
        LogEntry entry = logManager.createLogEntry(lsn, cName);

        if(entry instanceof PageWriteLogEntry) {
            manager.redoWrite(pageId, lsn, data);
            System.out.println("redo write on page " + pageId);
        }
         */
	}

	/**
	 * Read all files 
	 */
	private void readLogs() {

        //TODO use only the new log file that was not recovered
        for (final File log : new File(Constants.getLogPath()).listFiles()) {
			if (!log.isDirectory()) {
				readLogFile(log);
			} else {
				// There is a directory that does not belong here...
				System.out.println(log.getName());
			}
		}
	}

	/**
	 * Read a log-file and decide what to do with it
	 * @param log
     * @return log lines ordered by their appearance in the log (lsn ascending)
	 */
	private LinkedList<String> readLogFile(File log) {

        System.out.println("Reading log data");

        LinkedList<String> contents = new LinkedList<>();
        try {
			BufferedReader rdr = new BufferedReader(new FileReader(log));
            String entry = null;
			while((entry = rdr.readLine()) != null) {
                contents.addFirst(entry); //TODO ordered entries
            }
			rdr.close();

			//decideRedo(contents);
		} catch (Exception e) {
			System.out.println("Page could not be read at: " + log.getPath());
			e.printStackTrace();
		}

        return contents;
	}
}
