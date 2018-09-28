package database;

/**
 * @author vikrant
 */
public final class Query {

    public static final String
    CREATE = (
            "CREATE TABLE IF NOT EXIST " +
                    "customers" +
                    " ( " +

                    "id varchar(36) PRIMARY KEY," +
                    "firstname text," +
                    "lastname text," + // can do NOT NULL

                    "gender text," +

                    "birthDate blob," +
                    "joiningDate blob," +
                    "membershipEndDate blob" +

                    " ); "
    ),

    INSERT = (
            "INSERT INTO " + "customers" + " ( " + "?, ?, ?, ?, ?, ?" + " ); "
    ),

    LIST = (
            "SELECT * FROM customers;"
    ),

    GET = (
            "SELECT FROM customers WHERE id = ?;"
    ),

    UPDATE = (
            "UPDATE" +
                    "customers" +

                    " SET " +
                    "firstname = ?," +
                    "lastname = ?," +
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
    )
            ;

    private Query () {
        /*
            Object creation disallowed. (Abstract)
         */
    }
}
