package de.dis2011.data;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-07
 */
public class Apartment extends Estate {
    private String floor;
    private BigDecimal rent;
    private Integer rooms;
    private boolean balcony;
    private boolean builtInKitchen;

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public BigDecimal getRent() {
        return rent;
    }

    public void setRent(BigDecimal rent) {
        this.rent = rent;
    }

    public Integer getRooms() {
        return rooms;
    }

    public void setRooms(Integer rooms) {
        this.rooms = rooms;
    }

    public boolean hasBalcony() {
        return balcony;
    }

    public void setBalcony(boolean balcony) {
        this.balcony = balcony;
    }

    public boolean hasBuiltInKitchen() {
        return builtInKitchen;
    }

    public void setBuiltInKitchen(boolean builtInKitchen) {
        this.builtInKitchen = builtInKitchen;
    }

    @Override
    public void applyResultSet(ResultSet resultSet) throws SQLException {
        super.applyResultSet(resultSet);

        this.setFloor(resultSet.getString("floor"));
        this.setRent(resultSet.getBigDecimal("rent"));
        this.setRooms(resultSet.getInt("rooms"));
        this.setBalcony(resultSet.getBoolean("balcony"));
        this.setBuiltInKitchen(resultSet.getBoolean("built_in_kitchen"));
    }

    @Override
    public PreparedStatement createSelectStatement() throws SQLException {
        String selectSQL = "SELECT * FROM ESTATE INNER JOIN APARTMENT ON ESTATE.ID = APARTMENT.ESTATE_ID WHERE ESTATE.ID = ?";
        PreparedStatement preparedStatement = getConnection().prepareStatement(selectSQL);
        preparedStatement.setInt(1, getId());

        return preparedStatement;
    }
}
