package de.dis2011.model;

import de.dis2011.data.Person;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-14
 */
public class PersonModel extends EntityModel<Person> {

    final private static String[] COLUMNS = {"ID", "First name", "Last name", "Address"};

    @Override
    public Object getValueAt(int i, int i1) {
        Person p = findByRow(i);
        switch (i1) {
            case 0: return p.getId();
            case 1: return p.getFirstName();
            case 2: return p.getName();
            case 3: return p.getAddress();
        }
        return null;
    }

    @Override
    public void setValueAt(Object o, int i, int i1) {
        Person p = findByRow(i);
        switch (i1) {
            case 1: p.setFirstName((String) o); break;
            case 2: p.setName((String) o); break;
            case 3: p.setAddress((String) o); break;
        }
        p.save();
    }

    @Override
    protected String[] getColumns() {
        return COLUMNS;
    }
}
