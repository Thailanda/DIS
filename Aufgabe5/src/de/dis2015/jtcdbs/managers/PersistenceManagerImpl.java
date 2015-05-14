package de.dis2015.jtcdbs.managers;

import java.io.FileWriter;
import java.io.IOException;

import de.dis2015.jtcdbs.PersistenceManager;

public class PersistenceManagerImpl implements PersistenceManager {

	private static final String _persistanceStoragePath = "persistentDataStorage/";
	private static final String _fileExtension = ".ppg"; // ppg ~ PersistentPaGe
	private static final String _separator = ";";

	private long _lastLSN = 0;

	@Override
	public int beginTransaction() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void commit(int tx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void write(int tx, int pageId, String data) {
		// TODO Hier noch die LSN
		writePage(pageId, -1, data);
		writeLogEntry(tx, pageId, data);
	}

	private boolean writePage(int pageId, int lsn, String data) {
		try {
			FileWriter fw = new FileWriter(_persistanceStoragePath + pageId
					+ _fileExtension);

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
			FileWriter fw = new FileWriter(_persistanceStoragePath + tx + "."
					+ pageId + _fileExtension);

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
}
