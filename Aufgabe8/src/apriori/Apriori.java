package apriori;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Apriori
{
    // List structure to work with
    private List<int[]> _ItemSets;

    // The input file
    private String _InputFile = "trans_test";

    // No of different elements in transactions
    private int _noItems = 0;

    // No of different transactions in file
    private int _noTransactions = 0;

    // MIN_SUP
    private double MIN_SUP = 0.01;

    public Apriori(String filename, double minSup)
    {
        _InputFile = filename;
        MIN_SUP = minSup;
        prepare();
    }

    public void execute()
    {
        System.out.println("Starting Apriori Algorithm on file: " + _InputFile);
        System.out.println("MIN_SUP is " + MIN_SUP + " (" + (MIN_SUP * 100) + "%)");
        System.out.println("");

        // start timer
        long start = System.currentTimeMillis();

        // initial 1-itemsets
        findFrequentOneItemsets();

        int itemsetNumber = 1; // Start with the first itemset
        int countFrequentSetsFound = 0; // Number of frequent itemsets found

        while (_ItemSets.size() > 0)
        {

            // all Lk-1 possible k-itemsets
            calculateFrequentItemsets();

            if (_ItemSets.size() != 0)
            {
                countFrequentSetsFound += _ItemSets.size();
                System.out.println(_ItemSets.size() + " ItemSets were frequent with size: " + itemsetNumber);

                createPermutationsFromPrevious();
            }

            // Continue with the next set
            itemsetNumber++;
        }

        long end = System.currentTimeMillis();

        System.out.println("Found " + countFrequentSetsFound + " frequents sets for support " + (MIN_SUP * 100)
                + "% (absolute " + Math.round(_noTransactions * MIN_SUP) + ")");

        System.out.println("Computation took " + ((double) (end - start) / 1000) + " seconds");
        System.out.println("Finished!");
    }

    private void prepare()
    {
        // Read number of transactions from file beforehand
        // TODO Inefficient as f**k
        try
        {
            BufferedReader data_in = new BufferedReader(new FileReader(_InputFile));
            while (data_in.ready())
            {
                String line = data_in.readLine();
                _noTransactions++;

                String[] transaction = line.split(" ");

                for (String elem : transaction)
                {
                    int parsedInt = Integer.parseInt(elem);

                    if (parsedInt + 1 > _noItems)
                        _noItems = parsedInt + 1;
                }
            }

            data_in.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void findFrequentOneItemsets()
    {
        System.out.println("Creating initial Itemsets of size 1...");
        // Create an itemset for each element
        // Therefore creating itemsets containing one item each
        _ItemSets = new ArrayList<int[]>();
        for (int i = 0; i < _noItems; i++)
        {
            int[] item = { i };
            _ItemSets.add(item);
        }

        System.out.println(_ItemSets.size() + " itemsets of size one created");
        System.out.println("");
    }

    private void createPermutationsFromPrevious()
    {
        System.out.println("Creating permuted itemsets from previous results...");

        // New sets have a fixed size
        int size = _ItemSets.get(0).length;

        System.out.println("Itemsets will have size: " + size);
        System.out.println("Number of previous sets: " + _ItemSets.size());

        // Temporary Map of possible elements
        HashMap<String, int[]> possibleElements = new HashMap<String, int[]>();

        // compare elements pairwise to find possible permutations
        for (int i = 0; i < _ItemSets.size(); i++)
        {
            for (int j = i + 1; j < _ItemSets.size(); j++)
            {
                int[] elemA = _ItemSets.get(i);
                int[] elemB = _ItemSets.get(j);

                int[] possibleE = new int[size + 1];
                for (int s = 0; s < possibleE.length - 1; s++)
                {
                    possibleE[s] = elemA[s];
                }

                int diff = 0;
                // Search for missing value
                for (int s1 = 0; s1 < elemB.length; s1++)
                {
                    boolean found = false;
                    // is elemB[s1] in elemA?
                    for (int s2 = 0; s2 < elemA.length; s2++)
                    {
                        if (elemA[s2] == elemB[s1])
                        {
                            found = true;
                            break;
                        }
                    }
                    if (!found)
                    { // elemB[s1] is not in elemA
                        diff++;
                        // append possible element
                        possibleE[possibleE.length - 1] = elemB[s1];
                    }

                }
                if (diff == 1)
                {
                    // System.out.println(Arrays.toString(possibleE)); // We
                    // don't need to see the permutations
                    Arrays.sort(possibleE);
                    possibleElements.put(Arrays.toString(possibleE), possibleE);
                }
            }
        }

        // set the new itemsets
        _ItemSets = new ArrayList<int[]>(possibleElements.values());
        System.out.println("Created " + _ItemSets.size() + " unique itemsets with size " + (size + 1));
        System.out.println("");
    }

    private void convTransactionString(String line, boolean[] transBoolArray)
    {
        Arrays.fill(transBoolArray, false);

        // Split Transaction
        String[] spl = line.split(" ");

        // Parse each value and set its index in the array to true
        for (String s : spl)
        {
            int valuee = Integer.parseInt(s);
            transBoolArray[valuee] = true;
        }
    }

    private void calculateFrequentItemsets()
    {
        List<int[]> frequentElements = new ArrayList<int[]>();

        boolean match; // true if this transaction has all demanded elements

        int count[] = new int[_ItemSets.size()]; // counts of found matches

        // read file
        BufferedReader rdr = null;
        try
        {
            rdr = new BufferedReader(new InputStreamReader(new FileInputStream(_InputFile)));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        boolean[] transBoolArray = new boolean[_noItems];

        // read each line
        for (int i = 0; i < _noTransactions; i++)
        {
            String line = "";
            try
            {
                line = rdr.readLine();
                convTransactionString(line, transBoolArray);

                // check each candidate
                for (int c = 0; c < _ItemSets.size(); c++)
                {
                    match = true;
                    int[] cand = _ItemSets.get(c);

                    for (int elem : cand)
                    {
                        if (transBoolArray[elem] == false)
                        {
                            match = false;
                            break;
                        }
                    }
                    if (match)
                    {
                        count[c]++;
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        try
        {
            rdr.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("Following frequent ItemSets were found (<Set> : <Confidence> - <Occurences>):");

        for (int i = 0; i < _ItemSets.size(); i++)
        {
            // if the percentage in count is larger than the one in minsup, add
            // to frequent elements
            if ((count[i] / (double) (_noTransactions)) >= MIN_SUP)
            {
                System.out.println(String.format("%s : %s - %d", Arrays.toString(_ItemSets.get(i)), count[i] / (double) _noTransactions, count[i]));
//                System.out.println(Arrays.toString(_ItemSets.get(i)) + "  " + ((count[i] / (double) _noTransactions))
//                        + " - " + count[i]);
                frequentElements.add(_ItemSets.get(i));
            }
        }

        // new candidates are only the frequent candidates
        _ItemSets = frequentElements;

        System.out.println("");
    }
}