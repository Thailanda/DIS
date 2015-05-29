package de.dis2015.jtcdbs.recovery;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;

import com.google.inject.Inject;

import de.dis2015.jtcdbs.Constants;
import de.dis2015.jtcdbs.PersistenceManager;

public class RecoveryManager {
	@Inject
	PersistenceManager manager;

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
        decideRedo(winnerEntries);
        //decide redo
        //redo
        //delete or rename the old log
		System.out.println("Recovery completed!");
	}

	/**
	 * Decides, whether a recovery is necessary of the page given
	 * @param winnerContents has to be ordered descending by the lsn
	 */
	private void decideRedo(LinkedList<String[]> winnerContents) {

        while(!winnerContents.isEmpty()){
            String[] entry = winnerContents.removeLast();
            String lsn = entry[0];
            String clazz = entry[1];
            String pageNo = entry[2];
            String content = entry[4];

            if(isWriteEntry(content) && isBetterLSN(lsn, pageNo)) {
                //TODO
            }
        }

        //TODO decide redo using the winner transactions and the lsn
        //TODO redo
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
        //TODO
        boolean isBetter = false;
        String path = Constants.getPersistenceStoragePath() + pageNo + Constants.getFileExtensionPage();
        File page = new File(path);

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

        LinkedList<String> winnerTransactions = new LinkedList<>(); //transaction id of all committed transactions
        LinkedList<String[]> winnerData = new LinkedList<>(); //data of all committed transactions

        while(fileContents.size() > 1) {

            String entry = fileContents.removeLast();
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
	private void redo(int txId, int pageId, int lsn, String data) {
		// TODO redo pages in buffer if lsn in log > lsn of page id in buffer

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

        LinkedList<String> contents = new LinkedList<>();
        try {
			BufferedReader rdr = new BufferedReader(new FileReader(log));


            String entry = null;
			while((entry = rdr.readLine()) != null) {
                entry = rdr.readLine();
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
