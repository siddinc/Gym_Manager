package database.models;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

/**
 * Customer POJO model to be saved in database.
 */
@Entity
@Table(name = "CUSTOMER")
public class Customer {
    @Id private String _id;
    @Column(name = "FIRST_NAME", nullable = false) private String firstName;
    @Column(name = "LAST_NAME", nullable = false) private String lastName;
    @Column(name = "GENDER") @Enumerated(EnumType.STRING)  private Gender gender;
    @Column(name = "BIRTH_DATE") private LocalDate birthDate;

    @Column(name = "JOINING_DATE") private LocalDate joiningDate;
    @Column(name = "MEMBERSHIP_END_DATE") private LocalDate membershipEndDate;

    public Customer () {
        _id = UUID.randomUUID ().toString ();
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
    public long daysFromJoining () {
        return daysFromJoining (LocalDate.now ());
    }

    /**
     * Returns a positive long integer of the number of days past the joining date.
     * Returns a negative long integer if the joining date is in the future.
     *
     * @param ref Reference date.
     * @return Number of days past the provided date.
     */
    public long daysFromJoining (LocalDate ref) {
        return ChronoUnit.DAYS.between (joiningDate, ref);
    }

    /**
     * Returns a positive long integer if today's date is before end of membership.
     * Returns a negative long integer if the membership date has passed.
     *
     * @return Number of days till the end date of membership from current date.
     */
    public long daysTillEnd () {
        return daysTillEnd (LocalDate.now ());
    }

    /**
     * Returns a positive long integer if the reference date is before end of membership.
     * Returns a negative long integer if the membership date has passed.
     *
     * @param ref Reference date.
     * @return Number of days till the end date of membership from reference date.
     */
    public long daysTillEnd (LocalDate ref) {
        return ChronoUnit.DAYS.between (ref, membershipEndDate);
    }

    // -----

    @Override
    public String toString () {
        return String.format ("%s %s", firstName, lastName);
    }

    // -----

    public void set_id (String _id) {
        this._id = _id;
    }

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


    public String get_id () {
        return _id;
    }

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
