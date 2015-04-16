package de.dis2011.gui.contract;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JSpinner;

import de.dis2011.data.Entity;
import de.dis2011.data.PurchaseContractEntity;
import de.dis2011.gui.EstateFrame;
import de.dis2011.gui.estate.AbstractForm;

public class PurchaseContractForm extends AbstractForm {

	private JSpinner noInstallments;
	private JSpinner interestRate;
	private JSpinner startDate;

	public PurchaseContractForm(EstateFrame estateFrame) {
		super(estateFrame, "Purchase Contract Form");
	}

	@Override
	protected void buildForm() {
		noInstallments = addFormIntElement("No. of Installments");
		interestRate = addFormDecimalElement("Interest Rate");
		startDate = addFormDateElement("Start Date");
	}

	@Override
	protected void loadForm(Entity entity) {
		PurchaseContractEntity ent = (PurchaseContractEntity) entity;

		noInstallments.setValue((int) ent.getNoOfInstallments());
		interestRate.setValue((Double) ent.getInterestRate());

		startDate.setValue(new java.util.Date(((Date) startDate.getValue()).getTime()));
	}

	@Override
	public void saveForm(Entity entity) {
		PurchaseContractEntity ent = (PurchaseContractEntity) entity;

		ent.setNoOfInstallments((int) noInstallments.getValue());
		ent.setInterestRate((Double) interestRate.getValue());
		ent.setDate((Date) startDate.getValue());

		ent.save();
	}

}
