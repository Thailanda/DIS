package de.dis2011.gui;

import de.dis2011.data.Entity;
import de.dis2011.data.Person;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-14
 */
public class PersonFrame extends JFrame {

    final private MainFrame mainFrame;
    final private PersonModel model = new PersonModel();
    final private JTable table = new JTable();

    public PersonFrame(MainFrame mainFrame) throws HeadlessException {
        super("Persons");
        this.mainFrame = mainFrame;

        initGui();
        List<Entity> persons = Person.findAll(Person.class);
        for (Entity person : persons) {
            model.addPerson((Person) person);
        }
    }

    public void showGui() {
        mainFrame.centerFrame(this);
        setVisible(true);
    }

    private void initGui() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

        table.setModel(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton btnInsert = new JButton("Insert");
        btnInsert.setIcon(mainFrame.createImageIcon("/de/dis2011/icons/user_add.png"));
        btnInsert.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Person p = new Person();
                p.save();

                model.addPerson(p);
            }
        });

        JButton btnRemove = new JButton("Remove");
        btnRemove.setIcon(mainFrame.createImageIcon("/de/dis2011/icons/user_delete.png"));
        btnRemove.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Person person = model.findByRow(table.getSelectedRow());
                if (person.drop()) {
                    model.removePerson(person);
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
}
