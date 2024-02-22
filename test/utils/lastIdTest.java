package utils;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The <code>LastIdTest</code> class provides JUnit tests for the {@link LastID} class.
 *
 * <p>
 * These tests validate the functionality of obtaining the last ID of a specified table in a database.
 * </p>
 *
 * <p>
 * To use this class, ensure that you have the MySQL JDBC driver (com.mysql.cj.jdbc.Driver) in your project's classpath.
 * Also, make sure to have a test database set up with the required table structure.
 * </p>
 *
 * <p>
 * Note: These tests assume that the specified table has an 'id' column that is auto-incremented.
 * </p>
 *
 * @author Tommy Brisset
 * @version 1.0
 */
public class lastIdTest {

    /**
     * Default constructor.
     */
    public lastIdTest() {
    }

    private static final String URL = "jdbc:mysql://localhost:3306/istore_test";
    private static final String UTILISATEUR = "root";
    private static final String MOT_DE_PASSE = "";

    /**
     * Tests the {@link LastID#obtenirDernierID(Connection, String)} method.
     * Obtains the last ID of the 'user' table and verifies that it is greater than or equal to 1.
     */
    @Test
    public void testObtenirDernierID() {
        try (Connection connection = DriverManager.getConnection(URL, UTILISATEUR, MOT_DE_PASSE)) {
            String tableName = "user";

            int lastID = LastID.obtenirDernierID(connection, tableName);

            assertTrue(lastID >= 1, "Le dernier ID devrait être supérieur ou égal à 1.");

        } catch (SQLException e) {
            e.printStackTrace(System.out);
            fail("Une exception SQL ne devrait pas être levée ici.");
        }
    }
}
