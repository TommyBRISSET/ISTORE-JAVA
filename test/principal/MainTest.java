package principal;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The {@code MainTest} class contains JUnit tests for the {@code Main} class.
 * It ensures the correct behavior of the main method and constructor in the context
 * of creating and initializing a graphical user interface using Swing.
 *
 * <p>
 * Example usage:
 * </p>
 *
 *
 * @see Main
 * @see javax.swing.SwingUtilities
 * @see registerFrame
 * @author Tommy Brisset
 * @version 1.0
 */
public class MainTest {

    /**
     * Default constructor.
     */
    public MainTest() {
    }

    /**
     * Test the main method of the {@code Main} class.
     * It ensures that the main method runs without throwing any exceptions.
     */
    @Test
    void MainMethod() {
        assertDoesNotThrow(() -> Main.main(new String[]{}));
    }

    /**
     * Test the default constructor of the {@code Main} class.
     * It ensures that the default constructor does not throw any exceptions.
     */
    @Test
    void MainConstructor() {
        assertDoesNotThrow(Main::new);
    }

    /**
     * Test the use of {@code SwingUtilities.invokeLater()} for initializing the graphical interface.
     * It ensures that creating an instance of {@code registerFrame} in the EDT does not throw any exceptions.
     */
    @Test
    void SwingUtilitiesInvokeLater() {
        assertDoesNotThrow(() -> SwingUtilities.invokeLater(() -> new registerFrame(false)));
    }
}
