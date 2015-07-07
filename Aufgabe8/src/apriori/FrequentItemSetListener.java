package apriori;

import java.util.List;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-07-07
 */
public interface FrequentItemSetListener {

    void foundItemSet(int size, List<FrequentItemSet> itemSets);
}
