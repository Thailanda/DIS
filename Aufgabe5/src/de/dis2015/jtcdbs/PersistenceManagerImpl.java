package de.dis2015.jtcdbs;

import java.io.FileWriter;
import java.io.IOException;

public class PersistenceManagerImpl implements PersistenceManager {

	private static final String _persistancePath = "persistentDataStorage/";
	private static final String _fileExtension = ".ppg"; // ppg ~ PersistentPaGe
	private static final String _fileSeparator = ".";

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
		// TODO Hier fehlt bestimmt noch was
		writePage(tx, pageId, data);
	}

	private boolean writePage(int tx, int pageId, String data) {
		try {
			FileWriter fw = new FileWriter(_persistancePath + _fileSeparator
					+ tx + _fileSeparator + pageId + _fileExtension);
			fw.write(tx + pageId + data);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		// Page successfully written
		return true;
	}
}
