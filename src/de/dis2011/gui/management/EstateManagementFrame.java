package de.dis2011.gui.management;

import com.google.inject.Inject;
import de.dis2011.data.Apartment;
import de.dis2011.data.Estate;
import de.dis2011.data.House;
import de.dis2011.data.dao.ApartmentDao;
import de.dis2011.data.dao.HouseDao;
import de.dis2011.gui.MainFrame;
import de.dis2011.gui.estate.ApartmentForm;
import de.dis2011.gui.estate.HouseForm;
import de.dis2011.model.EstateAgentSecurityContext;
import de.dis2011.model.EstateModel;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-14
 */
public class EstateManagementFrame extends JFrame implements Observer {

    private final MainFrame mainFrame;
    private final EstateModel model;
    private final JTable table = new JTable();
    private final ApartmentDao apartmentDao;
    private final HouseDao houseDao;
    private final EstateAgentSecurityContext context;

    @Inject
    public EstateManagementFrame(MainFrame mainFrame, EstateAgentSecurityContext context) throws HeadlessException {
        super("Estates");
        this.mainFrame = mainFrame;
        this.context = context;

        context.addObserver(this);
        
        apartmentDao = new ApartmentDao();
        houseDao = new HouseDao();
        model = new EstateModel(apartmentDao, houseDao);
        
        initGui();
    }

    public void showGui() {
        mainFrame.centerFrame(this);
        setVisible(true);
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable == context) {
            boolean authenticated = context.isAuthenticated();

            if (authenticated) {
                setTitle("Estates by " + context.getUser().getName());

                model.clear();
                Set<Estate> estates = context.getUser().getEstates();
                model.addAll(estates);
            }
        }
    }

    public void centerFrame(JFrame frame) {
        frame.setLocation(getX() + getWidth() / 2 - frame.getWidth() / 2, getY() + getHeight() / 2 - frame.getHeight()
                / 2);
    }

    private void initGui() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

        table.setModel(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton btnEdit = new JButton("Edit");
        btnEdit.setIcon(mainFrame.createImageIcon("/de/dis2011/icons/pencil.png"));
        btnEdit.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    Estate estate = model.findByRow(row);
                    editEstate(estate);
                }
            }
        });

        JButton btnNewHouse = new JButton("New House");
        btnNewHouse.setIcon(mainFrame.createImageIcon("/de/dis2011/icons/house.png"));
        btnNewHouse.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                House house = new House();
                house.setManager(context.getUser());
                if (houseDao.save(house)){
                    model.add(house);
                    editEstate(house);
                }

            }
        });

        JButton btnNewApartment = new JButton("New Apartment");
        btnNewApartment.setIcon(mainFrame.createImageIcon("/de/dis2011/icons/building.png"));
        btnNewApartment.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Apartment apartment = new Apartment();
                apartment.setManager(context.getUser());
                apartment.setRooms(0);
                if(apartmentDao.save(apartment)){
                	model.add(apartment);
                	editEstate(apartment);
                }
            }
        });

        JButton btnRemove = new JButton("Remove");;
        btnRemove.setIcon(mainFrame.createImageIcon("/de/dis2011/icons/delete.png"));
        btnRemove.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    boolean deleted = false;
                	Estate estate = model.findByRow(row);
                	
                    if (estate instanceof Apartment) {
                    	deleted = apartmentDao.delete((Apartment) estate);
                    }
                    else if (estate instanceof House){
                    	deleted = houseDao.delete((House) estate);
                    }
                    
                    if(deleted){
                        model.remove(estate);
                        deleted = false;
                    }
                }
            }
        });

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPane.add(btnEdit);
        buttonPane.add(Box.createHorizontalGlue());
        buttonPane.add(btnNewHouse);
        buttonPane.add(btnNewApartment);
        buttonPane.add(btnRemove);

        Container contentPane = getContentPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.add(buttonPane, BorderLayout.PAGE_END);

        pack();

        setMinimumSize(new Dimension(800, getHeight()));
    }

    private void editEstate(Estate estate) {
        if (estate instanceof Apartment) {
            ApartmentForm form = new ApartmentForm(this, apartmentDao);
            form.setEntity(estate);
            form.showGui();
        }
        else if (estate instanceof House) {
            HouseForm form = new HouseForm(this, houseDao);
            form.setEntity(estate);
            form.showGui();
        }
    }
}
