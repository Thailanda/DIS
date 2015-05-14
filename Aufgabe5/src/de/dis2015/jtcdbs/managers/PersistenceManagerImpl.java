package de.dis2015.jtcdbs.managers;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import de.dis2015.jtcdbs.PersistenceManager;
import de.dis2015.jtcdbs.page.Page;

public class PersistenceManagerImpl implements PersistenceManager {

	private static HashMap<Integer, Page> _buffer;
	private static HashSet<Integer> _ongoingTransactions;

	private static final String _persistanceStoragePath = "persistentDataStorage/";
	private static final String _logPath = "logStorate/";
	private static final String _fileExtensionPage = ".ppg"; // ppg ~ PersistentPaGe
	private static final String _fileExtensionLogEntry = ".plog"; // plog ~ PageLog
	private static final String _separator = ";";

	private long _lastLSN = 0;

	@Override
	public int beginTransaction() {
		int transactionId = getNextTransactionID();
		_ongoingTransactions.add(transactionId);	
		
		return transactionId;
	}

	@Override
	public void commit(int tx) {
		_ongoingTransactions.remove(tx);
		//TODO Write Page to buffer?
	}

	@Override
	public void write(int tx, int pageId, String data) {
		// TODO Hier noch die LSN
		writePage(pageId, -1, data);
		writeLogEntry(tx, pageId, data);
	}

	private void insertIntoBuffer(Page page, boolean overwrite) {
		if (_buffer.containsKey(page.getPageNo()) && !overwrite) {
			return;
		}

		_buffer.put(page.getPageNo(), page);
	}

	private boolean writePage(int pageId, int lsn, String data) {
		try {
			FileWriter fw = new FileWriter(_persistanceStoragePath + pageId
					+ _fileExtensionPage);

			fw.write(pageId + _separator + lsn + _separator + data);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		// Page successfully written
		return true;
	}

	private boolean writeLogEntry(int tx, int pageId, String data) {
		try {
			FileWriter fw = new FileWriter(_logPath + tx + "."
					+ pageId + _fileExtensionLogEntry);

			fw.write(tx + _separator + pageId + _separator + getNextLSN()
					+ _separator + data);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		// Log successfully written
		return true;
	}

	private long getNextLSN() {
		_lastLSN += 1;
		return _lastLSN;
	}

	private int getNextTransactionID() {
		//TODO return correct TransactionID not already in use
		return 0;
	}
}
