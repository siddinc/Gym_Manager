package database.models;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.UUID;

/**
 * POJO of Customer model to be saved in database.
 */
public class Customer {
    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate birthDate;

    private LocalDate joiningDate;
    private LocalDate membershipEndDate;

    private List<Payment> paymentsHistory = new LinkedList<Payment> ();

    public Customer () {
        joiningDate = LocalDate.now ();
        membershipEndDate = LocalDate.now ();
    }

    // -----

    /**
     * @param payment Payment POJO to add to Customer's history.
     */
    public void addPayment (Payment payment) {
        paymentsHistory.add (payment);
    }

    /**
     * @param targetIdString UUID of payment to remove.
     * @throws Payment.PaymentNotFoundExcept If payment does not exist in the Customer's history.
     */
    public void removePayment (String targetIdString) throws Payment.PaymentNotFoundException {
        UUID targetId = UUID.fromString (targetIdString);
        ListIterator<Payment> it = paymentsHistory.listIterator ();
        while (it.hasNext ()) {
            if (it.next ().get_id ().compareTo (targetId) == 0) {
                it.remove ();
                return;
            }
        }
        throw new Payment.PaymentNotFoundException (targetId, this);
    }

    // -----

    /**
     * Returns a positive long integer of the number of days past the joining date.
     * Returns a negative long integer if the joining date is in the future.
     *
     * @return Number of days between current date and date of joining.
     */
    public long calculateDaysFromJoining () {
        return calculateDaysFromJoining (LocalDate.now ());
    }

    /**
     * Returns a positive long integer of the number of days past the joining date.
     * Returns a negative long integer if the joining date is in the future.
     *
     * @param ref Reference date.
     * @return Number of days past the provided date.
     */
    public long calculateDaysFromJoining (LocalDate ref) {
        return ChronoUnit.DAYS.between (joiningDate, ref);
    }

    /**
     * Returns a positive long integer if today's date is before end of membership.
     * Returns a negative long integer if the membership date has passed.
     *
     * @return Number of days till the end date of membership.
     */
    public long calculateDaysTillEnd () {
        return calculateDaysTillEnd (LocalDate.now ());
    }

    /**
     * Returns a positive long integer if the reference date is before end of membership.
     * Returns a negative long integer if the membership date has passed.
     *
     * @param ref Reference date.
     * @return
     */
    public long calculateDaysTillEnd (LocalDate ref) {
        return ChronoUnit.DAYS.between (ref, membershipEndDate);
    }

    // -----

    @Override
    public String toString () {
        return String.format ("%s %s", firstName, lastName);
    }

    // -----

    public void setFirstName (String firstName) {
        this.firstName = firstName;
    }

    public void setLastName (String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender () {
        return gender;
    }

    public void setBirthDate (LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setJoiningDate (LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public void setMembershipEndDate (LocalDate membershipEndDate) {
        this.membershipEndDate = membershipEndDate;
    }

    // -----

    public String getFirstName () {
        return firstName;
    }

    public String getLastName () {
        return lastName;
    }

    public void setGender (Gender gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate () {
        return birthDate;
    }

    public LocalDate getJoiningDate () {
        return joiningDate;
    }

    public LocalDate getMembershipEndDate () {
        return membershipEndDate;
    }

    // -----

    /**
     * Options for customer gender field.
     */
    public static enum Gender {
        MALE,
        FEMALE,
        OTHER;
    }

}
