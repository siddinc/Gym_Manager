import database.models.Customer;

import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.Month;

public class CustomerTests {

    private static String
        FIRSTNAME = "ABCDEF",
        LASTNAME = "UVWXYZ";

    private static Customer.Gender
        GENDER = Customer.Gender.OTHER;

    private static int
            NUMBER_OF_DAYS = 100;

    private static LocalDate
        BIRTHDATE = LocalDate.of (1999, Month.SEPTEMBER, 30),
        JOININGDATE = LocalDate.now (),
        MEMBERSHIPENDDATE = JOININGDATE.plusDays (NUMBER_OF_DAYS);

    // ---

    private Customer createCustomer () {
        Customer tester = new Customer ();

        tester.setFirstName (FIRSTNAME);
        tester.setLastName (LASTNAME);
        tester.setGender (GENDER);
        tester.setBirthDate (BIRTHDATE);

        tester.setJoiningDate (JOININGDATE);
        tester.setMembershipEndDate (MEMBERSHIPENDDATE);

        return tester;
    }

    // ---

    @Test
    public void testCreation () {
        final Customer tester = createCustomer ();
    }

    @Test
    public void testDaysFromJoining () {
        final Customer tester = createCustomer ();
        final String msg = "Failure - Days from joining date are not the same";

        assertEquals (msg, NUMBER_OF_DAYS, tester.daysFromJoining (JOININGDATE.plusDays (NUMBER_OF_DAYS)));
    }

    @Test
    public void testDaysTillEndDate () {
        final Customer tester = createCustomer ();
        final String msg = "Failure - Days till end date are not the same";

        assertEquals (msg, NUMBER_OF_DAYS, tester.daysTillEnd (JOININGDATE));
    }

}
