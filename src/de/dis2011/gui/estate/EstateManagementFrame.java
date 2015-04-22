package de.dis2011.gui.estate;

import de.dis2011.data.House;
import de.dis2011.data.dao.HouseDao;
import de.dis2011.gui.MainFrame;
import de.dis2011.model.HouseDataModel;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
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


public class EstateManagementFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	JFrame pwdFrame = new JFrame("Password Required");
	private final String PASSWORD = "demo";
	
	private HouseDataModel model = new HouseDataModel();
	private JTable table = new JTable();

	private final HouseDao houseDao;

    public EstateManagementFrame(MainFrame mainFrame) throws HeadlessException {
        super("Estates");
		houseDao = new HouseDao(mainFrame.getSessionFactory());

        initGui();
        List<House> persons = houseDao.findAll();
        for (House person : persons) {
            model.addHouse(person);
        }
    }

    private void initGui() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setMinimumSize(new Dimension(800, 600));
        setLocation(screenSize.width / 2 - getSize().width / 2, screenSize.height / 2 - getSize().height / 2);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

        table.setModel(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton btnInsert = new JButton("Insert");
        btnInsert.addActionListener(new AbstractAction() {
			@Override
            public void actionPerformed(ActionEvent actionEvent) {
				saveHouse();
            }
        });

        JButton btnRemove = new JButton("Remove");
        btnRemove.addActionListener(new AbstractAction() {
			@Override
            public void actionPerformed(ActionEvent actionEvent) {
				removeHouse();
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
    
    private void saveHouse() {
    	House h = new House();
        h.save();

        model.addHouse(h);
    }
    
    private void removeHouse() {
    	House house = model.findByRow(table.getSelectedRow());
        if (house.drop()) {
            model.removeHouse(house);
        }
    }

	public void showGUI() {
		this.setVisible(true);
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
					showGUI();
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
