package database.models;

import java.time.LocalDate;

public class Payment {
    private double amount;
    private LocalDate payDate;
    private int daysAddition;

    // ---

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
