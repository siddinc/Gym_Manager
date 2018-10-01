import database.DataHandler;
import database.models.Customer;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DatabaseTests {

    private static DataHandler dh;
    private static List<Customer> dummies;

    @BeforeClass
    public static void setup () throws SQLException {
        final int dummyCount = 10;

        dh = new DataHandler ();

        dummies = new ArrayList<Customer> (dummyCount);
        for (int i = 0; i < dummyCount; i++)
            dummies.add (TestUtility.createCustomer ());
    }

    @AfterClass
    public static void endup () throws SQLException {
        dh.closeConnection ();
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
                TestUtility.errorMessage ("Data list is wrong."),
                dummies.size (),
                ls.size ()
        );

        for (Customer c : ls)
            System.out.println (c);
    }

    @Test
    @Ignore // TODO: Remove <<<<<---------- ignore
    public void getCustomer () throws SQLException {
        for (Customer d : dummies) {
            Customer customer = dh.getById (d.getId ());
            String msg = TestUtility.errorMessage ("Object not same.");

            assertNotNull (customer);

            assertEquals (d.getId (), customer.getId ());
            assertTrue (d.getFirstName ().equals (customer.getFirstName ()));
            assertTrue (d.getLastName ().equals (customer.getLastName ()));
            assertEquals (d.getGender (), customer.getGender ());

            assertTrue (d.getBirthDate ().isEqual (customer.getBirthDate ()));
            assertTrue (d.getJoiningDate ().isEqual (customer.getJoiningDate ()));
            assertTrue (d.getMembershipEndDate ().isEqual (customer.getMembershipEndDate ()));
        }
    }

    @Test
    @Ignore // TODO: Remove <<<<<----- ignore
    public void updateCustomer () throws SQLException {
        // TODO: Work.
    }

    @Test
    public void deleteCustomer () throws SQLException {
        List<Customer> cs = dh.getList ();
        assertEquals (
                TestUtility.errorMessage ("Weird, wrong number of dummies."),
                dummies.size (),
                cs.size ()
        );

        for (Customer d: cs)
            dh.deleteById (d.getId ());

        cs = dh.getList ();
        assertEquals (
                TestUtility.errorMessage ("Not deleted."),
                0,
                cs.size ()
        );
    }

    @Test
    @Ignore
    public void dropTable () throws SQLException {
        dh.dropTable ();
    }

}
