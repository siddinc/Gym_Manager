import database.models.Customer;

import database.models.Payment;

import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

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

    private Payment createPayment () {
        Payment p = new Payment ();

        p.setAmount ( (Math.random () * 10000) + 1);
        p.setDaysAddition ( (int) Math.floor (Math.random () * 10 + 1) );
        p.setPayDate (LocalDate.now ().plusDays ( (int) Math.floor (Math.random () * 100 + 1) ));

        return p;
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

    @Test
    public void addPaymentSingle () {
        final Customer tester = createCustomer ();
        final String msg = "Failure - Payments history didn't work as expected";

        final Payment p = createPayment ();

        tester.addPayment (p);
        assertTrue (msg, tester.getPaymentsHistory ().contains (p));
        assertEquals (msg, 1, tester.getPaymentsHistory ().size ());
    }

    @Test
    public void addPaymentMultiple () {
        final Customer tester = createCustomer ();
        final String msg_not_same = "Failure - Payment not same";
        final String msg_excess = "Failure - Customer has extra payments";

        List<Payment> pmts = new ArrayList<Payment> ();
        for (int i = 0; i < 10; i++) {
            Payment p = createPayment ();
            pmts.add (p);
            tester.addPayment (p);
        }

        ListIterator<Payment>
                expected = pmts.listIterator (),
                actual = tester.getPaymentsHistory ().listIterator ();

        assertEquals (msg_excess, pmts.size (), tester.getPaymentsHistory ().size ());

        while (expected.hasNext ()) {
            assertTrue (msg_not_same, expected.next ().equals (actual.next ()));
        }
    }

}
