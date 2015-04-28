package de.dis2011.gui.management;

import de.dis2011.data.Person;
import de.dis2011.data.dao.PersonDao;
import de.dis2011.gui.MainFrame;
import de.dis2011.model.PersonModel;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
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
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-14
 */
public class PersonManagementFrame extends JFrame {

    final private MainFrame mainFrame;
    final private PersonModel model = new PersonModel();
    final private JTable table = new JTable();
    final private PersonDao personDao;

    public PersonManagementFrame(MainFrame mainFrame) throws HeadlessException {
        super("Persons");
        this.mainFrame = mainFrame;
        this.personDao = new PersonDao(mainFrame.getSessionFactory());

        model.setDao(personDao);

        initGui();
    }

    public void showGui() {
        model.clear();
        List<Person> persons = personDao.findAll();
        model.addAll(persons);

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
                Person p = new Person();
                personDao.save(p);

                model.add(p);
            }
        });

        final JButton btnRemove = new JButton("Remove");;
        btnRemove.setIcon(mainFrame.createImageIcon("/de/dis2011/icons/user_delete.png"));
        btnRemove.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    Person person = model.findByRow(row);
                    if (personDao.delete(person)) {
                        model.remove(person);
                    } else {
                        showError("Could not delete person – maybe there are still contracts which prohibit deletion?");
                    }
                }
            }
        });

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = table.getSelectedRow();
                Person person = model.findByRow(row);

                if (person != null) {
                    btnRemove.setEnabled(person.getContracts().isEmpty());
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

    protected void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
