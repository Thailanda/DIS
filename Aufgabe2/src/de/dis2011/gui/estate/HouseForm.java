package de.dis2011.gui.estate;

import de.dis2011.data.House;
import de.dis2011.data.Entity;
import de.dis2011.gui.EstateFrame;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.JTextField;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-14
 */
public class HouseForm extends AbstractForm {

    private JTextField floors;
    private JSpinner price;
    private JCheckBox garden;

    public HouseForm(EstateFrame estateFrame) {
        super(estateFrame, "House Form");
    }

    @Override
    protected void buildForm() {
        floors = addFormTextElement("Floors");
        price = addFormDecimalElement("Price");
        garden = addFormCheckboxElement("Has a Garden");
    }

    @Override
    protected void loadForm(Entity entity) {
        House house = (House) entity;
        floors.setText(house.getFloors());
        price.setValue(house.getPrice());
        garden.setSelected(house.hasGarden());
    }

    @Override
    public void saveForm(Entity entity) {
        House house = (House) entity;

        house.setFloors(floors.getText());
        house.setPrice((Double) price.getValue());
        house.setGarden(garden.isSelected());

        house.save();
    }
}
