package de.dis2011.gui.estate;

import de.dis2011.data.Apartment;
import de.dis2011.data.Entity;
import de.dis2011.gui.management.EstateManagementFrame;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.JTextField;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-14
 */
public class ApartmentForm extends AbstractForm {

    private JTextField floor;
    private JSpinner rent;
    private JSpinner rooms;
    private JCheckBox balcony;
    private JCheckBox builtInKitchen;

    public ApartmentForm(EstateManagementFrame estateFrame) {
        super(estateFrame, "Apartment Form");
    }

    @Override
    protected void buildForm() {
        floor = addFormTextElement("Floor");
        rent = addFormDecimalElement("Rent");
        rooms = addFormIntElement("Rooms");
        balcony = addFormCheckboxElement("Has a Balcony");
        builtInKitchen = addFormCheckboxElement("Has a built in Kitchen");
    }

    @Override
    protected void loadForm(Entity entity) {
        Apartment apartment = (Apartment) entity;
        floor.setText(apartment.getFloor());
        rent.setValue(apartment.getRent());
        rooms.setValue(apartment.getRooms());
        balcony.setSelected(apartment.isBalcony());
        builtInKitchen.setSelected(apartment.isBuiltInKitchen());
    }

    @Override
    public void saveForm(Entity entity) {
        Apartment apartment = (Apartment) entity;

        apartment.setFloor(floor.getText());
        apartment.setRent((Double) rent.getValue());
        apartment.setRooms((Integer) rooms.getValue());
        apartment.setBalcony(balcony.isSelected());
        apartment.setBuiltInKitchen(builtInKitchen.isSelected());

        apartment.save();
    }
}
