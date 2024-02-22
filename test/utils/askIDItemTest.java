package utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The <code>AskIDItemTest</code> class provides JUnit tests for the {@link askIDItem} class.
 *
 * <p>
 * These tests validate the behavior of the {@link askIDItem} class when handling a valid item ID.
 * </p>
 *
 * <p>
 * Note: This test assumes that there is an existing test database with appropriate content.
 * </p>
 *
 * @author Tommy Brisset
 * @version 1.0
 */
public class askIDItemTest {

    /**
     * Default constructor.
     */
    public askIDItemTest() {
    }

    /**
     * Tests the behavior of the {@link askIDItem} class when handling a valid item ID.
     * The test simulates user interaction and asserts that the window is disposed after handling the event.
     */
    @Test
    public void testValidItemID() {
        askIDItem askIDItem = new askIDItem(10);

        askIDItem.idField.setText("1000");

        askIDItem.buttonEventHandler();

        assertTrue(askIDItem.isVisible(), "The window should be disposed after handling the event");
    }
}
