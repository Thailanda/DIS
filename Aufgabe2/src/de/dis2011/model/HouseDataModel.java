package de.dis2011.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import de.dis2011.data.House;

public class HouseDataModel extends AbstractTableModel {

	final private static String[] COLUMNS = { "ID", "City", "Postal Code",
			"Street", "Street Number", "Square Area", "Floors", "Price",
			"Garden" };
	final private List<House> houses;

	public HouseDataModel() {
		houses = new ArrayList<House>();
	}

	@Override
	public int getRowCount() {
		return houses.size();
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
		House p = houses.get(i);
		switch (i1) {
		case 0:
			return p.getId();
		case 1:
			return p.getCity();
		case 2:
			return p.getPostalCode();
		case 3:
			return p.getStreet();
		case 4:
			return p.getStreetNumber();
		case 5:
			return p.getSquareArea();
		case 6:
			return p.getFloors();
		case 7:
			return p.getPrice();
		case 8:
			return p.hasGarden();
		}
		return null;
	}

	@Override
	public void setValueAt(Object o, int i, int i1) {
		House p = houses.get(i);
		switch (i1) {
		case 1:
			p.setCity((String) o);
			break;
		case 2:
			p.setPostalCode((String) o);
			break;
		case 3:
			p.setStreet((String) o);
			break;
		case 4:
			p.setStreetNumber((String) o);
			break;
		case 5:
			p.setSquareArea((Integer) o);
			break;
		case 6:
			p.setStreet((String) o);
			break;
		case 7:
			p.setPrice((double) o);
			break;
		case 8:
			p.setGarden((boolean) o);
			break;
		}
		p.save();
	}

	public void addHouse(House house) {
		this.houses.add(house);
		this.fireTableDataChanged();
	}

	public void removeHouse(House house) {
		this.houses.remove(house);
		this.fireTableDataChanged();
	}

	public House findByRow(int i) {
		return this.houses.get(i);
	}
}
