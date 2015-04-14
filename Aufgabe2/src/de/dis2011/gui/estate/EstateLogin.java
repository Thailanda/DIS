package de.dis2011.gui.estate;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import de.dis2011.data.EstateAgent;

public class EstateLogin extends JFrame {

	public EstateLogin() {
		final JLabel xName = new JLabel("Name");
		final JLabel xPass = new JLabel("Password");
		
		this.setSize(600, 60);
		this.setTitle("Please enter Estate Agent Login");

		final JTextArea txtAreaName = new JTextArea(1, 20);
		final JTextArea txtAreaPassword = new JTextArea(1, 20);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					login(txtAreaName.getText(), txtAreaPassword.getText());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		this.setLayout(new FlowLayout());
		
		this.add(xName);
		this.add(txtAreaName);
		
		this.add(xPass);		
		this.add(txtAreaPassword);
		
		this.add(btnLogin);
	}

	private void login(String estateID, String estatePassword) throws SQLException {
		EstateAgent a = new EstateAgent();

		if (a.verifyLogin(estateID, estatePassword)) {
			// If Login Successfull, show next Window
			EstateManagement man = new EstateManagement();
			man.setVisible(true);
		}
		
		
	}
}