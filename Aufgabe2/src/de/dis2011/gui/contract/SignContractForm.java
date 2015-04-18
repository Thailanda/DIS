package de.dis2011.gui.contract;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import de.dis2011.data.Entity;
import de.dis2011.data.Estate;
import de.dis2011.data.Person;
import de.dis2011.data.PurchaseContractEntity;
import de.dis2011.data.TenancyContractEntity;
import de.dis2011.gui.EstateFrame;
import de.dis2011.gui.estate.AbstractForm;

public class SignContractForm extends AbstractForm {

	JComboBox<String> contractTypeC;
	JComboBox<Integer> estatesC;
	JComboBox<Integer> personsC;

	JSpinner date;
	JTextField contractNo;
	JTextField place;

	// Purchse Contract
	JSpinner noInstallments;
	JSpinner interestRate;

	// Tenancy Contract
	JSpinner startDate;
	JSpinner duration;
	JSpinner addtionalCosts;

	public SignContractForm(EstateFrame estateFrame) {
		super(estateFrame, "Sign new Contract");
	}

	@Override
	protected void buildForm() {
		contractTypeC = addFormComboBoxElement(new String[] { "Purchase",
				"Rent" });
		estatesC = addFormComboBoxIntElement(new Integer[] {});
		personsC = addFormComboBoxIntElement(new Integer[] {});
		date = addFormDateElement("Date");
		contractNo = addFormTextElement("Contract No");
		place = addFormTextElement("Place");

		noInstallments = addFormIntElement("No of Installments");
		interestRate = addFormDecimalElement("Interest Rate");

		startDate = addFormDateElement("Start Date");
		duration = addFormIntElement("Duration");
		addtionalCosts = addFormDecimalElement("Additional Costs");

		contractTypeC.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				processTypeSelection();
			}
		});

		preparePurchase();
	}

	@Override
	protected void loadForm(Entity entity) {
		// Nothing to load here
	}

	@Override
	public void saveForm(Entity entity) {
		if (((String) contractTypeC.getSelectedItem()).equals("Purchase")) {
			PurchaseContractEntity contr = new PurchaseContractEntity();

			contr.setId(-1);
			contr.setContractNo((String) contractNo.getText());
			contr.setDate(new java.sql.Date(((java.util.Date) date.getValue())
					.getTime()));
			contr.setPlace((String) place.getText());

			contr.setInterestRate((Double) interestRate.getValue());
			contr.setNoOfInstallments((int) noInstallments.getValue());

			contr.save();
		} else // Should be Rent
		{
			TenancyContractEntity contr = new TenancyContractEntity();

			contr.setId(-1);
			contr.setContractNo((String) contractNo.getText());
			contr.setDate(new java.sql.Date(((java.util.Date) date.getValue())
					.getTime()));
			contr.setPlace((String) place.getText());

			contr.setStartDate(new java.sql.Date(((java.util.Date) startDate
					.getValue()).getTime()));
			contr.setAdditionalCosts((Double) addtionalCosts.getValue());
			contr.setDuration((Integer) duration.getValue());

			contr.save();
		}
		this.setVisible(false);
	}

	private void processTypeSelection() {
		String s = (String) contractTypeC.getSelectedItem();

		if (s.equals("Purchase")) {
			preparePurchase();
		} else // Should be Rent
		{
			prepareRent();
		}
		
		List<Entity> persons = Person.findAll(Person.class);
		personsC.removeAllItems();
		for (Entity e : persons) {
			personsC.addItem(((Person) e).getId());
		}
	}

	private void preparePurchase() {
		ArrayList<Estate> houses = Estate.findByKind("House");
		estatesC.removeAllItems();
		for (Estate e : houses) {
			estatesC.addItem(e.getId());
		}

		startDate.setEnabled(false);
		duration.setEnabled(false);
		addtionalCosts.setEnabled(false);
		noInstallments.setEnabled(true);
		interestRate.setEnabled(true);
	}

	private void prepareRent() {
		ArrayList<Estate> houses = Estate.findByKind("Apartment");
		estatesC.removeAllItems();
		for (Estate e : houses) {
			estatesC.addItem(e.getId());
		}

		startDate.setEnabled(true);
		duration.setEnabled(true);
		addtionalCosts.setEnabled(true);
		noInstallments.setEnabled(false);
		interestRate.setEnabled(false);
	}
}
