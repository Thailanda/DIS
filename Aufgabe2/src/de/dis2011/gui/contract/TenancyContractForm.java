package de.dis2011.gui.contract;

import java.math.BigDecimal;

import javax.swing.JSpinner;

import de.dis2011.data.Entity;
import de.dis2011.data.TenancyContractEntity;
import de.dis2011.gui.EstateFrame;
import de.dis2011.gui.estate.AbstractForm;

public class TenancyContractForm extends AbstractForm {
	
	private JSpinner duration;
	private JSpinner additionalCosts;

	public TenancyContractForm(EstateFrame estateFrame) {
		super(estateFrame, "Tenancy Contract");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void buildForm() {
	
		duration = addFormIntElement("Duration");
		additionalCosts = addFormDecimalElement("Additional Costs");
	}

	@Override
	protected void loadForm(Entity entity) {
		TenancyContractEntity contract = (TenancyContractEntity) entity;
		
		duration.setValue(contract.getDuration());
		additionalCosts.setValue(contract.getAdditionalCosts());
	}

	@Override
	public void saveForm(Entity entity) {
		TenancyContractEntity contract = (TenancyContractEntity) entity;
		
		contract.setDuration((Integer) duration.getValue());
		contract.setAdditionalCosts((BigDecimal) additionalCosts.getValue());	
		
		contract.save();
	}

}
