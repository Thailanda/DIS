package de.dis2011.gui.contract;

import de.dis2011.data.Entity;
import de.dis2011.data.PurchaseContract;
import de.dis2011.data.dao.PurchaseContractDao;
import de.dis2011.gui.estate.AbstractForm;
import de.dis2011.gui.management.ContractManagementFrame;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import org.hibernate.SessionFactory;

public class PurchaseContractForm extends AbstractForm {

	private JSpinner noInstallments;
	private JSpinner interestRate;
	private JSpinner startDate;

	public PurchaseContractForm(JFrame frame) {
		super(frame, "Purchase Contract Form");
	}

	@Override
	protected void buildForm() {
		noInstallments = addFormIntElement("No. of Installments");
		interestRate = addFormDecimalElement("Interest Rate");
		startDate = addFormDateElement("Start Date");
	}

	@Override
	protected void loadForm(Entity entity) {
		PurchaseContract ent = (PurchaseContract) entity;

		noInstallments.setValue((int) ent.getNoOfInstallments());
		interestRate.setValue((Double) ent.getInterestRate());

		startDate.setValue(ent.getDate());
	}

	@Override
	public void saveForm(Entity entity) {
		PurchaseContract ent = (PurchaseContract) entity;

		ent.setNoOfInstallments((int) noInstallments.getValue());
		ent.setInterestRate((Double) interestRate.getValue());

		Date startDateValue = (Date) startDate.getValue();
		ent.setDate(new java.sql.Date(startDateValue.getTime()));

		PurchaseContractDao dao = new PurchaseContractDao();
		dao.save(ent);
	}

	/**
	 * @return current session fac inst
	 */
	private SessionFactory getSessionFactory() {
		return ((ContractManagementFrame) frame).getSessionFactory();
	}
}
