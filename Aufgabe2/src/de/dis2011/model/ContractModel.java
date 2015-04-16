package de.dis2011.model;

import de.dis2011.data.Contract;
import de.dis2011.data.House;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-14
 */
public class ContractModel extends EntityModel<Contract> {

    final private static String[] COLUMNS = {"ID", "Contract No", "Date", "Place"};
	final private List<Contract> contracts;
	
	public ContractModel() {
		contracts = new ArrayList<Contract>();
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
        p.save();
    }

    @Override
    protected String[] getColumns() {
        return COLUMNS;
    }
    
    public void addContract(Contract contract)
    {
    	contracts.add(contract);
    }
    
	public void removeContract(Contract c) {
		this.contracts.remove(c);
		this.fireTableDataChanged();
	}
}
