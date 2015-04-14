package de.dis2011.data;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-07
 */
public class EstateAgent extends Entity {
    private int id;
    private String name;
    private String address;
    private String login;
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    protected String getFindAllSql() {
        return "SELECT * FROM ESTATE_AGENT";
    }

    @Override
    public void applyResultSet(ResultSet resultSet) throws SQLException {
        this.setName(resultSet.getString("name"));
        this.setAddress(resultSet.getString("address"));
        this.setLogin(resultSet.getString("login"));
        this.setPassword(resultSet.getString("password"));
    }

    @Override
    public PreparedStatement createSelectStatement() throws SQLException {
        String selectSQL = "SELECT * FROM ESTATE_AGENT WHERE id = ?";
        PreparedStatement preparedStatement = getConnection().prepareStatement(selectSQL);
        preparedStatement.setInt(1, id);

        return preparedStatement;
    }

    @Override
    public PreparedStatement createInsertStatement() throws SQLException {
        String insertSQL = "INSERT INTO ESTATE_AGENT (NAME, ADDRESS, LOGIN, PASSWORD) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = getConnection().prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setString(1, getName());
        preparedStatement.setString(2, getAddress());
        preparedStatement.setString(3, getLogin());
        preparedStatement.setString(4, getPassword());

        return preparedStatement;
    }

    @Override
    public PreparedStatement createUpdateStatement() throws SQLException {
        String updateSQL = "UPDATE ESTATE_AGENT SET NAME = ?, ADDRESS = ?, LOGIN = ?, PASSWORD = ? WHERE ID = ?";
        PreparedStatement preparedStatement = getConnection().prepareStatement(updateSQL);

        preparedStatement.setString(1, getName());
        preparedStatement.setString(2, getAddress());
        preparedStatement.setString(3, getLogin());
        preparedStatement.setString(4, getPassword());
        preparedStatement.setInt(5, getId());

        return preparedStatement;
    }
}
