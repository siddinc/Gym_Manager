package database.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.util.Vector;

/**
 * Customer POJO model to be saved in database.
 *
 * @author vikrant
 */
public class Customer {
    private String id;
    private String firstName;
    private String lastName;

    private Gender gender;

    private LocalDate birthDate;
    private LocalDate joiningDate;
    private LocalDate membershipEndDate;

    public Customer () {
        id = UUID.randomUUID ().toString ();

        // Default values.
        firstName = "John";
        lastName = "Smith";
        gender = Gender.OTHER;
        birthDate = LocalDate.now ();
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

    /**
     * Returns a positive long if today's date is before birthdate.
     * Returns a negative long if the birthdate has passed.
     *
     * @return Number of days till the user's birthday.
     */
    public long daysTillBirthday () {
        return daysTillBirthday (LocalDate.now ());
    }

    /**
     * Returns a positive long if reference date is before birthdate.
     * Returns a negative long if the birthdate has passed.
     *
     * @param ref Reference date.
     * @return Number of days till the user's birthday.
     */
    public long daysTillBirthday (LocalDate ref) {
        return MonthDay.from (birthDate).compareTo (MonthDay.from (ref));
    }

    /**
     * @param days Number of days to add to Customer's membership.
     */
    public void addDaysToMembership (long days) {
        membershipEndDate = membershipEndDate.plusDays (days);
    }

    // -----

    @Override
    public String toString () {
        return String.format ("%s: %s %s\n Born %s\n Join %s\n End %s\n *", id, firstName, lastName, birthDate, joiningDate, membershipEndDate);
    }

    // -----

    public void setId (String id) {
        this.id = id;
    }

    public void setFirstName (String firstName) {
        this.firstName = firstName;
    }

    public void setLastName (String lastName) {
        this.lastName = lastName;
    }

    public void setGender (Gender gender) {
        this.gender = gender;
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


    public String getId () {
        return id;
    }

    public String getFirstName () {
        return firstName;
    }

    public String getLastName () {
        return lastName;
    }

    public Gender getGender () {
        return gender;
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
     * @apiNote UNSAFE
     * @param resultSet result set to extract data from.
     * @return Generated customer.
     */
    public static Customer fromResultSet (ResultSet resultSet) {
        try {
            Customer c = null;
            c = new Customer ();
            c.id = resultSet.getString ("id");
            c.firstName = resultSet.getString ("firstname");
            c.lastName = resultSet.getString ("lastname");
            c.gender = Customer.Gender.valueOf (resultSet.getString ("gender"));
            c.birthDate = resultSet.getDate ("birthDate").toLocalDate ();
            c.joiningDate = resultSet.getDate ("joiningDate").toLocalDate ();
            c.membershipEndDate = resultSet.getDate ("membershipEndDate").toLocalDate ();
            return c;
        } catch (SQLException e) {
            System.err.println (e);
            e.printStackTrace ();
        }
        return null;
    }

    /**
     * @return Object converted to Object[] array for easy conversion to JTable row.
     */
    public Vector toVector () {
        Vector v = new Vector ();
        v.add (id);
        v.add (firstName);
        v.add (lastName);
        v.add (gender.toString ());
        v.add (birthDate);
        v.add (joiningDate);
        v.add (membershipEndDate);

        return v;
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
