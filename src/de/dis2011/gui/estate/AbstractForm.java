package de.dis2011.gui.estate;

import de.dis2011.data.Entity;
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

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-14
 */
public abstract class AbstractForm extends JFrame {

	final protected JFrame frame;

	private Entity entity;
	private JPanel formPane;

	abstract protected void buildForm();

	abstract protected void loadForm(Entity entity);

	abstract public void saveForm(Entity entity);

	public void setEntity(Entity entity) {
		this.entity = entity;
		loadForm(entity);
	}

	public AbstractForm(JFrame frame, String title) {
		super(title);
		this.frame = frame;

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
		if (this.frame != null) {
			setLocation(
				frame.getX() + frame.getWidth() / 2 - getWidth() / 2,
				frame.getY() + frame.getHeight() / 2 - getHeight() / 2
			);
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

	protected JComboBox<String> addFormComboBoxElement(String label, String[] contents) {
;		JComboBox<String> field = new JComboBox<>(contents);
		field.setAlignmentX(Component.LEFT_ALIGNMENT);
		addFormLabel(label).setLabelFor(field);

		formPane.add(field);
		formPane.add(Box.createRigidArea(new Dimension(0, 5)));

		return field;
	}

	protected JComboBox<Object> addFormComboBoxElement(String label, Object[] contents) {
		JComboBox<Object> field = new JComboBox<>(contents);
		field.setAlignmentX(Component.LEFT_ALIGNMENT);
		addFormLabel(label).setLabelFor(field);

		formPane.add(field);
		formPane.add(Box.createRigidArea(new Dimension(0, 5)));

		return field;
	}

	protected JSpinner addFormDateElement(String label) {
		JSpinner field = new JSpinner();
		SpinnerDateModel model = new SpinnerDateModel();

		field.setAlignmentX(Component.LEFT_ALIGNMENT);
		field.setModel(model);
		field.setEditor(new JSpinner.DateEditor(field, "dd.MM.yyyy"));
		addFormLabel(label).setLabelFor(field);

		formPane.add(field);

		return field;
	}

	private JLabel addFormLabel(String label) {
		JLabel jLabel = new JLabel(label);
		jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		formPane.add(jLabel);
		formPane.add(Box.createRigidArea(new Dimension(0, 5)));

		return jLabel;
	}
}
