package de.dis2011.data;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-07
 */
public class Estate extends Entity {
    private int id = -1;
    private EstateAgent manager;
    private Contract contract;
    private String city = "";
    private String postalCode = "";
    private String street = "";
    private String streetNumber = "";
    private int squareArea = 0;

    public String getKind() {
        return "Generic Estate";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EstateAgent getManager() {
        return manager;
    }

    public void setManager(EstateAgent manager) {
        this.manager = manager;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public int getSquareArea() {
        return squareArea;
    }

    public void setSquareArea(int squareArea) {
        this.squareArea = squareArea;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }
}
