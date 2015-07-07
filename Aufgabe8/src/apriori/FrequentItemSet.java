package apriori;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-07-07
 */
public class FrequentItemSet {

    private final int[] set;
    private final double confidence;
    private final int occurrences;

    public FrequentItemSet(int[] set, double confidence, int occurrences) {
        this.set = set;
        this.confidence = confidence;
        this.occurrences = occurrences;
    }

    public int[] getSet() {
        return set;
    }

    public double getConfidence() {
        return confidence;
    }

    public int getOccurrences() {
        return occurrences;
    }
}
