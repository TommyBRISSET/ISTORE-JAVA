package utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The <code>AskIDStoreTest</code> class provides JUnit tests for the {@link askIDStore} class.
 *
 * <p>
 * These tests validate the behavior of the {@link askIDStore} class when handling a valid item ID.
 * </p>
 *
 * <p>
 * The test simulates user interaction by creating an instance of the {@link askIDStore} class,
 * setting up the required parameters, and calling the {@link askIDStore#buttonEventHandler()} method.
 * It then asserts that the window is still visible after handling the event, which indicates the expected behavior.
 * </p>
 *
 * <p>
 * Note: This test assumes that the {@link askIDStore#buttonEventHandler()} method correctly handles a valid item ID.
 * </p>
 *
 * @author Tommy Brisset
 * @version 1.0
 */
public class askIDStoreTest {

    /**
     * Default constructor.
     */
    public askIDStoreTest() {
    }

    /**
     * Tests the behavior of the {@link askIDStore} class when handling a valid item ID.
     * The test simulates user interaction and asserts that the window is still visible after handling the event.
     */
    @Test
    public void testValidID() {
        askIDStore askIDStore = new askIDStore(30, 1);
        askIDStore.buttonEventHandler();

        assertTrue(askIDStore.isVisible());
    }
}

