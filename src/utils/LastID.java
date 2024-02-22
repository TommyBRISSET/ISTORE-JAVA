package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The <code>LastID</code> class provides a method to obtain the last ID of a table in a database.
 *
 * <p>
 * This class is used to retrieve the last ID of a specified table based on the maximum value of the 'id' column in that table.
 * </p>
 *
 * <p>
 * To use this class, you must provide an active connection to the database and the name of the table from which you want to obtain the last ID.
 * </p>
 *
 * <p>
 * If the specified table contains no records, the method returns 1 as the default value.
 * In case of an error during the execution of the SQL query, the method returns -1 and prints the details of the exception.
 * </p>
 *
 * <p>
 * Example of usage:
 * </p>
 *
 * <pre>
 * Connection connection = // obtain a connection to the database
 * String tableName = "myTable";
 * int lastID = LastID.getLastID(connection, tableName);
 * System.out.println("The last ID of table " + tableName + " is: " + lastID);
 * </pre>
 *
 * <p>
 * Note: It is recommended to use this class with caution to avoid any negative impact on database performance.
 * </p>
 *
 * @author Tommy Brisset
 * @version 1.0
 */
public class LastID {

    /**
     * Default constructor.
     */
    public LastID() {
    }

    /**
     * Gets the last ID of a table in a database.
     *
     * @param connexion The active connection to the database.
     * @param tableName The name of the table from which to obtain the last ID.
     * @return The last ID of the specified table.
     *         If the table is empty, returns 1 as the default value.
     *         In case of a SQL error, returns -1.
     */
    public static int obtenirDernierID(Connection connexion, String tableName) {
        String sql = "SELECT MAX(id) FROM " + tableName;

        try (PreparedStatement preparedStatement = connexion.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1) + 1;
            } else {
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            return -1;
        }
    }
}
