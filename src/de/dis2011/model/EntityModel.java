package de.dis2011.model;

import de.dis2011.data.Entity;
import de.dis2011.data.dao.Dao;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-14
 */
abstract public class EntityModel<T extends Entity> extends AbstractTableModel {

    final private List<T> entities;
    protected Dao<T> dao;

    abstract protected String[] getColumns();

    public EntityModel() {
        entities = new ArrayList<T>();
    }

    @Override
    public int getRowCount() {
        return entities.size();
    }

    @Override
    final public int getColumnCount() {
        return getColumns().length;
    }

    @Override
    final public String getColumnName(int i) {
        return getColumns()[i];
    }

    @Override
    public Class<?> getColumnClass(int i) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int i, int i1) {
        return i1 > 0;
    }

    public void setDao(Dao<T> dao) {
        this.dao = dao;
    }

    /**
     * Clears the table model.
     */
    public void clear() {
        entities.clear();
        fireTableDataChanged();
    }

    /**
     * Adds an entity to the table model.
     */
    public void add(T entity) {
        entities.add(entity);
        fireTableDataChanged();
    }

    /**
     * Adds all entities to the table model.
     */
    public void addAll(Collection<T> entities) {
        this.entities.addAll(entities);
        fireTableDataChanged();
    }

    /**
     * Removes an entity from the table model.
     */
    public void remove(T entity) {
        entities.remove(entity);
        fireTableDataChanged();
    }

    /**
     * Finds an entity by the row index.
     */
    public T findByRow(int i) {
        if (i < 0) {
            return null;
        }

        return entities.get(i);
    }
}
