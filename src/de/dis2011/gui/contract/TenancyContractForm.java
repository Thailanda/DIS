package de.dis2011.gui.contract;

import de.dis2011.data.Entity;
import de.dis2011.data.TenancyContract;
import de.dis2011.data.dao.TenancyContractDao;
import de.dis2011.gui.estate.AbstractForm;
import de.dis2011.gui.management.ContractManagementFrame;
import java.math.BigDecimal;
import java.util.Date;
import javax.swing.JSpinner;
import org.hibernate.SessionFactory;

public class TenancyContractForm extends AbstractForm {
	
	private JSpinner duration;
	private JSpinner additionalCosts;
	private JSpinner date;

	public TenancyContractForm(ContractManagementFrame frame) {
		super(frame, "Tenancy Contract");
	}

	@Override
	protected void buildForm() {

		date = addFormDateElement("Start Date");
		duration = addFormIntElement("Duration");
		additionalCosts = addFormDecimalElement("Additional Costs");
	}

	@Override
	protected void loadForm(Entity entity) {
		TenancyContract contract = (TenancyContract) entity;
		
		duration.setValue(contract.getDuration());
		additionalCosts.setValue(contract.getAdditionalCosts() != null? contract.getAdditionalCosts() : new BigDecimal(0));
		date.setValue(contract.getDate());
	}

	@Override
	public void saveForm(Entity entity) {
		TenancyContract contract = (TenancyContract) entity;
		
		contract.setDuration((Integer) duration.getValue());
		contract.setAdditionalCosts((Double) additionalCosts.getValue());

		Date dateValue = (Date) date.getValue();
		contract.setDate(new java.sql.Date(dateValue.getTime()));

		TenancyContractDao dao = new TenancyContractDao(getSessionFactory());
		dao.save(contract);
	}

	/**
	 * @return current session fac inst
	 * @unschön
	 */
	private SessionFactory getSessionFactory() {
		return ((ContractManagementFrame) frame).getSessionFactory();
	}
}
