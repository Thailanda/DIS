package de.dis2011.data.dao;

import de.dis2011.data.House;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-22
 */
final public class HouseDao extends EstateDao<House> {

    public HouseDao() {
        super(House.class);
    }
}
