package de.dis2011.gui.estate;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;

import de.dis2011.data.Entity;
import de.dis2011.gui.EstateFrame;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-14
 */
public abstract class AbstractForm extends JFrame {

	final private EstateFrame estateFrame;

	private Entity entity;
	private JPanel formPane;

	abstract protected void buildForm();

	abstract protected void loadForm(Entity entity);

	abstract public void saveForm(Entity entity);

	public void setEntity(Entity entity) {
		this.entity = entity;
		loadForm(entity);
	}

	public AbstractForm(EstateFrame estateFrame, String title) {
		super(title);
		this.estateFrame = estateFrame;

		formPane = new JPanel();
		formPane.setLayout(new BoxLayout(formPane, BoxLayout.PAGE_AXIS));
		formPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

		buildForm();

		// Buttons

		JButton btnOk = new JButton("OK");
		btnOk.setMinimumSize(new Dimension(75, 23));
		btnOk.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				saveForm(entity);
				setVisible(false);
			}
		});

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setMinimumSize(new Dimension(75, 23));
		btnCancel.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				cancel(entity);
			}
		});

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		buttonPane.add(Box.createHorizontalGlue());
		buttonPane.add(btnCancel);
		buttonPane.add(btnOk);

		Container contentPane = getContentPane();
		contentPane.add(formPane, BorderLayout.CENTER);
		contentPane.add(buttonPane, BorderLayout.PAGE_END);

		pack();
		setMinimumSize(getSize());
	}

	public void showGui() {
		if (this.estateFrame != null) {
			estateFrame.centerFrame(this);
		}
		setVisible(true);
	}

	protected void cancel(Entity entity) {
		loadForm(entity);
		setVisible(false);
	}

	protected JTextField addFormTextElement(String label) {
		JTextField field = new JTextField();
		field.setAlignmentX(Component.LEFT_ALIGNMENT);
		addFormLabel(label).setLabelFor(field);

		formPane.add(field);
		formPane.add(Box.createRigidArea(new Dimension(0, 5)));

		return field;
	}

	protected JSpinner addFormDecimalElement(String label) {
		JSpinner field = new JSpinner();
		field.setAlignmentX(Component.LEFT_ALIGNMENT);
		addFormLabel(label).setLabelFor(field);

		field.setModel(new SpinnerNumberModel(0d, null, null, 0.01d));
		formPane.add(field);
		formPane.add(Box.createRigidArea(new Dimension(0, 5)));

		return field;
	}

	protected JSpinner addFormIntElement(String label) {
		JSpinner field = new JSpinner();
		field.setAlignmentX(Component.LEFT_ALIGNMENT);
		addFormLabel(label).setLabelFor(field);

		formPane.add(field);

		return field;
	}

	protected JCheckBox addFormCheckboxElement(String label) {
		JCheckBox field = new JCheckBox(label);
		field.setAlignmentX(Component.LEFT_ALIGNMENT);
		formPane.add(field);
		formPane.add(Box.createRigidArea(new Dimension(0, 5)));

		return field;
	}

	private JLabel addFormLabel(String label) {
		JLabel jLabel = new JLabel(label);
		jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		formPane.add(jLabel);
		formPane.add(Box.createRigidArea(new Dimension(0, 5)));

		return jLabel;
	}

	protected JComboBox<String> addFormComboBoxElement(String[] contents) {
;		JComboBox<String> jCombobox = new JComboBox<String>(contents);
//		jCombobox.setSelectedIndex(0);
		formPane.add(jCombobox);
		formPane.add(Box.createRigidArea(new Dimension(0, 5)));

		return jCombobox;
	}

	protected JComboBox<Integer> addFormComboBoxIntElement(Integer[] contents) {
		JComboBox<Integer> jCombobox = new JComboBox<Integer>(contents);
//		jCombobox.setSelectedIndex(0);
		formPane.add(jCombobox);
		formPane.add(Box.createRigidArea(new Dimension(0, 5)));

		return jCombobox;
	}

	protected JSpinner addFormDateElement(String label) {
		JSpinner field = new JSpinner();
		field.setAlignmentX(Component.LEFT_ALIGNMENT);
		addFormLabel(label).setLabelFor(field);

		field.setModel(new SpinnerDateModel());
		formPane.add(field);
		formPane.add(Box.createRigidArea(new Dimension(0, 5)));

		return field;
	}
}
