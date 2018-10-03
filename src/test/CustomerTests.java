import database.models.Customer;

import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.Month;

public class CustomerTests {


    @Test
    public void testCreation () {
        final Customer tester = TestUtility.createCustomer ();
    }

    // TODO: FIX
//    @Test
//    public void testDaysFromJoining () {
//        final Customer tester = TestUtility.createCustomer ();
//
//        assertEquals (
//                "Days from joining date are not the same",
//                TestUtility.NUMBER_OF_DAYS,
//                tester.daysFromJoining (TestUtility.JOININGDATE.plusDays (TestUtility.NUMBER_OF_DAYS))
//        );
//    }
//
//    @Test
//    public void testDaysTillEndDate () {
//        final Customer tester = TestUtility.createCustomer ();
//
//        assertEquals (
//                "\"Days till end date are not the same\"",
//                TestUtility.NUMBER_OF_DAYS,
//                tester.daysTillEnd (TestUtility.JOININGDATE)
//        );
//    }
//
    @Test
    public void addPackage () {
        Customer c = TestUtility.createCustomer ();
        LocalDate prevDate = c.getMembershipEndDate ();
        int days = (int) Math.round (Math.random () * 1000);

        c.addDaysToMembership (days);

        assertTrue (
                "Days not added correctly.",
                prevDate.plusDays (days).isEqual (c.getMembershipEndDate ())
        );
    }

    @Test
    public void testDaysTillBirthday () {
        final Customer tester = TestUtility.createCustomer ();
        // Todo: Write
    }

}
