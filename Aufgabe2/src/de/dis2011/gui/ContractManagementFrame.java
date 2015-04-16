package de.dis2011.gui;

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

import de.dis2011.data.Apartment;
import de.dis2011.data.Contract;
import de.dis2011.data.Entity;
import de.dis2011.data.Estate;
import de.dis2011.data.House;
import de.dis2011.data.PurchaseContractEntity;
import de.dis2011.data.TenancyContractEntity;
import de.dis2011.gui.contract.PurchaseContractForm;
import de.dis2011.gui.contract.SignContractForm;
import de.dis2011.gui.contract.TenancyContractForm;
import de.dis2011.gui.estate.ApartmentForm;
import de.dis2011.gui.estate.HouseForm;
import de.dis2011.model.ContractModel;
import de.dis2011.model.HouseDataModel;

public class ContractManagementFrame extends JFrame {
	private ContractModel model = new ContractModel();
    private JTable table = new JTable();
    
    private SignContractForm signContractForm = new SignContractForm(null);

	public ContractManagementFrame(MainFrame main) {
		super("Contracts");

		initGUI(); 
		
		List<Contract> contracts = Contract.findAllContracts();
        for (Entity contract : contracts) {
            model.add((Contract) contract);
        }
	}

	public void showGui() {
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

		JButton btnInsert = new JButton("Sign Contract");
		btnInsert.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				signContract();
			}
		});

		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				removeContract();
			}
		});
		
        JButton btnEdit = new JButton("Edit");
//        btnEdit.setIcon(mainFrame.createImageIcon("/de/dis2011/icons/pencil.png"));
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
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		buttonPane.add(Box.createHorizontalGlue());
		buttonPane.add(btnEdit);
		buttonPane.add(btnInsert);
		buttonPane.add(btnRemove);

		Container contentPane = getContentPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		contentPane.add(buttonPane, BorderLayout.PAGE_END);

		pack();
	}
	    
    private void signContract() {
    	signContractForm.showGui();
    }
    
    private void removeContract() {
    	Contract c = model.findByRow(table.getSelectedRow());
        if (c.drop()) {
            model.remove(c);
        }
    }
    

    private void editContract(Contract contract) {
        if (contract instanceof TenancyContractEntity) {
            TenancyContractForm form = new TenancyContractForm(null);
            form.setEntity(contract);
            form.showGui();
        }
        else if (contract instanceof PurchaseContractEntity) {
            PurchaseContractForm form = new PurchaseContractForm(null);
            form.setEntity(contract);
            form.showGui();
        }
    }
}
