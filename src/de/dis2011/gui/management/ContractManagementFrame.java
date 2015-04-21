package de.dis2011.gui.management;

import de.dis2011.data.Contract;
import de.dis2011.data.Entity;
import de.dis2011.data.PurchaseContract;
import de.dis2011.data.TenancyContract;
import de.dis2011.gui.MainFrame;
import de.dis2011.gui.contract.PurchaseContractForm;
import de.dis2011.gui.contract.SignContractForm;
import de.dis2011.gui.contract.TenancyContractForm;
import de.dis2011.model.ContractModel;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ContractManagementFrame extends JFrame {
	private ContractModel model = new ContractModel();
    private JTable table = new JTable();
    
	final private MainFrame mainFrame;

	public ContractManagementFrame(MainFrame mainFrame) {
		super("Contracts");

		this.mainFrame = mainFrame;

		initGUI(); 
		
		List<Contract> contracts = Contract.findAllContracts();
        for (Entity contract : contracts) {
            model.add((Contract) contract);
        }
	}

	public void showGui() {
		mainFrame.centerFrame(this);
		this.setVisible(true);
	}

	private void initGUI() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		setMinimumSize(new Dimension(800, 600));
		setLocation(screenSize.width / 2 - getSize().width / 2,
				screenSize.height / 2 - getSize().height / 2);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

		table.setModel(model);
		JScrollPane scrollPane = new JScrollPane(table);

		JButton btnSignContract = new JButton("Sign Contract");
		btnSignContract.setIcon(mainFrame.createImageIcon("/de/dis2011/icons/script_add.png"));
		btnSignContract.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				signContract();
			}
		});

		JButton btnRemove = new JButton("Remove");
		btnRemove.setIcon(mainFrame.createImageIcon("/de/dis2011/icons/delete.png"));
		btnRemove.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				removeContract();
			}
		});
		
        JButton btnEdit = new JButton("Edit");
        btnEdit.setIcon(mainFrame.createImageIcon("/de/dis2011/icons/pencil.png"));
        btnEdit.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				int row = table.getSelectedRow();
				if (row >= 0) {
					Contract contract = model.findByRow(row);
					editContract(contract);
				}
			}
		});

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		buttonPane.add(btnSignContract);
		buttonPane.add(Box.createHorizontalGlue());
		buttonPane.add(btnEdit);
		buttonPane.add(btnRemove);

		Container contentPane = getContentPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		contentPane.add(buttonPane, BorderLayout.PAGE_END);

		pack();
	}
	    
    private void signContract() {
		final SignContractForm signContractForm = new SignContractForm(this);
    	signContractForm.showGui();
    }
    
    private void removeContract() {
    	Contract c = model.findByRow(table.getSelectedRow());
        if (c.drop()) {
            model.remove(c);
        }
    }


    private void editContract(Contract contract) {
        if (contract instanceof TenancyContract) {
            TenancyContractForm form = new TenancyContractForm(this);
            form.setEntity(contract);
            form.showGui();
        }
        else if (contract instanceof PurchaseContract) {
            PurchaseContractForm form = new PurchaseContractForm(this);
            form.setEntity(contract);
            form.showGui();
        }
    }

	public ContractModel getModel() {
		return model;
	}
}
