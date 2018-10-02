import database.DataHandler;
import database.models.Customer;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class DatabaseTests {

    private static DataHandler dh;
    private static List<Customer> dummies;

    @BeforeClass
    public static void setup () throws SQLException {
        final int dummyCount = 10;

        dh = new DataHandler (String.format (
                "jdbc:sqlite:test%d.db",
                new Date ().getTime ()
        ));
//        dh = new DataHandler ();

        dummies = new ArrayList<Customer> (dummyCount);
        for (int i = 0; i < dummyCount; i++)
            dummies.add (TestUtility.createCustomer ());
    }

    @AfterClass
    public static void endup () throws IOException {
        dh.close ();
    }

    // -----

    @Test
    public void createTable () throws SQLException {
        dh.createTable ();
    }

    @Test
    public void addCustomer () throws SQLException {
        for (Customer e : dummies)
            dh.addCustomer (e);
    }

    @Test
    public void getList () throws SQLException {
        List<Customer> ls = dh.getList ();

        assertEquals (
                "Data list is wrong.",
                dummies.size (),
                ls.size ()
        );

        for (Customer c : ls)
            System.out.println (c);
    }

    @Test
    public void getCustomer () throws SQLException {
        // * Depends on the fact that getList test did not fail.
        List<Customer> customerList = dh.getList ();
        for (Customer d : customerList) {
            Customer customer = dh.getById (d.getId ());
            String msg = "Object not same.";

            assertNotNull("Returned null for ID " + d.getId (), customer);
            assertEquals (msg, d.getId (), customer.getId ());
            assertEquals (msg, d.getGender (), customer.getGender ());
            assertTrue   (msg, d.getFirstName ().equals (customer.getFirstName ()));
            assertTrue   (msg, d.getLastName ().equals (customer.getLastName ()));
            assertTrue   (msg, d.getBirthDate ().isEqual (customer.getBirthDate ()));
            assertTrue   (msg, d.getJoiningDate ().isEqual (customer.getJoiningDate ()));
            assertTrue   (msg, d.getMembershipEndDate ().isEqual (customer.getMembershipEndDate ()));
        }
    }

    // NOTE: Updating can refer to any sort of member update.
    @Test
    public void updateCustomer () throws SQLException {
        List<Customer> customerList;
        Customer c;

        // Get the first customer.
        customerList = dh.getList ();
        c = customerList.get (0);
        System.out.println ("BEFORE: ");
        System.out.println (c);

        // Create an entirely new customer but preserve UUID.
        String previousId = c.getId ();
        c = TestUtility.createCustomer ();
        c.setId (previousId);

        // Update
        dh.updateCustomer (c);

        // Check the list again.
        c = dh.getById (previousId);
        System.out.println ("AFTER: ");
        System.out.println (c);

        // Assertions.
        assertEquals ("ID not the same!", previousId, c.getId ());
    }

    @Test
    public void addPackageToCustomer () throws SQLException {
        List<Customer> customerList = dh.getList ();;

        customerList.stream ().forEach (element -> {
            try {
                // Store previous date.
                LocalDate previousDate = element.getMembershipEndDate ();
                // Add package to POJO.
                int days = (int) Math.round (Math.random () * 100);
                element.addDaysToMembership (days);
                // Update database.
                dh.updateCustomer (element);
                // Get new customer record from database.
                Customer c = dh.getById (element.getId ());
                // Assertions.
                assertTrue (
                        "Days added not the same!",
                        element.getMembershipEndDate ().isEqual (previousDate.plusDays (days))
                );
            } catch (SQLException e) {
                e.printStackTrace ();
            }
        });
    }

    @Test
    public void deleteCustomer () throws SQLException {
        List<Customer> cs = dh.getList ();
        for (Customer d: cs)
            dh.deleteById (d.getId ());

        cs = dh.getList ();
        assertEquals (
                "Not deleted.",
                0,
                cs.size ()
        );
    }

    @Test
    public void dropTable () throws SQLException {
        dh.dropTable ();
    }

}
