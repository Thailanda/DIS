package de.dis2011.data;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-07
 */
public class TenancyContractEntity extends Contract {
    private Date startDate;
    private Integer duration;
    private BigDecimal additionalCosts;

    @Override
    public PreparedStatement createSelectStatement() throws SQLException {
        String selectSQL = "SELECT * FROM CONTRACT c INNER JOIN TENANCY_CONTRACT t ON c.ID = t.CONTRACT_ID WHERE id = ?";
        PreparedStatement preparedStatement = getConnection().prepareStatement(selectSQL);
        preparedStatement.setInt(1, getId());

        return preparedStatement;
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

    public BigDecimal getAdditionalCosts() {
        return additionalCosts;
    }

    public void setAdditionalCosts(BigDecimal additionalCosts) {
        this.additionalCosts = additionalCosts;
    }
}
