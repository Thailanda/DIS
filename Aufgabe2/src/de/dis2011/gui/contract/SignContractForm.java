package de.dis2011.gui.contract;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;

import de.dis2011.data.Entity;
import de.dis2011.data.Estate;
import de.dis2011.data.Person;
import de.dis2011.gui.EstateFrame;
import de.dis2011.gui.estate.AbstractForm;

public class SignContractForm extends AbstractForm {

	JComboBox<String> contractTypeC;
	JComboBox<Integer> estatesC;
	JComboBox<Integer> personsC;

	String[] contractTypes = { "Purchase", "Rent" };

	public SignContractForm(EstateFrame estateFrame) {
		super(estateFrame, "Sign new Contract");
	}

	@Override
	protected void buildForm() {
		contractTypeC = addFormComboBoxElement(contractTypes);
		estatesC = addFormComboBoxIntElement(new Integer[] {});
		personsC = addFormComboBoxIntElement(new Integer[] {});

		contractTypeC.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				processTypeSelection();
			}
		});
	}

	@Override
	protected void loadForm(Entity entity) {
		// Nothing to load here
	}

	@Override
	public void saveForm(Entity entity) {
		// TODO Auto-generated method stub

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
		personsC.removeAll();
		for (Entity e: persons)
		{
			personsC.addItem(((Person)e).getId());
		}
	}

	private void preparePurchase() {
		ArrayList<Estate> houses = Estate.findByKind("House");
		estatesC.removeAll();
		for (Estate e: houses)
		{
			estatesC.addItem(e.getId());
		}
	}
	
	private void prepareRent() {
		ArrayList<Estate> houses = Estate.findByKind("Apartment");
		estatesC.removeAll();
		for (Estate e: houses)
		{
			estatesC.addItem(e.getId());
		}
	}
}
