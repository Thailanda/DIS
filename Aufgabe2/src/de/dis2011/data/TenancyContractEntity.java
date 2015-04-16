package de.dis2011.data;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-07
 */
public class TenancyContractEntity extends Contract {
    private Date startDate;
    private Integer duration;
    private double additionalCosts;

    @Override
    public PreparedStatement createSelectStatement() throws SQLException {
        String selectSQL = "SELECT * FROM CONTRACT c INNER JOIN TENANCY_CONTRACT t ON c.ID = t.CONTRACT_ID WHERE id = ?";
        PreparedStatement preparedStatement = getConnection().prepareStatement(selectSQL);
        preparedStatement.setInt(1, getId());

        return preparedStatement;
    }

    @Override
    public void applyResultSet(ResultSet resultSet) throws SQLException {
        super.applyResultSet(resultSet);

        this.setStartDate(resultSet.getDate("start_date"));
        this.setDuration(resultSet.getInt("duration"));
        this.setAdditionalCosts(resultSet.getDouble("additional_costs"));
    }

    @Override
    public void applyAdditionalInsertStatements(int insertedId) throws SQLException {
        String insertSQL = "INSERT INTO TENANCY_CONTRACT (CONTRACT_ID, START_DATE, DURATION, ADDITIONAL_COSTS) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = getConnection().prepareStatement(insertSQL);

        preparedStatement.setInt(1, insertedId);
        preparedStatement.setDate(2, startDate);
        preparedStatement.setInt(3, duration);
        preparedStatement.setDouble(4, additionalCosts);

        preparedStatement.executeUpdate();
    }

    @Override
    public void applyAdditionalUpdateStatements(int updatedId) throws SQLException {
        String insertSQL = "UPDATE TENANCY_CONTRACT SET START_DATE=?, DURATION=?, ADDITIONAL_COSTS=? WHERE CONTRACT_ID=?";
        PreparedStatement preparedStatement = getConnection().prepareStatement(insertSQL);

        preparedStatement.setDate(1, startDate);
        preparedStatement.setInt(2, duration);
        preparedStatement.setDouble(3, additionalCosts);
        preparedStatement.setInt(4, updatedId);

        preparedStatement.executeUpdate();
    }

    public boolean drop() {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("DELETE FROM TENANCY_CONTRACT WHERE CONTRACT_ID=?");
            preparedStatement.setInt(1, getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();

            return super.drop();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getAdditionalCosts() {
        return additionalCosts;
    }

    public void setAdditionalCosts(Double additionalCosts) {
        this.additionalCosts = additionalCosts;
    }
}
