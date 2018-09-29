import database.DataHandler;
import database.models.Customer;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class DatabaseTests {

    private static DataHandler dh;
    private static Customer singleCustomerTester;

    @BeforeClass
    public static void setup () throws SQLException {
        dh = new DataHandler ();
        singleCustomerTester = TestUtility.createCustomer ();
        System.out.println ("Created the following single tester: ");
        System.out.println (singleCustomerTester);
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
        dh.addCustomer (singleCustomerTester);
    }

    @Test
    public void getList () throws SQLException {
        List<Customer> ls = dh.getList ();

        System.out.println ("All the records: ");
        for (Customer c : ls) {
            System.out.println (c);
        }

        assertEquals (
                TestUtility.errorMessage ("Data list is wrong."),
                1,
                ls.size ()
        );
    }

    @Test
    public void getCustomer () throws SQLException {
        Customer customer = dh.getById (singleCustomerTester.getId ());
        String msg = TestUtility.errorMessage ("Object not same.");

        assertNotNull (customer);

        assertEquals (singleCustomerTester.getId (), customer.getId ());
        assertTrue (singleCustomerTester.getFirstName ().equals (customer.getFirstName ()));
        assertTrue (singleCustomerTester.getLastName ().equals (customer.getLastName ()));
        assertEquals (singleCustomerTester.getGender (), customer.getGender ());

        assertTrue (singleCustomerTester.getBirthDate ().isEqual (customer.getBirthDate ()));
        assertTrue (singleCustomerTester.getJoiningDate ().isEqual (customer.getJoiningDate ()));
        assertTrue (singleCustomerTester.getMembershipEndDate ().isEqual (customer.getMembershipEndDate ()));
    }

    @Test
    public void updateCustomer () throws SQLException {

    }

    @Test
    public void deleteCustomer () throws SQLException {
        dh.deleteById (singleCustomerTester.getId ());
    }

    @Test
    @Ignore
    public void dropTable () throws SQLException {
        dh.dropTable ();
    }

}
