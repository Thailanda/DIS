package de.dis2011.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-07
 */
public class Estate extends Entity {
    private int id;
    private EstateAgent manager;
    private String city;
    private String postalCode;
    private String street;
    private String streetNumber;
    private Integer squareArea;

    @Override
    public void applyResultSet(ResultSet resultSet) throws SQLException {
        EstateAgent manager = new EstateAgent();
        manager.load(resultSet.getInt("manager_id"));
        this.setManager(manager);

        this.setCity(resultSet.getString("city"));
        this.setPostalCode(resultSet.getString("postal_code"));
        this.setStreet(resultSet.getString("street"));
        this.setStreetNumber(resultSet.getString("street_number"));
        this.setSquareArea(resultSet.getInt("square_area"));
    }

    @Override
    public PreparedStatement createSelectStatement() throws SQLException {
        String selectSQL = "SELECT * FROM ESTATE WHERE id = ?";
        PreparedStatement preparedStatement = getConnection().prepareStatement(selectSQL);
        preparedStatement.setInt(1, id);

        return preparedStatement;
    }

    @Override
    public PreparedStatement createInsertStatement() throws SQLException {
        String insertSQL = "INSERT INTO ESTATE (MANAGER_ID, CITY, POSTAL_CODE, STREET, STREET_NUMBER, SQUARE_AREA)  VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = getConnection().prepareStatement(insertSQL);

        preparedStatement.setInt(1, getManager().getId());
        preparedStatement.setString(2, getCity());
        preparedStatement.setString(3, getPostalCode());
        preparedStatement.setString(4, getStreet());
        preparedStatement.setString(5, getStreetNumber());
        preparedStatement.setInt(6, getSquareArea());

        return preparedStatement;
    }

    @Override
    public PreparedStatement createUpdateStatement() throws SQLException {
        String updateSQL = "UPDATE ESTATE SET MANAGER_ID=?, CITY=?, POSTAL_CODE=?, STREET=?, STREET_NUMBER=?, SQUARE_AREA=? WHERE ID=?";
        PreparedStatement preparedStatement = getConnection().prepareStatement(updateSQL);

        preparedStatement.setInt(1, getManager().getId());
        preparedStatement.setString(2, getCity());
        preparedStatement.setString(3, getPostalCode());
        preparedStatement.setString(4, getStreet());
        preparedStatement.setString(5, getStreetNumber());
        preparedStatement.setInt(6, getSquareArea());
        preparedStatement.setInt(7, getId());


        return preparedStatement;
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

    public Integer getSquareArea() {
        return squareArea;
    }

    public void setSquareArea(Integer squareArea) {
        this.squareArea = squareArea;
    }
}
