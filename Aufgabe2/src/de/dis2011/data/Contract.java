package de.dis2011.data;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-07
 */
public class Contract extends Entity {
    private int id;
    private String contractNo;
    private Date date;
    private String place;

    @Override
    public void applyResultSet(ResultSet resultSet) throws SQLException {

        this.setContractNo(resultSet.getString("contract_no"));
        this.setDate(resultSet.getDate("date"));
        this.setPlace(resultSet.getString("place"));
    }

    @Override
    public PreparedStatement createSelectStatement() throws SQLException {
        String selectSQL = "SELECT * FROM CONTRACT WHERE id = ?";
        PreparedStatement preparedStatement = getConnection().prepareStatement(selectSQL);
        preparedStatement.setInt(1, id);

        return preparedStatement;
    }

    @Override
    public PreparedStatement createInsertStatement() throws SQLException {
        String insertSQL = "INSERT INTO CONTRACT (CONTRACT_NO, DATE, PLACE) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = getConnection().prepareStatement(insertSQL);

        preparedStatement.setString(1, getContractNo());
        preparedStatement.setDate(2, getDate());
        preparedStatement.setString(3, getPlace());

        return preparedStatement;
    }

    @Override
    public PreparedStatement createUpdateStatement() throws SQLException {
        String updateSQL = "UPDATE CONTRACT SET CONTRACT_NO=?, DATE=?, PLACE=? WHERE ID=?";
        PreparedStatement preparedStatement = getConnection().prepareStatement(updateSQL);

        preparedStatement.setString(1, getContractNo());
        preparedStatement.setDate(2, getDate());
        preparedStatement.setString(3, getPlace());
        preparedStatement.setInt(4, getId());


        return preparedStatement;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
