package de.dis2011.gui.estate;

import de.dis2011.data.EstateAgent;
import de.dis2011.gui.MainFrame;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import org.hibernate.Query;
import org.hibernate.Session;

public class EstateLogin extends JFrame {

	final private MainFrame mainFrame;
//	final private EstateManagementFrame managementFrame = new EstateManagementFrame();
	
	private final JPasswordField txtFieldPassword;
	

	public EstateLogin(MainFrame mainFrame) {
		super("Please enter Estate Agent Login");
		final JButton btnLogin = new JButton("Login");

		this.mainFrame = mainFrame;

		final JLabel xName = new JLabel("Name");
		final JLabel xPass = new JLabel("Password");
		final JTextField txtFieldName = new JTextField();
		txtFieldPassword = new JPasswordField();
		txtFieldPassword.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent keyEvent) {
				if (keyEvent.getKeyCode() == 10) {
					btnLogin.doClick();
				} else if (keyEvent.getKeyCode() == 27) {
					setVisible(false);
				}
			}
		});

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

	private void login(String estateLogin, String estatePassword) throws SQLException {
		Session session = mainFrame.getSessionFactory().openSession();
		Query query = session.createQuery("from EstateAgent where login=:login AND password=:password");
		query.setString("login", estateLogin);
		query.setString("password", estatePassword);
		EstateAgent a = (EstateAgent) query.uniqueResult();

		if (a != null) {
			mainFrame.getContext().setUser(a);
			mainFrame.getContext().notifyObservers();
			setVisible(false);
			return;
		}

		String msg = "Login was not successful!";
		JOptionPane.showMessageDialog(this, msg, "Error: Could not login", JOptionPane.ERROR_MESSAGE);
		txtFieldPassword.setText("");
	}
}
