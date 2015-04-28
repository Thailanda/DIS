package de.dis2011.data;

import java.sql.Date;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-07
 */
public class TenancyContract extends Contract {
    private Date startDate;
    private Integer duration;
    private double additionalCosts;

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
