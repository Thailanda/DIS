package de.dis2011.gui.estate;

import de.dis2011.data.EstateAgent;
import de.dis2011.gui.MainFrame;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class EstateLogin extends JFrame {

	final private MainFrame mainFrame;
	private final JPasswordField txtFieldPassword;

	public EstateLogin(MainFrame mainFrame) {
		super("Please enter Estate Agent Login");

		this.mainFrame = mainFrame;

		final JLabel xName = new JLabel("Name");
		final JLabel xPass = new JLabel("Password");
		final JTextField txtFieldName = new JTextField();
		txtFieldPassword = new JPasswordField();

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					login(txtFieldName.getText(), new String(txtFieldPassword.getPassword()));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		buttonPane.add(xName);
		buttonPane.add(txtFieldName);
		buttonPane.add(xPass);
		buttonPane.add(txtFieldPassword);
		buttonPane.add(btnLogin);

		Container contentPane = getContentPane();
		contentPane.add(buttonPane, BorderLayout.CENTER);

		pack();

		setMinimumSize(new Dimension(600, getHeight()));
	}

	public void showGui() {
		mainFrame.centerFrame(this);
		setVisible(true);
	}

	private void login(String estateID, String estatePassword) throws SQLException {
		EstateAgent a = new EstateAgent();

		if (a.verifyLogin(estateID, estatePassword)) {
			mainFrame.getContext().setUser(a);
			mainFrame.getContext().notifyObservers();
			setVisible(false);
		} else {
			String msg = "Login was not successful!";
			JOptionPane.showMessageDialog(this, msg, "Error: Could not login", JOptionPane.ERROR_MESSAGE);
			txtFieldPassword.setText("");
		}
	}
}