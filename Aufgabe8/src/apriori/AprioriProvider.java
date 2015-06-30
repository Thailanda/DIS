package apriori;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AprioriProvider {

	private List<List<Integer>> _itemSets;
	private int MIN_SUP = 1;

	public AprioriProvider() {
		System.out.println("Creating Provider");
		setUp();
	}

	public void run() {
		findFrequentOneItemset();

		int count = 0;
		
		for (int k = 1; _itemSets.get(k - 1) != null; ++k) {
			System.out.println(_itemSets.get(k - 1).size());
			List<List<Integer>> ck = generateCandidates(_itemSets.get(k - 1));
			
			for (List<Integer> itemSet : ck) 
			{
				for (Integer item : itemSet)
				{
					if (itemSet.contains(item))
					{
						count++;
					}
				}
			}
			
			ArrayList<Integer> n = new ArrayList<Integer>();
			n.add(count);
			_itemSets.set(k, n);
		}
	}

	private List<List<Integer>> generateCandidates(List<Integer> is) {
		// TODO Auto-generated method stub
		return null;
	}

	private void findFrequentOneItemset() {
		// TODO Auto-generated method stub

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

				_itemSets = new ArrayList<>();

				String tmp;
				while ((tmp = rdr.readLine()) != null) {
					List<Integer> itemSet = new ArrayList<Integer>();

					String[] transactions = tmp.split(" ");

					for (String a : transactions) {
						itemSet.add(Integer.parseInt(a));
					}

					_itemSets.add(itemSet);
				}

				System.out.println("Read " + _itemSets.size() + " Datasets");

				rdr.close();

			} catch (FileNotFoundException e) {
				System.out.println("File " + fileName + " not found");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("IOException");
				e.printStackTrace();
			}
		}
	}
}
