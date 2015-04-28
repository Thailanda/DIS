package de.dis2011.model;

import de.dis2011.data.Contract;
import de.dis2011.data.PurchaseContract;
import de.dis2011.data.TenancyContract;
import de.dis2011.data.dao.PurchaseContractDao;
import de.dis2011.data.dao.TenancyContractDao;
import java.sql.Date;
import org.hibernate.SessionFactory;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-14
 */
public class ContractModel extends EntityModel<Contract> {

    final private static String[] COLUMNS = {"ID", "Contract No", "Date", "Place"};

    final private TenancyContractDao tenancyContractDao;
    final private PurchaseContractDao purchaseContractDao;

	public ContractModel(SessionFactory factory) {
        tenancyContractDao = new TenancyContractDao(factory);
        purchaseContractDao = new PurchaseContractDao(factory);
    }

    @Override
    public Object getValueAt(int i, int i1) {
        Contract p = findByRow(i);
        switch (i1) {
            case 0: return p.getId();
            case 1: return p.getContractNo();
            case 2: return p.getDate();
            case 3: return p.getPlace();
        }
        return null;
    }

    @Override
    public void setValueAt(Object o, int i, int i1) {
        Contract p = findByRow(i);
        switch (i1) {
            case 1: p.setContractNo((String) o); break;
            case 2: p.setDate((Date) o); break;
            case 3: p.setPlace((String) o); break;
        }

        if (p instanceof TenancyContract) {
            tenancyContractDao.save((TenancyContract) p);
        } else if (p instanceof PurchaseContract) {
            purchaseContractDao.save((PurchaseContract) p);
        }
    }

    @Override
    protected String[] getColumns() {
        return COLUMNS;
    }
}
