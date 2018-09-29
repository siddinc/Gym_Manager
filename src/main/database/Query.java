package database;

/**
 * @author vikrant
 */
public final class Query {

    public static final String
    CREATE = (
            "CREATE TABLE IF NOT EXISTS " +
                    "customers" +
                    " ( " +

                    "id varchar(36) PRIMARY KEY," +
                    "firstName text," +
                    "lastName text," + // can do NOT NULL

                    "gender text," +

                    "birthDate blob," +
                    "joiningDate blob," +
                    "membershipEndDate blob" +

                    " ); "
    ),

    INSERT = (
            "INSERT INTO " +
                    "customers" +
                    " VALUES ( " +
                    "?, ?, ?, ?, ?, ?, ?" +
                    " ); "
    ),

    LIST = (
            "SELECT * FROM customers;"
    ),

    GET = (
            "SELECT DISTINCT * FROM customers" +
                    " WHERE " +
                    "id = ?;"
    ),

    UPDATE = (
            "UPDATE " +
                    "customers" +
                    " SET " +
                    "firstName = ?," +
                    "lastName = ?," +
                    "gender = ?," +
                    "birthDate = ?," +
                    "joiningDate = ?," +
                    "membershipEndDate = ?" +

                    " WHERE " +
                    "id = ?; +; "
    ),

    DELETE = (
            "DELETE FROM customers WHERE id = ?;"
    ),

    DROP = (
            "DELETE FROM customers;"
    );

    private Query () {
        /*
            Object creation disallowed. (Abstract)
         */
    }
}
