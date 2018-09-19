package database.models;

import java.time.LocalDate;
import java.util.UUID;

/**
 * POJO of a Payment object of the Customer.
 * Each Payment is added to the list in Customer item.
 */
public class Payment {
    private UUID _id = UUID.randomUUID ();
    private double amount;
    private LocalDate payDate = LocalDate.now ();
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

    public void set_id (UUID _id) {
        this._id = _id;
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

    public UUID get_id () {
        return _id;
    }

    // ---

    /**
     * Thrown when a Payment doesn't exist.
     */
    public static class PaymentNotFoundException extends Exception {
        /**
         * @param paymenyId UUID of the payment to be removed.
         * @param victim Customer who's history was searched.
         */
        public PaymentNotFoundException(UUID paymenyId, Customer victim) {
            super (String.format ("Payment with UUID %s not found in the customer %s!", paymenyId, victim));
        }
    }
}
