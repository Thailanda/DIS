package de.dis2015.jtcdbs.recovery;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import com.google.inject.Inject;

import de.dis2015.jtcdbs.Constants;
import de.dis2015.jtcdbs.PersistenceManager;

public class RecoveryManager {
	@Inject
	PersistenceManager manager;

	public RecoveryManager() {
		System.out.println("Recovery Manager created");
	}

	/**
	 * Perform a recovery. Includes reading all logs and deciding, whether a recovery is necessary for each page
	 */
	public void recover() {
		System.out.println("Starting recovery of pages...");
		readLogs();
		System.out.println("Recovery completed!");
	}

	/**
	 * Decides, whether a recovery is necessary of the page given
	 * @param fileContents
	 */
	private void decideRedo(String fileContents) {
		String[] comp = fileContents.split(";");

		// TODO Decide whether to perform an redo or not based on LSN in log and
		// in pages in buffer
		redo(Integer.parseInt(comp[0]), Integer.parseInt(comp[1]),
				Integer.parseInt(comp[2]), comp[3]);
	}

	/**
	 * Undoes a transaction in the buffer
	 * @param txId
	 * @param pageId
	 * @param lsn
	 * @param data
	 */
	private void redo(int txId, int pageId, int lsn, String data) {
		// TODO undo pages in buffer if lsn in log > lsn of page id in buffer
	}

	
	/**
	 * Read all files 
	 */
	private void readLogs() {
		for (final File page : new File(Constants.getLogPath()).listFiles()) {
			if (!page.isDirectory()) {
				readLogPage(page);
			} else {
				// There is a directory that does not belong here...
				System.out.println(page.getName());
			}
		}
	}

	/**
	 * Read a log-page and decide what to do with it
	 * @param page
	 */
	private void readLogPage(File page) {
		try {
			BufferedReader rdr = new BufferedReader(new FileReader(page));

			String contents = rdr.readLine(); // we assume there is only one
												// line in the file
			rdr.close();

			decideRedo(contents);
		} catch (Exception e) {
			System.out.println("Page could not be read at: " + page.getPath());
			e.printStackTrace();
		}
	}
}
