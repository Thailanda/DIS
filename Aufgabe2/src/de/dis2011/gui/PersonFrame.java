package de.dis2011.gui;

import de.dis2011.data.Entity;
import de.dis2011.data.Person;
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

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-14
 */
public class PersonFrame extends JFrame {

    private PersonModel model = new PersonModel();
    private JTable table = new JTable();

    public PersonFrame() throws HeadlessException {
        super("Persons");

        initGui();
        List<Entity> persons = Person.findAll(Person.class);
        for (Entity person : persons) {
            model.addPerson((Person) person);
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
                Person p = new Person();
                p.save();

                model.addPerson(p);
            }
        });

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        buttonPane.add(Box.createHorizontalGlue());
        buttonPane.add(btnInsert);

        Container contentPane = getContentPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.add(buttonPane, BorderLayout.PAGE_END);

        pack();
    }

    public PersonModel getModel() {
        return model;
    }
}
