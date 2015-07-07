package apriori;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Apriori {

    // List structure to work with
    private List<int[]> _ItemSets;

    public Apriori() {
    }

    public void execute(Reader reader, double minSup, FrequentItemSetListener listener) {
        final List<int[]> transactions = readTransactions(reader);
        final int itemSetCount = countItemSets(transactions);

        System.out.println("Starting Apriori Algorithm on reader, item set count: " + itemSetCount);
        System.out.println("MIN_SUP is " + minSup + " (" + (minSup * 100) + "%)");
        System.out.println("");

        // start timer
        long start = System.currentTimeMillis();

        // initial 1-item sets
        findFrequentOneItemSets(itemSetCount);

        int itemSetNumber = 1; // Start with the first item set
        int countFrequentSetsFound = 0; // Number of frequent item sets found

        while (_ItemSets.size() > 0) {

            // all Lk-1 possible k-item sets
            calculateFrequentItemSets(itemSetNumber, transactions, minSup, itemSetCount, listener);

            if (_ItemSets.size() != 0) {
                countFrequentSetsFound += _ItemSets.size();
                System.out.println(_ItemSets.size() + " ItemSets were frequent with size: " + itemSetNumber);

                createPermutationsFromPrevious();
            }

            // Continue with the next set
            itemSetNumber++;
        }

        long end = System.currentTimeMillis();

        System.out.println("Found " + countFrequentSetsFound + " frequents sets for support " + (minSup * 100)
                + "% (absolute " + Math.round(transactions.size() * minSup) + ")");

        System.out.println("Computation took " + ((double) (end - start) / 1000) + " seconds");
        System.out.println("Finished!");
    }

    private List<int[]> readTransactions(Reader reader) {
        List<int[]> transactions = new ArrayList<>();
        // Read number of transactions from file beforehand
        // TODO Inefficient as f**k
        try (BufferedReader br = new BufferedReader(reader)) {
            while (br.ready()) {
                String line = br.readLine();

                String[] transaction = line.split(" ");
                int[] numbers = new int[transaction.length];
                for (int i = 0; i < transaction.length; i++) {
                    numbers[i] = Integer.parseInt(transaction[i]);
                }

                transactions.add(numbers);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return transactions;
    }

    private int countItemSets(List<int[]> transactions) {
        int itemSetCount = 0;
        for (int[] transaction : transactions) {
            for (int elem : transaction) {
                if (elem + 1 > itemSetCount) {
                    itemSetCount = elem + 1;
                }
            }
        }

        return itemSetCount;
    }

    private void findFrequentOneItemSets(int itemSetCount) {
        System.out.println("Creating initial item sets of size 1...");
        // Create an item set for each element
        // Therefore creating item sets containing one item each
        _ItemSets = new ArrayList<>();
        for (int i = 0; i < itemSetCount; i++) {
            _ItemSets.add(new int[]{i});
        }

        System.out.println(_ItemSets.size() + " item sets of size one created");
        System.out.println();
    }

    private void createPermutationsFromPrevious() {
        System.out.println("Creating permuted item sets from previous results...");

        // New sets have a fixed size
        int size = _ItemSets.get(0).length;

        System.out.println("Itemsets will have size: " + size);
        System.out.println("Number of previous sets: " + _ItemSets.size());

        // Temporary Map of possible elements
        HashMap<String, int[]> possibleElements = new HashMap<String, int[]>();

        // compare elements pairwise to find possible permutations
        for (int i = 0; i < _ItemSets.size(); i++) {
            for (int j = i + 1; j < _ItemSets.size(); j++) {
                int[] elemA = _ItemSets.get(i);
                int[] elemB = _ItemSets.get(j);

                int[] possibleE = new int[size + 1];
                System.arraycopy(elemA, 0, possibleE, 0, possibleE.length - 1);

                int diff = 0;
                // Search for missing value
                for (int anElemB : elemB) {
                    boolean found = false;
                    // is elemB[s1] in elemA?
                    for (int anElemA : elemA) {
                        if (anElemA == anElemB) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) { // elemB[s1] is not in elemA
                        diff++;
                        // append possible element
                        possibleE[possibleE.length - 1] = anElemB;
                    }

                }
                if (diff == 1) {
                    // System.out.println(Arrays.toString(possibleE)); // We
                    // don't need to see the permutations
                    Arrays.sort(possibleE);
                    possibleElements.put(Arrays.toString(possibleE), possibleE);
                }
            }
        }

        // set the new item sets
        _ItemSets = new ArrayList<int[]>(possibleElements.values());
        System.out.println("Created " + _ItemSets.size() + " unique item sets with size " + (size + 1));
        System.out.println("");
    }

    private boolean[] makeTransactionMask(int[] transaction, int itemSetCount) {
        boolean[] transBoolArray = new boolean[itemSetCount];
        Arrays.fill(transBoolArray, false);

        // Parse each value and set its index in the array to true
        for (int item : transaction) {
            transBoolArray[item] = true;
        }

        return transBoolArray;
    }

    private void calculateFrequentItemSets(int itemSetNumber, List<int[]> transactions, double minSup, int itemSetCount,
                                           FrequentItemSetListener listener) {
        List<int[]> frequentElements = new ArrayList<>();

        boolean match; // true if this transaction has all demanded elements

        int count[] = new int[_ItemSets.size()]; // counts of found matches

        for (int[] transaction : transactions) {

            // read each line
            boolean[] transBoolArray = makeTransactionMask(transaction, itemSetCount);

            // check each candidate
            for (int c = 0; c < _ItemSets.size(); c++) {
                match = true;
                int[] candidates = _ItemSets.get(c);

                for (int elem : candidates) {
                    if (!transBoolArray[elem]) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    count[c]++;
                }
            }
        }

        List<FrequentItemSet> itemSets = new ArrayList<>(_ItemSets.size());
        for (int i = 0; i < _ItemSets.size(); i++) {
            // if the percentage in count is larger than the one in min sup, add
            // to frequent elements
            final int occurrences = count[i];
            final double confidence = occurrences / (double) (transactions.size());
            if (confidence >= minSup) {
                itemSets.add(new FrequentItemSet(_ItemSets.get(i), confidence, occurrences));
                frequentElements.add(_ItemSets.get(i));
            }
        }

        listener.foundItemSet(itemSetNumber, itemSets);

        // new candidates are only the frequent candidates
        _ItemSets = frequentElements;

        System.out.println("");
    }
}
