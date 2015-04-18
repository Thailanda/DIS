package de.dis2011.gui.contract;

import de.dis2011.data.Entity;
import de.dis2011.data.TenancyContract;
import de.dis2011.gui.estate.AbstractForm;
import java.math.BigDecimal;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JSpinner;

public class TenancyContractForm extends AbstractForm {
	
	private JSpinner duration;
	private JSpinner additionalCosts;
	private JSpinner date;

	public TenancyContractForm(JFrame frame) {
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
		contract.setAdditionalCosts((Double)additionalCosts.getValue());

		Date dateValue = (Date) date.getValue();
		contract.setDate(new java.sql.Date(dateValue.getTime()));
		
		contract.save();
	}
}
