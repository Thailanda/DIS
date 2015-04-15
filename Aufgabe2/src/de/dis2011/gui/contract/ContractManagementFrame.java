package de.dis2011.gui.contract;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import de.dis2011.data.Contract;
import de.dis2011.data.House;
import de.dis2011.gui.MainFrame;
import de.dis2011.model.ContractModel;
import de.dis2011.model.HouseDataModel;

public class ContractManagementFrame extends JFrame {
	private ContractModel model = new ContractModel();
    private JTable table = new JTable();
    
    private SignContractForm signContractForm = new SignContractForm(null);

	public ContractManagementFrame(MainFrame main) {
		super("Contracts");

		initGUI();
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
				saveContract();
			}
		});

		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				removeContract();
			}
		});

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		buttonPane.add(Box.createHorizontalGlue());
		buttonPane.add(btnInsert);
		buttonPane.add(btnRemove);

		Container contentPane = getContentPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		contentPane.add(buttonPane, BorderLayout.PAGE_END);

		pack();
	}
	    
    private void saveContract() {
    	signContractForm.showGui();
    }
    
    private void removeContract() {
    	Contract c = model.findByRow(table.getSelectedRow());
        if (c.drop()) {
            model.removeContract(c);
        }
    }
}
