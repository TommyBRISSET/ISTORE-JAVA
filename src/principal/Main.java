package principal;

import javax.swing.*;

/**
 * The <code>Main</code> class contains the main method of the program.
 * It initializes the graphical user interface using the Swing library.
 *
 * <p>
 * The program creates an instance of the <code>registerFrame</code> class and executes it in the Event Dispatch Thread (EDT)
 * using the <code>invokeLater</code> method of the <code>SwingUtilities</code> class.
 * </p>
 *
 * <p>
 * The use of EDT is recommended when creating Swing graphical interfaces to ensure thread safety.
 * </p>
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * public class Main {
 *     public static void main(String[] args) {
 *         SwingUtilities.invokeLater(registerFrame::new);
 *     }
 * }
 * </pre>
 *
 * @see javax.swing.SwingUtilities
 * @see registerFrame
 * @author Tommy Brisset
 * @version 1.0
 */
public class Main {

    /**
     * Default constructor.
     */
    public Main() {
    }
    /**
     * The main method of the program.
     * It creates an instance of the <code>registerFrame</code> class and executes it in the EDT.
     *
     * @param args The command-line arguments (not used in this example).
     */
    public static void main(String[] args) {
        // Execute the creation of the graphical interface in the EDT
        SwingUtilities.invokeLater(() -> new registerFrame(false));
    }
}
