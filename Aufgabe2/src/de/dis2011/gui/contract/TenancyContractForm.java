package de.dis2011.gui.contract;

import java.math.BigDecimal;
import java.sql.Date;

import javax.swing.JSpinner;

import de.dis2011.data.Entity;
import de.dis2011.data.TenancyContractEntity;
import de.dis2011.gui.EstateFrame;
import de.dis2011.gui.estate.AbstractForm;

public class TenancyContractForm extends AbstractForm {
	
	private JSpinner duration;
	private JSpinner additionalCosts;
	private JSpinner startDate;

	public TenancyContractForm(EstateFrame estateFrame) {
		super(estateFrame, "Tenancy Contract");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void buildForm() {

		startDate = addFormDateElement("Start Date");
		duration = addFormIntElement("Duration");
		additionalCosts = addFormDecimalElement("Additional Costs");
	}

	@Override
	protected void loadForm(Entity entity) {
		TenancyContractEntity contract = (TenancyContractEntity) entity;
		
		duration.setValue(contract.getDuration());
		additionalCosts.setValue(contract.getAdditionalCosts() != null? contract.getAdditionalCosts() : new BigDecimal(0));
		startDate.setValue(contract.getDate());
	}

	@Override
	public void saveForm(Entity entity) {
		TenancyContractEntity contract = (TenancyContractEntity) entity;
		
		contract.setDuration((Integer) duration.getValue());
		contract.setAdditionalCosts((Double)additionalCosts.getValue());
		contract.setDate((Date) startDate.getValue());
		
		contract.save();
	}

}
