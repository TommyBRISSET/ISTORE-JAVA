package Toaster;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import javax.swing.*;

/**
 * JUnit test class for the {@link Toaster} class.
 * It tests the functionality of the Toaster by triggering different types of toast messages.
 *
 * @author Tommy Brisset
 * @version 1.0
 */
public class ToasterTest {

    /**
     * Default constructor.
     */
    public ToasterTest() {
    }

    /**
     * Tests the Toaster class by creating a test panel and triggering
     * different types of toast messages (success, error, info, warn).
     */
    @Test
    public void testToaster() {
        JPanel testPanel = new JPanel();
        Toaster toaster = new Toaster(testPanel);

        assertDoesNotThrow(() -> toaster.success("Success message"));

        assertDoesNotThrow(() -> toaster.error("Error message"));

        assertDoesNotThrow(() -> toaster.info("Info message"));

        assertDoesNotThrow(() -> toaster.warn("Warning message"));
    }
}
