package database;

import database.models.Customer;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Handler provides a simplified interface for interacting with the database.
 *
 * @author vikrant
 */
public class DataHandler {
    // TODO: Remove hardcoded URL to Properties.
    private final String DATABASE_URL = "jdbc:sqlite:gym.sqlite3";

    private PreparedStatement
        createStm,
        insertStm,
        listStm,
        getStm,
        updateStm,
        deleteStm;

    public DataHandler() {
        try (Connection conn = DriverManager.getConnection (DATABASE_URL)) {
            // Get statements.
            createStm = conn.prepareStatement (Query.CREATE);
            insertStm = conn.prepareStatement (Query.INSERT);
            listStm   = conn.prepareStatement (Query.LIST);
            getStm    = conn.prepareStatement (Query.GET);
            updateStm = conn.prepareStatement (Query.UPDATE);
            deleteStm = conn.prepareStatement (Query.DELETE);

            // Create Table.
            createStm.executeUpdate ();
        } catch (SQLException e) {
            System.err.println (e);
            e.printStackTrace ();
        };
    }

    /**
     * @return List of Customer POJOs from database.
     */
    public List<Customer> getList () {
        List<Customer> customerList = new ArrayList<Customer> ();

        try (ResultSet resultSet = listStm.executeQuery ()) {
            while (resultSet.next ()) {
                Customer c = new Customer ();
                c.fromResultSet (resultSet);
                customerList.add (c);
            }
        } catch (SQLException e) {
            System.err.println(e);
            e.printStackTrace ();
        }

        return customerList;
    }

    /**
     * @param customer Customer POJO to add to SQLite database.
     */
    public void addCustomer (Customer customer) {
        try {
            insertStm.setString (1, customer.getId ());
            insertStm.setString (2, customer.getFirstName ());
            insertStm.setString (3, customer.getLastName ());
            insertStm.setString (4, customer.getGender ().toString ());
            insertStm.setDate (5, Date.valueOf (customer.getBirthDate ()));
            insertStm.setDate (6, Date.valueOf (customer.getJoiningDate ()));
            insertStm.setDate (7, Date.valueOf (customer.getMembershipEndDate ()));

            insertStm.executeUpdate ();
        } catch (SQLException e) {
            System.err.println (e);
            e.printStackTrace ();
        }
    }
}
