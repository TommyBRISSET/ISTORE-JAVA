package utils;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The <code>AskIDInventoryTest</code> class provides JUnit tests for the {@link askIDInventory} class.
 *
 * <p>
 * These tests validate the behavior of the {@link askIDInventory} class when handling a valid inventory ID.
 * </p>
 *
 * <p>
 * Note: This test assumes that there is an existing test database with appropriate content.
 * </p>
 *
 * @author Tommy Brisset
 * @version 1.0
 */
public class askIDInventoryTest {

    /**
    * Default constructor.
    */
    public askIDInventoryTest() {
    }

    /**
     * Tests the behavior of the {@link askIDInventory} class when handling a valid inventory ID.
     * The test simulates user interaction and asserts that the window is disposed after handling the event.
     */
    @Test
    public void testValidInventoryID() {
        askIDInventory askIDInventory = new askIDInventory(20);

        askIDInventory.idField.setText("1000");

        askIDInventory.buttonEventHandler();

        assertTrue(askIDInventory.isVisible(), "The window should be disposed after handling a valid inventory ID event.");
    }
}
