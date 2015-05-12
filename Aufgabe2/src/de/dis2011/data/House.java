package de.dis2011.data;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-07
 */
public class House extends Estate {
    private String floors;
    private double price;
    private boolean garden;

    public String getFloors() {
        return floors;
    }

    public void setFloors(String floors) {
        this.floors = floors;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isGarden() {
        return garden;
    }

    public void setGarden(boolean garden) {
        this.garden = garden;
    }

    @Override
    public String getKind() {
        return "House";
    }

    @Override
    public String toString() {
        return "House in " + getPostalCode() + " " + getCity();
    }
}
