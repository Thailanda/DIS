package de.dis2011.gui;

import de.dis2011.data.Apartment;
import de.dis2011.data.Estate;
import de.dis2011.data.House;
import de.dis2011.gui.estate.ApartmentForm;
import de.dis2011.gui.estate.HouseForm;
import de.dis2011.model.EstateAgentSecurityContext;
import de.dis2011.model.EstateModel;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
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
public class EstateFrame extends JFrame implements Observer {

    final private MainFrame mainFrame;
    final private EstateAgentSecurityContext context;
    final private EstateModel model = new EstateModel();
    final private JTable table = new JTable();

    public EstateFrame(MainFrame mainFrame) throws HeadlessException {
        super("Estates");
        this.mainFrame = mainFrame;
        context = mainFrame.getContext();
        context.addObserver(this);

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
                List<Estate> estates = Estate.findByEstateAgent(context.getUser());
                for (Estate estate : estates) {
                    model.add(estate);
                }
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
                house.save();

                model.add(house);
                editEstate(house);
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
                apartment.save();

                model.add(apartment);
                editEstate(apartment);
            }
        });

        JButton btnRemove = new JButton("Remove");;
        btnRemove.setIcon(mainFrame.createImageIcon("/de/dis2011/icons/delete.png"));
        btnRemove.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    Estate estate = model.findByRow(row);
                    if (estate.drop()) {
                        model.remove(estate);
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
            ApartmentForm form = new ApartmentForm(this);
            form.setEntity(estate);
            form.showGui();
        }
        else if (estate instanceof House) {
            HouseForm form = new HouseForm(this);
            form.setEntity(estate);
            form.showGui();
        }
    }
}
