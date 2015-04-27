package de.dis2011.model;

import de.dis2011.data.Apartment;
import de.dis2011.data.Estate;
import de.dis2011.data.House;
import de.dis2011.data.dao.ApartmentDao;
import de.dis2011.data.dao.HouseDao;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-14
 */
public class EstateModel extends EntityModel<Estate> {

    final private static String[] COLUMNS = {"ID", "Street", "Street Number", "City", "Postal Code", "Square Area", "Kind"};
    final private ApartmentDao _apartmentDao;
    final private HouseDao _houseDao;

    public EstateModel(ApartmentDao apartmentDao, HouseDao houseDao) {
		_apartmentDao = apartmentDao;
		_houseDao = houseDao;
	}

	@Override
    public boolean isCellEditable(int row, int column) {
        return super.isCellEditable(row, column) && column != 6;
    }

    @Override
    public Class<?> getColumnClass(int column) {
        if (column == 5) {
            return Integer.class;
        }

        return super.getColumnClass(column);
    }

    @Override
    public Object getValueAt(int i, int i1) {
        Estate p = findByRow(i);
        switch (i1) {
            case 0: return p.getId();
            case 1: return p.getStreet();
            case 2: return p.getStreetNumber();
            case 3: return p.getCity();
            case 4: return p.getPostalCode();
            case 5: return p.getSquareArea();
            case 6: return p.getKind();
        }
        return null;
    }

    @Override
    public void setValueAt(Object o, int i, int i1) {
        Estate p = findByRow(i);
        switch (i1) {
            case 1: p.setStreet((String) o); break;
            case 2: p.setStreetNumber((String) o); break;
            case 3: p.setCity((String) o); break;
            case 4: p.setPostalCode((String) o); break;
            case 5: p.setSquareArea((Integer) o); break;
        }
        
        if (p instanceof Apartment){
        	_apartmentDao.save((Apartment) p);
        }
        else if (p instanceof House){
        	_houseDao.save((House) p);
        }
    }

    @Override
    protected String[] getColumns() {
        return COLUMNS;
    }
    
}
