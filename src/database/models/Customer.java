package database.models;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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

    // TODO: Add payments list.

    // TODO: Remove defaults.
    public Customer () {
        joiningDate = LocalDate.now ();
        membershipEndDate = LocalDate.now ();
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
        return String.format ("%s %s: Joined %s", firstName, lastName, joiningDate);
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
