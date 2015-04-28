package de.dis2011.model;

import de.dis2011.data.Person;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-14
 */
public class PersonModel extends EntityModel<Person> {

    final private static String[] COLUMNS = {"ID", "First name", "Last name", "Address", "No. of Estates"};

    @Override
    public Object getValueAt(int i, int i1) {
        Person person = findByRow(i);
        switch (i1) {
            case 0: return person.getId();
            case 1: return person.getFirstName();
            case 2: return person.getName();
            case 3: return person.getAddress();
            case 4: return person.getContracts() != null ? person.getContracts().size() : 0;
        }
        return null;
    }

    @Override
    public void setValueAt(Object o, int i, int i1) {
        Person person = findByRow(i);
        switch (i1) {
            case 1: person.setFirstName((String) o); break;
            case 2: person.setName((String) o); break;
            case 3: person.setAddress((String) o); break;
            case 4: return;
        }
        dao.save(person);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return super.isCellEditable(row, column) && column != 4;
    }

    @Override
    public Class<?> getColumnClass(int column) {
        if (column == 4) {
            return Integer.class;
        }

        return super.getColumnClass(column);
    }

    @Override
    protected String[] getColumns() {
        return COLUMNS;
    }
}
