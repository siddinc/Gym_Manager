package database.models;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Customer {
    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate birthDate;

    private LocalDate joiningDate;
    private LocalDate membershipEndDate;

    public Customer () {
        joiningDate = LocalDate.now ();
        birthDate = LocalDate.now ();
        membershipEndDate = LocalDate.now ();
    }

    // -----

    public long calculateDaysFromJoining () {
        return calculateDaysFromJoining (LocalDate.now ());
    }

    public long calculateDaysFromJoining (LocalDate ref) {
        return ChronoUnit.DAYS.between (ref, joiningDate);
    }

    public long calculateDaysTillEnd () {
        return calculateDaysTillEnd (LocalDate.now ());
    }

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

    public static enum Gender {
        MALE,
        FEMALE,
        OTHER;
    }
}
