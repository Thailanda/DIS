package de.dis2011.gui.estate;

import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import de.dis2011.data.Entity;
import de.dis2011.gui.EstateFrame;

public class PurchaseContractForm extends AbstractForm {

    private JTextArea startDate;
    private JSpinner duration;
    private JSpinner additionalCosts;

	public PurchaseContractForm(EstateFrame estateFrame) {
		super(estateFrame, "Purchase Contract Form");
	}

	@Override
	protected void buildForm() {
		// TODO
		
	}

	@Override
	protected void loadForm(Entity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveForm(Entity entity) {
		// TODO Auto-generated method stub
		
	}

}
