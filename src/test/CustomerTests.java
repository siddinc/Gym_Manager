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

    @Test
    public void testDaysFromJoining () {
        final Customer tester = TestUtility.createCustomer ();

        assertEquals (
                TestUtility.errorMessage ("Days from joining date are not the same"),
                TestUtility.NUMBER_OF_DAYS,
                tester.daysFromJoining (TestUtility.JOININGDATE.plusDays (TestUtility.NUMBER_OF_DAYS))
        );
    }

    @Test
    public void testDaysTillEndDate () {
        final Customer tester = TestUtility.createCustomer ();

        assertEquals (
                TestUtility.errorMessage ("\"Days till end date are not the same\""),
                TestUtility.NUMBER_OF_DAYS,
                tester.daysTillEnd (TestUtility.JOININGDATE)
        );
    }

}
