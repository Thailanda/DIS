package de.dis2011.data.dao;

import de.dis2011.data.PurchaseContract;
import org.hibernate.SessionFactory;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-22
 */
final public class PurchaseContractDao extends Dao<PurchaseContract> {

    public PurchaseContractDao(SessionFactory factory) {
        super(factory, PurchaseContract.class);
    }
}
