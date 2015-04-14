package de.dis2011.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import de.dis2011.gui.estate.EstateLogin;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private EstateLogin estateLoginFrame;

	private PersonFrame personFrame = new PersonFrame();

	public MainFrame() {
		this.setSize(600, 50);
		JPanel listPane = new JPanel();

		this.setLayout(new FlowLayout());

		JButton btnManagementModeAgent = new JButton("Management Estate Agent");
		btnManagementModeAgent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionManagementAgent();
			}
		});
		JButton btnManagementModeEstate = new JButton("Manage Estate");
		btnManagementModeEstate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionMangementEstate();
			}
		});

		JButton btnManagementContract = new JButton("Manage Contract");
		btnManagementContract.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionmManagementContract();
			}
		});

		listPane.add(btnManagementModeAgent);
		listPane.add(btnManagementModeEstate);
		listPane.add(btnManagementContract);

		this.add(listPane);

	}

	public void showGui() {
		setVisible(true);
	}

	private void actionManagementAgent() {
		// TODO Do Stuff
	}

	private void actionMangementEstate() {
		estateLoginFrame = new EstateLogin();
		estateLoginFrame.setVisible(true);
	}

	private void actionmManagementContract() {
		personFrame.setVisible(true);
	}
}
