package de.dis2011.data;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-07
 */
public class House extends Estate {
    private String floors;
    private BigDecimal price;
    private boolean garden;

    public String getFloors() {
        return floors;
    }

    public void setFloors(String floors) {
        this.floors = floors;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean hasGarden() {
        return garden;
    }

    public void setGarden(boolean garden) {
        this.garden = garden;
    }

    @Override
    public void applyResultSet(ResultSet resultSet) throws SQLException {
        super.applyResultSet(resultSet);

        this.setFloors(resultSet.getString("floors"));
        this.setPrice(resultSet.getBigDecimal("price"));
        this.setGarden(resultSet.getBoolean("garden"));
    }

    @Override
    public void applyAdditionalInsertStatements(int insertedId) throws SQLException {
        String insertSQL = "INSERT INTO HOUSE (ESTATE_ID, FLOORS, PRICE, GARDEN) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = getConnection().prepareStatement(insertSQL);

        preparedStatement.setInt(1, insertedId);
        preparedStatement.setString(2, floors);
        preparedStatement.setBigDecimal(3, price);
        preparedStatement.setBoolean(4, garden);

        preparedStatement.executeUpdate();
    }

    @Override
    public void applyAdditionalUpdateStatements(int updatedId) throws SQLException {
        String insertSQL = "UPDATE HOUSE SET FLOORS=?, PRICE=?, GARDEN=? WHERE ESTATE_ID=?";
        PreparedStatement preparedStatement = getConnection().prepareStatement(insertSQL);

        preparedStatement.setString(1, floors);
        preparedStatement.setBigDecimal(2, price);
        preparedStatement.setBoolean(3, garden);
        preparedStatement.setInt(4, updatedId);

        preparedStatement.executeUpdate();
    }

    @Override
    public PreparedStatement createSelectStatement() throws SQLException {
        String selectSQL = "SELECT * FROM ESTATE e INNER JOIN HOUSE h ON e.ID = h.ESTATE_ID WHERE e.ID = ?";
        PreparedStatement preparedStatement = getConnection().prepareStatement(selectSQL);
        preparedStatement.setInt(1, getId());

        return preparedStatement;
    }
}
