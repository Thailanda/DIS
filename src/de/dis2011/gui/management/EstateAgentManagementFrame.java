package de.dis2011.gui.management;

import com.google.inject.Inject;
import de.dis2011.data.EstateAgent;
import de.dis2011.data.dao.EstateAgentDao;
import de.dis2011.gui.MainFrame;
import de.dis2011.model.EstateAgentModel;

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
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;



public class EstateAgentManagementFrame extends JFrame {

    private final EstateAgentDao estateAgentDao;

    final private MainFrame mainFrame;
    final private EstateAgentModel model = new EstateAgentModel();
    final private JTable table = new JTable();

    JFrame pwdFrame = new JFrame("Password Required");
    private final String PASSWORD = "demo";
    private EstateAgent _agent;

    JFrame loginFrame = new JFrame("New Login Required");

    @Inject
    public EstateAgentManagementFrame(MainFrame mainFrame, EstateAgentDao estateAgentDao) throws HeadlessException {
        super("Estate Agents");
        this.mainFrame = mainFrame;
        this.estateAgentDao = estateAgentDao;

        model.setDao(estateAgentDao);
        
        List<EstateAgent> agents = estateAgentDao.findAll();
        model.addAll(agents);
        
        initGui();
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

        
        // Add a new estate agent
        JButton btnInsert = new JButton("Insert");
        btnInsert.setIcon(mainFrame.createImageIcon("/de/dis2011/icons/user_add.png"));
        btnInsert.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            	_agent = new EstateAgent();
                provideAgentLogin();
                
            }
        });

       
        // remove estate agent
        JButton btnRemove = new JButton("Remove");
        btnRemove.setIcon(mainFrame.createImageIcon("/de/dis2011/icons/user_delete.png"));
        btnRemove.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    EstateAgent agent = model.findByRow(row);
                    if (estateAgentDao.delete(agent)) {
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

        pwdFrame.setSize(600, 100);
        JPanel pwdPanel = new JPanel();
        pwdPanel.setLayout(new BorderLayout());
        final JPasswordField pwd = new JPasswordField();

        //Buttons for password
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButton ok = new JButton("OK");
        JButton cancel = new JButton("Cancel");
        buttonPanel.add(ok);
        buttonPanel.add(cancel);

        pwdPanel.add(pwd, BorderLayout.CENTER);
        pwdPanel.add(buttonPanel, BorderLayout.PAGE_END);
        pwdFrame.add(pwdPanel);

        pwdFrame.setVisible(true);

        ok.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                final String password = pwd.getText();
                System.out.println(password);

                if (password.equals(PASSWORD)) {
                    showGui();
                    pwdFrame.setVisible(false);         
                } else {
                    String msg = "Login Failed";
                    JOptionPane.showMessageDialog(pwdFrame, msg, "Error: Wrong Password", JOptionPane.ERROR_MESSAGE);
                }
                pwd.setText("");                
                
            }

        });

        cancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                pwdFrame.setVisible(false);
            }

        });
    }

    public void provideAgentLogin() {

        loginFrame.setSize(600, 100);
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BorderLayout());
        final JTextField loginField = new JTextField();

        //Buttons for password
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButton ok = new JButton("OK");
        JButton cancel = new JButton("Cancel");
        buttonPanel.add(ok);
        buttonPanel.add(cancel);

        loginPanel.add(loginField, BorderLayout.CENTER);
        loginPanel.add(buttonPanel, BorderLayout.PAGE_END);
        loginFrame.add(loginPanel);

        loginFrame.setVisible(true);

        // set the password
        ok.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                final String login = loginField.getText();
                addLoginToAgent(login);
         	    loginField.setText("");
                           
            }

        });

        // cancel
        cancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                loginFrame.setVisible(false);

            }

        });

    }
    
   private void addLoginToAgent(String login){
    
	   _agent.setLogin(login);
	   if (estateAgentDao.save(_agent)){
		   model.add(_agent);              	
	   }
	   loginFrame.setVisible(false);
    
   }
}
