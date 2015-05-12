package de.dis2011.data.dao;

import de.dis2011.data.Apartment;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-22
 */
final public class ApartmentDao extends EstateDao<Apartment> {

    public ApartmentDao() {
        super(Apartment.class);
    }
}
