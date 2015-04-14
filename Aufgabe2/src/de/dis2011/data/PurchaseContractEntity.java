package de.dis2011.data;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-07
 */
public class PurchaseContractEntity extends Contract {
    private Integer noOfInstallments;
    private BigDecimal interestRate;

    @Override
    public PreparedStatement createSelectStatement() throws SQLException {
        String selectSQL = "SELECT * FROM CONTRACT c INNER JOIN PURCHASE_CONTRACT p ON c.ID = p.CONTRACT_ID WHERE id = ?";
        PreparedStatement preparedStatement = getConnection().prepareStatement(selectSQL);
        preparedStatement.setInt(1, getId());

        return preparedStatement;
    }

    public Integer getNoOfInstallments() {
        return noOfInstallments;
    }

    public void setNoOfInstallments(Integer noOfInstallments) {
        this.noOfInstallments = noOfInstallments;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }
}
