package de.dis2011.gui;

import de.dis2011.data.Entity;
import de.dis2011.data.EstateAgent;
import de.dis2011.model.AgentModel;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;


public class ManagementAgentFrame extends JFrame {
	
	final private MainFrame mainFrame;
    final private AgentModel model = new AgentModel();
    final private JTable table = new JTable();
    
	JFrame pwdFrame = new JFrame("Password Required");
	private final String PASSWORD = "demo";

    public ManagementAgentFrame(MainFrame mainFrame) throws HeadlessException {
        super("Estate Agents");
        this.mainFrame = mainFrame;

        initGui();
        List<Entity> agents = EstateAgent.findAll(EstateAgent.class);
        for (Entity agent : agents) {
            model.add((EstateAgent) agent);
        }
    }

    public void showGui() {
        mainFrame.centerFrame(this);
        setVisible(true);
    }

    private void initGui() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

        table.setModel(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton btnInsert = new JButton("Insert");
        btnInsert.setIcon(mainFrame.createImageIcon("/de/dis2011/icons/user_add.png"));
        btnInsert.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                EstateAgent agent = new EstateAgent();
                agent.save();

                model.add(agent);
            }
        });

        JButton btnRemove = new JButton("Remove");;
        btnRemove.setIcon(mainFrame.createImageIcon("/de/dis2011/icons/user_delete.png"));
        btnRemove.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    EstateAgent agent = model.findByRow(row);
                    if (agent.drop()) {
                        model.remove(agent);
                    }
                }
            }
        });

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPane.add(Box.createHorizontalGlue());
        buttonPane.add(btnInsert);
        buttonPane.add(btnRemove);

        Container contentPane = getContentPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.add(buttonPane, BorderLayout.PAGE_END);

        pack();

        setMinimumSize(new Dimension(800, getHeight()));
    }
    
    public void authenticate() {

		pwdFrame.setSize(600,100);
		JPanel pwdPanel = new JPanel();
		pwdPanel.setLayout(new BorderLayout());
		final JTextField pwd = new JTextField();
		
		//Buttons for password
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		JButton ok = new JButton("Ok");
		JButton cancel = new JButton("Cancel");
		buttonPanel.add(ok);
		buttonPanel.add(cancel);
		
		pwdPanel.add(pwd, BorderLayout.CENTER);
		pwdPanel.add(buttonPanel, BorderLayout.PAGE_END);
		pwdFrame.add(pwdPanel);
		
		pwdFrame.show();
		
		ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				final String password = pwd.getText();

				if (pwd.equals(PASSWORD)){
					showGui();
					pwdFrame.setVisible(false);
				} else {
					String msg = "Login Failed";
					JOptionPane.showMessageDialog(pwdFrame, msg, "Error: Wrong Password", JOptionPane.ERROR_MESSAGE);
					pwd.setText("");
				}		
			}

		});
		
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				pwdFrame.setVisible(false);				
			}

		});
		
	}
	
}
