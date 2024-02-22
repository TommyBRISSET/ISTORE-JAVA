package principal;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.*;

/**
 * This class provides JUnit tests for the {@link listPersonAcess} class.
 * It ensures the proper initialization of the class and validates the behavior
 * of the {@code createUsersTable} method.
 *
 * @author Tommy Brisset
 * @version 1.0
 * @see listPersonAcess
 * @see JFrame
 */
public class listPersonAcessTest {

    /**
     * Default constructor.
     */
    public listPersonAcessTest() {
    }

    /**
     * Tests the initialization of the {@link listPersonAcess} class.
     * Verifies that creating an instance does not throw any exceptions.
     */
    @Test
    public void ListPersonAcessInit() {
        assertDoesNotThrow(() -> {
            SwingUtilities.invokeLater(() -> new listPersonAcess(1));
        });
    }

    /**
     * Tests the behavior of the {@code createUsersTable} method in the {@link listPersonAcess} class.
     * Verifies that the method executes without exceptions, and the resulting {@link JTable} is not null,
     * has columns, and has rows.
     */
    @Test
    public void CreateUsersTable() {
        listPersonAcess listAcess = new listPersonAcess(1);
        assertDoesNotThrow(listAcess::createUsersTable);
        JTable usersTable = listAcess.getUsersTable();
        assertNotNull(usersTable);

        int columnCount = usersTable.getColumnCount();
        assertTrue(columnCount > 0);

        int rowCount = usersTable.getRowCount();
        assertTrue(rowCount > 0);
    }
}
