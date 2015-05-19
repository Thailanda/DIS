package de.dis2015.jtcdbs.managers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.dis2015.jtcdbs.Constants;
import de.dis2015.jtcdbs.LSNManager;
import de.dis2015.jtcdbs.LogManager;
import de.dis2015.jtcdbs.PersistenceManager;
import de.dis2015.jtcdbs.page.Page;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

@Singleton
public class PersistenceManagerImpl implements PersistenceManager {

	@Inject LSNManager lsnManager;
	@Inject LogManager logManager;

	private static HashMap<Integer, Page> _buffer; // The buffer containing all
													// currently used pages
	private static HashSet<Integer> _ongoingTransactions; // A list of ongoing
															// transaction

	public PersistenceManagerImpl() {
		restorePages();

		System.out.println("PersistanceManagerImpl created");
	}

	@Override
	public int beginTransaction() {
		int transactionId = getNextTransactionID();

		System.out.println("Begin Transaction with ID: " + transactionId);
		_ongoingTransactions.add(transactionId);
		
		// TODO Use a mutex for transaction? e.g. lock a page?

		return transactionId;
	}

	@Override
	public void commit(int tx) {
		System.out.println("Commit Transaction with ID: " + tx);
		_ongoingTransactions.remove(tx);
		// TODO Write Page to buffer?
	}

	@Override
	public void write(int tx, int pageId, String data) {
		// TODO Hier noch die LSN
		int lsn = lsnManager.nextLSN();
		insertIntoBuffer(new Page(pageId, lsn, data), true);
//		writePage(pageId, lsn, data);

		writeLogEntry(tx, pageId, data);
	}

	/**
	 * Insert a page into the buffer
	 * 
	 * @param page
	 * @param overwrite
	 */
	private void insertIntoBuffer(Page page, boolean overwrite) {
		if (_buffer.containsKey(page.getPageNo()) && !overwrite) {
			return;
		}

		_buffer.put(page.getPageNo(), page);
	}

	/**
	 * Restores the buffer after a restart -> Reads all pages from the disk and
	 * inserts their contents into the buffer
	 */
	private void restorePages() {
		// TODO Auto-generated method stub

		// TODOset _lastLSN to max from buffer + 1;
	}

	/**
	 * Write a page to the disk
	 * 
	 * @param pageId
	 * @param lsn
	 * @param data
	 * @return Whether the page was written sucessfully or not
	 */
	private boolean writePage(int pageId, int lsn, String data) {
		String fileName = Constants.getPersistenceStoragePath() + pageId + Constants.getFileExtensionPage();
		try (FileWriter fw = new FileWriter(fileName)) {
			fw.write(pageId + Constants.getSeparator() + lsn + Constants.getSeparator() + data);
			fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		// Page successfully written
		return true;
	}

	/**
	 * Write a log entry for the given transaction and page
	 * 
	 * @param tx
	 * @param pageId
	 * @param data
	 * @return Whether the log was written sucessfully or not
	 */
	private boolean writeLogEntry(int tx, int pageId, String data) {
		try {
			FileWriter fw = new FileWriter(Constants.getLogPath() + tx + "."
					+ pageId + Constants.getFileExtensionLogEntry());

			fw.write(tx + Constants.getSeparator() + pageId
					+ Constants.getSeparator() + getNextLSN()
					+ Constants.getSeparator() + data);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		// Log successfully written
		return true;
	}

	/**
	 * Return the next unused transaction ID
	 * 
	 * @return
	 */
	private int getNextTransactionID() {
		// TODO return correct TransactionID not already in use
		return 0;
	}
}
