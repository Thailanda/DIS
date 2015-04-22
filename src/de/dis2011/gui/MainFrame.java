package de.dis2011.gui;

import de.dis2011.gui.estate.EstateLogin;
import de.dis2011.gui.management.EstateAgentManagementFrame;
import de.dis2011.gui.management.ContractManagementFrame;
import de.dis2011.gui.management.EstateManagementFrame;
import de.dis2011.gui.management.PersonManagementFrame;
import de.dis2011.model.EstateAgentSecurityContext;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.hibernate.SessionFactory;

public class MainFrame extends JFrame implements Observer {
	private static final long serialVersionUID = 1L;
	public static final String TITLE = "Estate Agent Software";

	// Owned frames
	private final EstateAgentSecurityContext context;
	private final PersonManagementFrame personFrame;
	private final EstateManagementFrame estateFrame;
	private final EstateLogin estateLoginFrame;
	private final ContractManagementFrame contractFrame;
	private final EstateAgentManagementFrame agentFrame;

	// Hibernate session factory
	private final SessionFactory sessionFactory;

	// GUI elements
	private final JButton btnPersonManagement;
	private final JButton btnAuthenticate;
	private final JButton btnManageEstates;
	private final JButton btnManageContracts;

	public MainFrame(SessionFactory sessionFactory) {
		super(TITLE);

		this.sessionFactory = sessionFactory;
		setDefaultCloseOperation(MainFrame.EXIT_ON_CLOSE);
		getRootPane().putClientProperty("apple.awt.brushMetalLook", true);

		context = new EstateAgentSecurityContext(this);
		personFrame = new PersonManagementFrame(this);
		estateFrame = new EstateManagementFrame(this);
		estateLoginFrame = new EstateLogin(this);
		contractFrame = new ContractManagementFrame(this);
		agentFrame = new EstateAgentManagementFrame(this);

		btnAuthenticate = new JButton("Authenticate");
		btnAuthenticate.setIcon(createImageIcon("/de/dis2011/icons/key.png"));
		btnAuthenticate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionManagementEstate();
			}
		});
		btnAuthenticate.putClientProperty("JButton.buttonType", "roundRect");
		btnAuthenticate.putClientProperty("JButton.segmentPosition", "first");
		btnAuthenticate.setHorizontalTextPosition(JButton.CENTER);
		btnAuthenticate.setVerticalTextPosition(JButton.BOTTOM);

		btnManageEstates = new JButton("Manage Estates");
		btnManageEstates.setIcon(createImageIcon("/de/dis2011/icons/house.png"));
		btnManageEstates.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionManagementAgent();
			}
		});
		btnManageEstates.putClientProperty("JButton.buttonType", "roundRect");
		btnManageEstates.putClientProperty("JButton.segmentPosition", "middle");
		btnManageEstates.setHorizontalTextPosition(JButton.CENTER);
		btnManageEstates.setVerticalTextPosition(JButton.BOTTOM);

		btnPersonManagement = new JButton("Manage Persons");
		btnPersonManagement.setIcon(createImageIcon("/de/dis2011/icons/user_edit.png"));
		btnPersonManagement.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionManagePersons();
			}
		});
		btnPersonManagement.putClientProperty("JButton.buttonType", "roundRect");
		btnPersonManagement.putClientProperty("JButton.segmentPosition", "middle");
		btnPersonManagement.setHorizontalTextPosition(JButton.CENTER);
		btnPersonManagement.setVerticalTextPosition(JButton.BOTTOM);

		btnManageContracts = new JButton("Manage Contracts");
		btnManageContracts.setIcon(createImageIcon("/de/dis2011/icons/script_edit.png"));
		btnManageContracts.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionManageContracts();
			}
		});
		btnManageContracts.putClientProperty("JButton.buttonType", "roundRect");
		btnManageContracts.putClientProperty("JButton.segmentPosition", "middle");
		btnManageContracts.setHorizontalTextPosition(JButton.CENTER);
		btnManageContracts.setVerticalTextPosition(JButton.BOTTOM);

		JButton btnManageEstateAgents = new JButton("Manage Estate Agents");
		btnManageEstateAgents.setIcon(createImageIcon("/de/dis2011/icons/key_add.png"));
		btnManageEstateAgents.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionManageAgents();
			}
		});
		btnManageEstateAgents.putClientProperty("JButton.buttonType", "roundRect");
		btnManageEstateAgents.putClientProperty("JButton.segmentPosition", "last");
		btnManageEstateAgents.setHorizontalTextPosition(JButton.CENTER);
		btnManageEstateAgents.setVerticalTextPosition(JButton.BOTTOM);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		buttonPane.add(Box.createHorizontalGlue());
		buttonPane.add(btnAuthenticate);
		buttonPane.add(Box.createRigidArea(new Dimension(5, 0)));
		buttonPane.add(btnManageEstates);
		buttonPane.add(Box.createRigidArea(new Dimension(5, 0)));
		buttonPane.add(btnPersonManagement);
		buttonPane.add(Box.createRigidArea(new Dimension(5, 0)));
		buttonPane.add(btnManageContracts);
		buttonPane.add(Box.createRigidArea(new Dimension(5, 0)));
		buttonPane.add(btnManageEstateAgents);
		buttonPane.add(Box.createHorizontalGlue());

		Container contentPane = getContentPane();
		contentPane.add(buttonPane, BorderLayout.CENTER);

		pack();

		setMinimumSize(getSize());

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(screenSize.width / 2 - getSize().width / 2, screenSize.height / 2 - getSize().height / 2);

		update(context, null);
	}

	public void centerFrame(JFrame frame) {
		frame.setLocation(getX() + getWidth() / 2 - frame.getWidth() / 2, getY() + getHeight() / 2 - frame.getHeight()
				/ 2);
	}

	public void showGui() {
		setVisible(true);
	}

	public ImageIcon createImageIcon(String path) {
		URL imgURL = getClass().getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, path);
		}

		return null;
	}

	public EstateAgentSecurityContext getContext() {
		return context;
	}

	@Override
	public void update(Observable observable, Object o) {
		if (observable == context) {
			boolean authenticated = context.isAuthenticated();

			btnAuthenticate.setEnabled(!authenticated);
			btnManageEstates.setEnabled(authenticated);
			btnPersonManagement.setEnabled(authenticated);
			btnManageContracts.setEnabled(authenticated);

			if (authenticated) {
				setTitle(TITLE + " - [" + context.getUser().getName() + " #"+ context.getUser().getId() + "]");
			} else {
				setTitle(TITLE);
			}
		}
	}

	private void actionManagementAgent() {
		estateFrame.showGui();
	}

	private void actionManagementEstate() {
		estateLoginFrame.showGui();
	}

	private void actionManagePersons() {
		personFrame.showGui();
	}
	
	protected void actionManageContracts() {
		 contractFrame.showGui();
		
	}
	
	protected void actionManageAgents() {
		agentFrame.authenticate();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
