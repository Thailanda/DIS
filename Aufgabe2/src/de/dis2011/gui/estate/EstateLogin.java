package de.dis2011.gui.estate;

import de.dis2011.data.EstateAgent;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EstateLogin extends JFrame {

	public EstateLogin() {
		final JLabel xName = new JLabel("Name");
		final JLabel xPass = new JLabel("Password");
		
		this.setSize(600, 60);
		this.setTitle("Please enter Estate Agent Login");

		final JTextField txtFieldName = new JTextField();
		final JTextField txtFieldPassword = new JTextField();

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					login(txtFieldName.getText(), txtFieldPassword.getText());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		buttonPane.add(xName);
		buttonPane.add(txtFieldName);
		buttonPane.add(xPass);
		buttonPane.add(txtFieldPassword);
		buttonPane.add(btnLogin);

		Container contentPane = getContentPane();
		contentPane.add(buttonPane, BorderLayout.CENTER);
	}

	private void login(String estateID, String estatePassword) throws SQLException {
		EstateAgent a = new EstateAgent();

		if (a.verifyLogin(estateID, estatePassword)) {
			// If Login Successful, show next Window
			EstateManagement man = new EstateManagement();
			man.setVisible(true);
		}
	}
}
