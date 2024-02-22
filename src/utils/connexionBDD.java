package utils;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The <code>connexionBDD</code> class provides methods to test and obtain a connection to a MySQL database.
 *
 * <p>
 * To use this class, make sure to have the MySQL JDBC driver (com.mysql.cj.jdbc.Driver) in the classpath of your project.
 * </p>
 *
 * @author Tommy Brisset
 * @version 1.0
 */
public class connexionBDD {

    /**
     * Default constructor.
     */
    public connexionBDD() {
    }

    private static final String URL = "jdbc:mysql://localhost:3306/istore";

    private static final String UTILISATEUR = "root";

    private static final String MOT_DE_PASSE = "";

    /**
     * Tests the connection to the database.
     *
     * @return <code>true</code> if the connection is successful, <code>false</code> otherwise.
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
     * Obtains a connection to the database.
     *
     * @return The connection to the database.
     * @throws SQLException If an error occurs while obtaining the connection.
     */
    public static Connection obtenirConnexion() throws SQLException {
        return DriverManager.getConnection(URL, UTILISATEUR, MOT_DE_PASSE);
    }

    /**
     * Tests the {@link #testerConnexion()} Connection()} method.
     */
    @Test
    public void testTesterConnexion() {
        assertTrue(testerConnexion(), "La connexion devrait réussir.");
    }

    /**
     * Tests the {@link #obtenirConnexion()} method.
     */
    @Test
    public void testObtenirConnexion() {
        assertDoesNotThrow(() -> {
            try (Connection connection = obtenirConnexion()) {
                assertNotNull(connection, "La connexion ne devrait pas être nulle.");
            }
        }, "L'obtention de la connexion ne devrait pas générer d'exception.");
    }
}
