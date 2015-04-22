package de.dis2011.data.dao;

import de.dis2011.data.TenancyContract;
import org.hibernate.SessionFactory;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-22
 */
final public class TenancyContractDao extends Dao<TenancyContract> {

    public TenancyContractDao(SessionFactory factory) {
        super(factory, TenancyContract.class);
    }
}
