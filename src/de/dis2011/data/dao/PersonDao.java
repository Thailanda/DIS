package de.dis2011.data.dao;

import de.dis2011.data.Person;
import org.hibernate.SessionFactory;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-22
 */
final public class PersonDao extends Dao<Person> {

    public PersonDao(SessionFactory factory) {
        super(factory, Person.class);
    }
}
