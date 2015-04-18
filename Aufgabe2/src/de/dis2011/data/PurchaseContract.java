package de.dis2011.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-07
 */
public class PurchaseContract extends Contract {
    private int noOfInstallments = 0;
    private double interestRate = 0.0;

    @Override
    public PreparedStatement createSelectStatement() throws SQLException {
        String selectSQL = "SELECT * FROM CONTRACT c INNER JOIN PURCHASE_CONTRACT p ON c.ID = p.CONTRACT_ID WHERE id = ?";
        PreparedStatement preparedStatement = getConnection().prepareStatement(selectSQL);
        preparedStatement.setInt(1, getId());

        return preparedStatement;
    }

    @Override
    public void applyResultSet(ResultSet resultSet) throws SQLException {
        super.applyResultSet(resultSet);

        this.setNoOfInstallments(resultSet.getInt("no_of_installments"));
        this.setInterestRate(resultSet.getDouble("interest_rate"));
    }

    @Override
    public void applyAdditionalInsertStatements(int insertedId) throws SQLException {
        String insertSQL = "INSERT INTO PURCHASE_CONTRACT (CONTRACT_ID, NO_OF_INSTALLMENTS, INTEREST_RATE) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = getConnection().prepareStatement(insertSQL);

        preparedStatement.setInt(1, insertedId);
        preparedStatement.setInt(2, noOfInstallments);
        preparedStatement.setDouble(3, interestRate);

        preparedStatement.executeUpdate();
    }

    @Override
    public void applyAdditionalUpdateStatements(int updatedId) throws SQLException {
        String insertSQL = "UPDATE PURCHASE_CONTRACT SET NO_OF_INSTALLMENTS=?, INTEREST_RATE=? WHERE CONTRACT_ID=?";
        PreparedStatement preparedStatement = getConnection().prepareStatement(insertSQL);

        preparedStatement.setInt(1, noOfInstallments);
        preparedStatement.setDouble(2, interestRate);
        preparedStatement.setInt(3, updatedId);

        preparedStatement.executeUpdate();
    }

    public boolean drop() {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("DELETE FROM PURCHASE_CONTRACT WHERE CONTRACT_ID=?");
            preparedStatement.setInt(1, getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();

            return super.drop();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getNoOfInstallments() {
        return noOfInstallments;
    }

    public void setNoOfInstallments(int noOfInstallments) {
        this.noOfInstallments = noOfInstallments;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
}
