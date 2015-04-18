package de.dis2011.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * Finds all apartments.
     */
    public static List<Apartment> findAll() {
        ArrayList<Apartment> apartments = new ArrayList<>();
        try {
            String sql = "SELECT * FROM ESTATE e INNER JOIN APARTMENT a ON e.ID = a.ESTATE_ID";
            PreparedStatement statement = getConnection().prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Apartment p = new Apartment();
                p.applyResultSet(resultSet);
                apartments.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return apartments;
    }

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
        this.setRent(resultSet.getDouble("rent"));
        this.setRooms(resultSet.getInt("rooms"));
        this.setBalcony(resultSet.getBoolean("balcony"));
        this.setBuiltInKitchen(resultSet.getBoolean("built_in_kitchen"));
    }

    @Override
    public void applyAdditionalInsertStatements(int insertedId) throws SQLException {
        String insertSQL = "INSERT INTO APARTMENT (ESTATE_ID, FLOOR, RENT, ROOMS, BALCONY, BUILT_IN_KITCHEN) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = getConnection().prepareStatement(insertSQL);

        preparedStatement.setInt(1, insertedId);
        preparedStatement.setString(2, floor);
        preparedStatement.setDouble(3, rent);
        preparedStatement.setInt(4, rooms);
        preparedStatement.setBoolean(5, balcony);
        preparedStatement.setBoolean(6, builtInKitchen);

        preparedStatement.executeUpdate();
    }

    @Override
    public void applyAdditionalUpdateStatements(int updatedId) throws SQLException {
        String insertSQL = "UPDATE APARTMENT SET FLOOR=?, RENT=?, ROOMS=?, BALCONY=?, BUILT_IN_KITCHEN=? WHERE ESTATE_ID=?";
        PreparedStatement preparedStatement = getConnection().prepareStatement(insertSQL);

        preparedStatement.setString(1, floor);
        preparedStatement.setDouble(2, rent);
        preparedStatement.setInt(3, rooms);
        preparedStatement.setBoolean(4, balcony);
        preparedStatement.setBoolean(5, builtInKitchen);
        preparedStatement.setInt(6, updatedId);

        preparedStatement.executeUpdate();
    }

    @Override
    public PreparedStatement createSelectStatement() throws SQLException {
        String selectSQL = "SELECT * FROM ESTATE INNER JOIN APARTMENT ON ESTATE.ID = APARTMENT.ESTATE_ID WHERE ESTATE.ID = ?";
        PreparedStatement preparedStatement = getConnection().prepareStatement(selectSQL);
        preparedStatement.setInt(1, getId());

        return preparedStatement;
    }

    @Override
    public boolean drop() {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("DELETE FROM APARTMENT WHERE ESTATE_ID=?");
            preparedStatement.setInt(1, getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();

            return super.drop();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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
