package database;

import database.models.Customer;

import java.sql.*;

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
            System.err.println ();
        };
    }
}
