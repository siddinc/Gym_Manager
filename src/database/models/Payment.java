package database.models;

import java.time.LocalDate;

/**
 * POJO of a Payment object of the Customer.
 * Each Payment is added to the list in Customer item.
 */
public class Payment {
    private double amount;
    private LocalDate payDate;
    private int daysAddition;

    // ---

    /**
     * New membership end date is not saved in the payments collection.
     * Rather we save it in the Customer model for easier access.
     * @return Payment date + The amount of days added.
     */
    public LocalDate getNewEndDate () {
        return LocalDate.ofEpochDay (payDate.toEpochDay () + daysAddition);
    }

    // ---

    public void setAmount (double amount) {
        this.amount = amount;
    }

    public void setPayDate (LocalDate payDate) {
        this.payDate = payDate;
    }

    public void setDaysAddition (int daysAddition) {
        this.daysAddition = daysAddition;
    }

    // ---

    public double getAmount () {
        return amount;
    }

    public LocalDate getPayDate () {
        return payDate;
    }

    public int getDaysAddition () {
        return daysAddition;
    }
}
