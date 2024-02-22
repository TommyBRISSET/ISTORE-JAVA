package utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * The <code>connectionBDDTest</code> class provides methods for testing
 * and obtain a connection to a MySQL database.
 *
 * <p>
 * To use this class, make sure you have the MySQL JDBC driver
 * (com.mysql.cj.jdbc.Driver) in your project's class path.
 * </p>
 *
 * @author Tommy Brisset
 * @version 1.0
 */
public class connexionbddtest {

    /**
     * Default constructor.
     */
    public connexionbddtest() {
    }

    private static final String URL = "jdbc:mysql://localhost:3306/istore_test";

    private static final String UTILISATEUR = "root";

    private static final String MOT_DE_PASSE = "";

    private static Connection connection;


    /**
     * Tests database connection.
     *
     * @return <code>true</code> if the connection is successful; <code>false</code> otherwise.
     */
    public static boolean testerConnexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection connexion = DriverManager.getConnection(URL, UTILISATEUR, MOT_DE_PASSE)) {
                return true;
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(System.out);
            return false;
        }
    }

    /**
     * Gets a connection to the database.
     *
     * @return A <code>Connection</code> object representing the database connection.
     * @throws SQLException If an error occurs while trying to connect.
     */
    public static Connection obtenirConnexion() throws SQLException {
        return DriverManager.getConnection(URL, UTILISATEUR, MOT_DE_PASSE);
    }

    /**
     * Set up the resources required for the tests.
     *
     * @throws SQLException If an error occurs while trying to connect.
     */
    @BeforeAll
    public static void setUp() throws SQLException {
        // Initialisation des ressources nécessaires avant les tests
        connection = connexionbddtest.obtenirConnexion();
    }

    /**
     * Clean up the resources after the tests.
     *
     * @throws SQLException If an error occurs while trying to close the connection.
     */
    @AfterAll
    public static void tearDown() throws SQLException {
        // Nettoyage des ressources après les tests
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    /**
     * Test the method {@link #testerConnexion()}.
     */
    @Test
    public void testTesterConnexion() {
        assertTrue(testerConnexion(), "La connexion devrait réussir.");
    }

    /**
     * Test the method {@link #obtenirConnexion()}.
     */
    @Test
    public void testObtenirConnexion() {
        assertDoesNotThrow(() -> {
            try (Connection connection = obtenirConnexion()) {
                assertNotNull(connection, "La connexion ne devrait pas être nulle.");
            }
        }, "L'obtention de la connexion ne devrait pas générer d'exception.");
    }

    /**
     * Test the validity of the connection.
     */
    @Test
    public void testConnexionEstValide() {
        assertDoesNotThrow(() -> {
            assertTrue(connection.isValid(5), "La connexion devrait être valide.");
        }, "La validation de la connexion ne devrait pas générer d'exception.");
    }
}
