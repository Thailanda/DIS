package de.dis2011.gui;

import de.dis2011.data.Person;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-14
 */
public class PersonModel extends AbstractTableModel {

    final private static String[] COLUMNS = {"ID", "First name", "Last name", "Address"};
    final private List<Person> persons;

    public PersonModel() {
        persons = new ArrayList<Person>();
    }

    @Override
    public int getRowCount() {
        return persons.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }

    @Override
    public String getColumnName(int i) {
        return COLUMNS[i];
    }

    @Override
    public Class<?> getColumnClass(int i) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int i, int i1) {
        return i1 > 0;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        Person p = persons.get(i);
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
        Person p = persons.get(i);
        switch (i1) {
            case 1: p.setFirstName((String) o); break;
            case 2: p.setName((String) o); break;
            case 3: p.setAddress((String) o); break;
        }
        p.save();
    }

    public void addPerson(Person person) {
        this.persons.add(person);
        this.fireTableDataChanged();
    }

    public void removePerson(Person person) {
        this.persons.remove(person);
        this.fireTableDataChanged();
    }

    public Person findByRow(int i) {
        return this.persons.get(i);
    }
}
