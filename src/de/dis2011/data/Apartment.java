package de.dis2011.data;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-07
 */
public class Apartment extends Estate {
    private String floor;
    private double rent;
    private Integer rooms;
    private boolean balcony;
    private boolean builtInKitchen;

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public double getRent() {
        return rent;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

    public Integer getRooms() {
        return rooms;
    }

    public void setRooms(Integer rooms) {
        this.rooms = rooms;
    }

    public boolean isBalcony() {
        return balcony;
    }

    public void setBalcony(boolean balcony) {
        this.balcony = balcony;
    }

    public boolean isBuiltInKitchen() {
        return builtInKitchen;
    }

    public void setBuiltInKitchen(boolean builtInKitchen) {
        this.builtInKitchen = builtInKitchen;
    }

    @Override
    public String getKind() {
        return "Apartment";
    }

    @Override
    public String toString() {
        return "Apartment in " + getPostalCode() + " " + getCity();
    }
}
