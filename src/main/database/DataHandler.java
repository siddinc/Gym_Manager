package database;

import database.models.Customer;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handler provides a simplified interface for interacting with the database.
 *
 * @author vikrant
 */
public class DataHandler implements Closeable {

    // TODO: Remove hardcoded URL to Properties.
    protected static final String DATABASE_URL = "jdbc:sqlite:gym.db";

    protected Connection conn;

    private PreparedStatement
        createStm,
        insertStm,
        listStm,
        getStm,
        updateStm,
        deleteStm,
        dropStm;

    public DataHandler() throws SQLException {
        this(DATABASE_URL);
    }

    public DataHandler(String url) throws SQLException {
        conn = DriverManager.getConnection (url);

        createStm = conn.prepareStatement (Query.CREATE);
        // Create Table.
        createStm.execute ();

        insertStm = conn.prepareStatement (Query.INSERT);
        listStm   = conn.prepareStatement (Query.LIST);
        getStm    = conn.prepareStatement (Query.GET);
        updateStm = conn.prepareStatement (Query.UPDATE);
        deleteStm = conn.prepareStatement (Query.DELETE);
        dropStm   = conn.prepareStatement (Query.DROP);
    }

    // -----

    @Override
    public void close () throws IOException {
        try {
            conn.close ();
        } catch (SQLException e) {
            e.printStackTrace ();
            throw new IOException (e);
        }
    }

    // -----

    /**
     * Create SQLite table.
     * @throws SQLException
     */
    public void createTable () throws SQLException {
        createStm.execute ();
    }

    /**
     * @param customer Customer POJO to add to SQLite database.
     */
    public void addCustomer (Customer customer) throws SQLException {
        insertStm.setString (1, customer.getId ());
        insertStm.setString (2, customer.getFirstName ());
        insertStm.setString (3, customer.getLastName ());
        insertStm.setString (4, customer.getGender ().toString ());
        insertStm.setDate   (5, Date.valueOf (customer.getBirthDate ()));
        insertStm.setDate   (6, Date.valueOf (customer.getJoiningDate ()));
        insertStm.setDate   (7, Date.valueOf (customer.getMembershipEndDate ()));

        insertStm.executeUpdate ();
    }

    /**
     * @return List of Customer POJOs from database.
     */
    public List<Customer> getList () throws SQLException {
        List<Customer> customerList = new ArrayList<Customer> ();

        ResultSet resultSet = listStm.executeQuery ();
        while (resultSet.next ()) {
            Customer c = Customer.fromResultSet (resultSet);
            customerList.add (c);
        }
        resultSet.close ();

        return customerList;
    }

    /**
     * @param id UUID of the customer to retrieve
     * @return Customer POJO from the database.
     * @throws SQLException
     */
    public Customer getById (String id) throws SQLException {
        getStm.setString (1, id);
        ResultSet resultSet = getStm.executeQuery ();
        Customer c = null;
        if (resultSet.next ())
             c = Customer.fromResultSet (resultSet);
        return c;
    }

    /**
     * @param customer Customer record to update.
     * @throws SQLException
     */
    public void updateCustomer (Customer customer) throws SQLException {
        updateStm.setString (1, customer.getFirstName ());
        updateStm.setString (2, customer.getLastName ());
        updateStm.setString (3, customer.getGender ().toString ());
        updateStm.setDate   (4, Date.valueOf (customer.getBirthDate ()));
        updateStm.setDate   (5, Date.valueOf (customer.getJoiningDate ()));
        updateStm.setDate   (6, Date.valueOf (customer.getMembershipEndDate ()));
        updateStm.setString (7, customer.getId ());

        updateStm.executeUpdate ();
    }

    /**
     * @param id UUID of the customer to be deleted.
     * @throws SQLException
     */
    public void deleteById (String id) throws SQLException {
        deleteStm.setString (1, id);
        deleteStm.executeUpdate ();
    }

    /**
     * Delete the whole table.
     * NOTE: After dropping table be sure to create a new table before any operations.
     * @throws SQLException
     */
    public void dropTable () throws SQLException {
        dropStm.execute ();
    }

}
