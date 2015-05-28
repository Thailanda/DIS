package de.dis2015.jtcdbs.managers;

import com.sun.corba.se.impl.orbutil.closure.Constant;
import de.dis2015.jtcdbs.log.entries.PageWriteLogEntry;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.dis2015.jtcdbs.Constants;
import de.dis2015.jtcdbs.LSNManager;
import de.dis2015.jtcdbs.LogManager;
import de.dis2015.jtcdbs.PersistenceManager;
import de.dis2015.jtcdbs.page.Page;

@Singleton
public class PersistenceManagerImpl implements PersistenceManager {

	private static String LOG_FILE_NAME;

	private static final int _bufferThreshold = 5;
	
	private static Random _transactRandom;

	@Inject
	LSNManager lsnManager;
	@Inject
	LogManager logManager;

	private static HashMap<Integer, Page> _buffer; // The buffer containing all
													// currently used pages
	private static HashSet<Integer> _ongoingTransactions; // A list of ongoing
															// transaction

	public PersistenceManagerImpl() {
		System.out.println("PersistanceManagerImpl created");
		
		_transactRandom = new Random();
		_ongoingTransactions = new HashSet<Integer>();
		_buffer = new HashMap<Integer, Page>();

		LOG_FILE_NAME = Constants.getLogPath() + Constants.getLogName() + Constants.getFileExtensionLogEntry();
	}

	@Override
	public int beginTransaction() {
		int transactionId = getNextTransactionID();

		String beginMsg = Constants.getBeginTransactionMessage();
		writeLogMessage(transactionId, beginMsg);

		System.out.println("Begin Transaction with ID " + transactionId);
		_ongoingTransactions.add(transactionId);

		return transactionId;
	}

	@Override
	public void commit(int tx) {
		System.out.println("Commit Transaction with ID " + tx);

		if (!_ongoingTransactions.contains(tx)) {
			System.out.println("No ongoing transaction found with ID: " + tx
					+ "!!");
			return;
		}
		
		//log commit so a redo is possible
		String commitMsg = Constants.getCommitMessage();
		writeLogMessage(tx, commitMsg);
		
		_ongoingTransactions.remove(tx);
		System.out.println("Transaction with ID " + tx + " is commited");

		checkBuffer();
	}

	@Override
	public void write(int tx, int pageId, String data) {
		int lsn = lsnManager.nextLSN();
		Page page = new Page(pageId, lsn, data);

		// Write a log entry.
		writeLog(page, tx);

		insertIntoBuffer(page, true);
		checkBuffer();
	}
	
	/**
	 * Check, whether the buffer's threshold is reached and flushes its contents
	 * to disk if it is
	 */
	public void checkBuffer() {
		System.out.println("Checking buffer...");
		if (isBufferThresholdReached()) {
			System.out.println("Threshold is reached, flushing buffer is mandatory...F");
			flushBufferToDisk();
		}
	}

	/**
	 * Flushes all pages currently in buffer to disk and cleans the buffer
	 */
	private void flushBufferToDisk() {
		System.out.println("Flushing buffer to disk...");
		for (Page p : _buffer.values()) {
			writePage(p.getPageId(), p.getLSN(), p.getData());
		}
		System.out.println("Flushing done");
		
		System.out.println("Clearing buffer...");
		_buffer.clear();
		System.out.println("Clearing buffer done");
	}

	/**
	 * Returns whether the maximum of pages in the buffer is reached
	 * 
	 * @return
	 */
	private boolean isBufferThresholdReached() {
		return _buffer.size() >= _bufferThreshold;
	}

	/**
	 * Insert a page into the buffer
	 * 
	 * @param page The page to be written
	 * @param overwrite Whether a page should be overwritten in the buffer
	 */
	private void insertIntoBuffer(Page page, boolean overwrite) {
		if (_buffer.containsKey(page.getPageId()) && !overwrite) {
			return;
		}

		System.out.println("Page with ID " + page.getPageId() + " stored in buffer");
		_buffer.put(page.getPageId(), page); 
	}

	/**
	 * Write a page to the disk
	 * 
	 * @param pageId
	 * @param lsn
	 * @param data
	 * @return Whether the page was written successfully or not
	 */
	private boolean writePage(int pageId, int lsn, String data) {
		String fileName = Constants.getPersistenceStoragePath() + pageId
				+ Constants.getFileExtensionPage();
		try (FileWriter fw = new FileWriter(fileName)) {
			fw.write(pageId + Constants.getSeparator() + lsn
					+ Constants.getSeparator() + data);
			fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		// Page successfully written
		return true;
	}

	/**
	 * Returns an random unused Transaction ID
	 * 
	 * @return
	 */
	private int getNextTransactionID() {
		int i = _transactRandom.nextInt();
		
		while (_ongoingTransactions.contains(i))
		{
			i = _transactRandom.nextInt();
		}
		
		return i;
	}

	/**
	 * Write page information into log
	 */
	private void writeLog(Page page, int tx) {

		try (FileWriter writer = new FileWriter(LOG_FILE_NAME, true)) {
			PageWriteLogEntry pageWriteLogEntry = new PageWriteLogEntry(page, tx);
			logManager.writeLogEntry(writer, pageWriteLogEntry);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Write messages related to a certain transaction into log 
	 * @param tx id of the Transaction
	 * @param msg message e.g. a commit message
	 */
	private void writeLogMessage(int tx, String msg) {
		
		int pageNumber = Constants.getDefaultPageNumber();
		int lsn = lsnManager.nextLSN();
		Page page = new Page(pageNumber, lsn, msg);
		writeLog(page, tx);
	}
}
