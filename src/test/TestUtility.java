import database.models.Customer;

import java.time.LocalDate;
import java.time.Month;

public abstract class TestUtility {
    public static String
            FIRSTNAME = "ABCDEF",
            LASTNAME = "UVWXYZ";

    public static Customer.Gender
            GENDER = Customer.Gender.OTHER;

    public static int
            NUMBER_OF_DAYS = 100;

    public static LocalDate
            BIRTHDATE = LocalDate.of (1999, Month.SEPTEMBER, 30),
            JOININGDATE = LocalDate.now (),
            MEMBERSHIPENDDATE = JOININGDATE.plusDays (NUMBER_OF_DAYS);

    public static Customer createCustomer () {
        Customer tester = new Customer ();

        tester.setFirstName (FIRSTNAME);
        tester.setLastName (LASTNAME);
        tester.setGender (GENDER);

        tester.setBirthDate (BIRTHDATE);
        tester.setJoiningDate (JOININGDATE);
        tester.setMembershipEndDate (MEMBERSHIPENDDATE);

        return tester;
    }

    // -----

    public static String errorMessage (String msg) {
        return String.format (
                "Failure - %s",
                msg
        );
    }
}
