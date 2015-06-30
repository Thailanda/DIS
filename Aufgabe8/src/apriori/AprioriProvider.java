package apriori;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class AprioriProvider {
	public AprioriProvider() {
		System.out.println("Creating Provider");
		setUp();
	}

	public void run() {

	}

	private void setUp() {
		System.out.println("Setting up...");
	}

	public void readTransactions(String fileName) {
		System.out.println("Reading Transactions");

		File file = new File(fileName);

		if (file.exists()) {
			try {
				BufferedReader rdr = new BufferedReader(new FileReader(file));
				
				
			} catch (FileNotFoundException e) {
				System.out.println("File " + fileName + " not found");
				e.printStackTrace();
			}

		}
	}
}
