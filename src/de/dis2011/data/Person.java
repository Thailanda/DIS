package de.dis2011.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-07
 */
public class Person extends Entity {
    private int id = -1;
    private String firstName = "";
    private String name = "";
    private String address = "";
    private Set<Contract> contracts = new HashSet<>();

    @Override
    public void applyResultSet(ResultSet resultSet) throws SQLException {
        this.setId(resultSet.getInt("id"));
        this.setFirstName(resultSet.getString("first_name"));
        this.setName(resultSet.getString("name"));
        this.setAddress(resultSet.getString("address"));
    }

    @Override
    public PreparedStatement createSelectStatement() throws SQLException {
        String selectSQL = "SELECT * FROM PERSON WHERE id = ?";
        PreparedStatement preparedStatement = getConnection().prepareStatement(selectSQL);
        preparedStatement.setInt(1, id);

        return preparedStatement;
    }

    @Override
    public PreparedStatement createInsertStatement() throws SQLException {
        String insertSQL = "INSERT INTO PERSON (FIRST_NAME, NAME, ADDRESS) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = getConnection().prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setString(1, getFirstName());
        preparedStatement.setString(2, getName());
        preparedStatement.setString(3, getAddress());

        return preparedStatement;
    }

    @Override
    public PreparedStatement createUpdateStatement() throws SQLException {
        String updateSQL = "UPDATE PERSON SET FIRST_NAME=?, NAME=?, ADDRESS=? WHERE ID=?";
        PreparedStatement preparedStatement = getConnection().prepareStatement(updateSQL);

        preparedStatement.setString(1, getFirstName());
        preparedStatement.setString(2, getName());
        preparedStatement.setString(3, getAddress());
        preparedStatement.setInt(4, getId());


        return preparedStatement;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(Set<Contract> contracts) {
        this.contracts = contracts;
    }

    @Override
    public String toString() {
        return firstName + " " + name;
    }
}
