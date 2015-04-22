package de.dis2011.gui.contract;

import de.dis2011.data.Apartment;
import de.dis2011.data.Contract;
import de.dis2011.data.Entity;
import de.dis2011.data.Estate;
import de.dis2011.data.House;
import de.dis2011.data.Person;
import de.dis2011.data.PurchaseContract;
import de.dis2011.data.TenancyContract;
import de.dis2011.data.dao.PersonDao;
import de.dis2011.gui.estate.AbstractForm;
import de.dis2011.gui.management.ContractManagementFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTextField;

public class SignContractForm extends AbstractForm {

	private final PersonDao personDao;
	private JComboBox<String> contractTypeChooser;
	private JComboBox<Object> estatesChooser;
	private JComboBox<Object> personsChooser;

	private JSpinner date;
	private JTextField contractNo;
	private JTextField place;

	// Purchase Contract
	private JSpinner noInstallments;
	private JSpinner interestRate;

	// Tenancy Contract
	private JSpinner startDate;
	private JSpinner duration;
	private JSpinner additionalCosts;

	final ContractManagementFrame contractManagementFrame;

	public SignContractForm(ContractManagementFrame frame) {
		super(frame, "Sign new Contract");
		contractManagementFrame = frame;
		this.personDao = new PersonDao(frame.getSessionFactory());
	}

	@Override
	protected void buildForm() {
		// Persons
		personsChooser = addFormComboBoxElement("Person", new Object[]{});
		loadPersons();
		personsChooser.setSelectedIndex(0);

		// Contract Types
		contractTypeChooser = addFormComboBoxElement("Contract Type", new String[] { "Purchase", "Rent" });
		contractTypeChooser.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				processTypeSelection();
			}
		});

		// Estates
		estatesChooser = addFormComboBoxElement("Estate", new Object[]{});

		date = addFormDateElement("Date");
		contractNo = addFormTextElement("Contract No");
		place = addFormTextElement("Place");

		noInstallments = addFormIntElement("No of Installments");
		interestRate = addFormDecimalElement("Interest Rate");

		startDate = addFormDateElement("Start Date");
		duration = addFormIntElement("Duration");
		additionalCosts = addFormDecimalElement("Additional Costs");

		contractTypeChooser.setSelectedIndex(0);
	}

	@Override
	protected void loadForm(Entity entity) {
		// Nothing to load here
	}

	@Override
	public void saveForm(Entity entity) {
		Contract contract;

		if (contractTypeChooser.getSelectedItem().equals("Purchase")) {
			contract = new PurchaseContract();
		} else {
			contract = new TenancyContract();
		}

		contract.setContractNo(contractNo.getText());
		Date dateValue = (Date) date.getValue();
		contract.setDate(new java.sql.Date(dateValue.getTime()));
		contract.setPlace(place.getText());


		if (contract instanceof PurchaseContract) {
			PurchaseContract purchaseContract = (PurchaseContract) contract;

			purchaseContract.setInterestRate((Double) interestRate.getValue());
			purchaseContract.setNoOfInstallments((int) noInstallments.getValue());

		} else  {
			// Should be Rent
			TenancyContract tenancyContract = (TenancyContract) contract;

			Date startDateValue = (Date) startDate.getValue();
			tenancyContract.setStartDate(new java.sql.Date(startDateValue.getTime()));
			tenancyContract.setAdditionalCosts((Double) additionalCosts.getValue());
			tenancyContract.setDuration((Integer) duration.getValue());
		}

		contract.save();
		// Get Person
		Person person = (Person) personsChooser.getSelectedItem();

		Estate estate = (Estate) estatesChooser.getSelectedItem();
		estate.setPerson(person);
		estate.setContract(contract);
		estate.save();

		contractManagementFrame.getModel().add(contract);
		this.setVisible(false);
	}

	private void processTypeSelection() {
		String s = (String) contractTypeChooser.getSelectedItem();

		if (s.equals("Purchase")) {
			preparePurchase();
		} else {
			// Should be Rent
			prepareRent();
		}
	}

	private void loadPersons() {
		List<Person> persons = personDao.findAll();
		for (Person person : persons) {
			personsChooser.addItem(person);
		}
	}

	private void preparePurchase() {
		List<House> houses = House.findAll();
		estatesChooser.removeAllItems();
		for (House e : houses) {
			estatesChooser.addItem(e);
		}

		startDate.setEnabled(false);
		duration.setEnabled(false);
		additionalCosts.setEnabled(false);
		noInstallments.setEnabled(true);
		interestRate.setEnabled(true);
	}

	private void prepareRent() {
		List<Apartment> houses = Apartment.findAll();
		estatesChooser.removeAllItems();
		for (Apartment e : houses) {
			estatesChooser.addItem(e);
		}

		startDate.setEnabled(true);
		duration.setEnabled(true);
		additionalCosts.setEnabled(true);
		noInstallments.setEnabled(false);
		interestRate.setEnabled(false);
	}
}
