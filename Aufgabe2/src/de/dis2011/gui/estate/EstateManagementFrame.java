package de.dis2011.gui.estate;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import de.dis2011.data.Entity;
import de.dis2011.data.House;
import de.dis2011.model.HouseDataModel;


public class EstateManagementFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private HouseDataModel model = new HouseDataModel();
    private JTable table = new JTable();

    public EstateManagementFrame() throws HeadlessException {
        super("Estates");

        initGui();
        List<Entity> persons = House.findAll(House.class);
        for (Entity person : persons) {
            model.addHouse((House) person);
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
}
