package de.dis2011.data.dao;

import de.dis2011.data.Estate;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-22
 */
abstract class EstateDao<EntityType extends Estate> extends Dao<EntityType> {

    public EstateDao(Class<EntityType> prototype) {
        super(prototype);
    }
}
