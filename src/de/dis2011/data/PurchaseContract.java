package de.dis2011.data;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-07
 */
public class PurchaseContract extends Contract {
    private int noOfInstallments = 0;
    private double interestRate = 0.0;

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
