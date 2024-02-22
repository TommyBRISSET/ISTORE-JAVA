package principal;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.*;

/**
 * Unit tests for the LoginFrame class.
 *
 * @author Tommy Brisset
 * @version 1.0
 */
public class loginFrameTest {

    /**
     * Default constructor.
     */
    public loginFrameTest() {
    }

    /**
     * Tests the initialization of the LoginFrame GUI.
     */
    @Test
    public void LoginFrameInit() {
        assertDoesNotThrow(() -> {
            SwingUtilities.invokeLater(LoginFrame::new);
        });
    }

    /**
     * Tests the login event handler with valid credentials.
     */
    @Test
    public void LoginEventHandler1() {
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.getUsernameField().setText("validemail@example.com");
        loginFrame.getPasswordField().setText("validPassword");
        assertDoesNotThrow(loginFrame::loginEventHandler);
    }

    /**
     * Tests the login event handler with an invalid email.
     */
    @Test
    public void LoginEventHandler2() {
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.getUsernameField().setText("invalidemail");
        loginFrame.getPasswordField().setText("validPassword");
        assertDoesNotThrow(loginFrame::loginEventHandler);
    }

    /**
     * Tests the login event handler with an invalid password.
     */
    @Test
    public void LoginEventHandler3() {
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.getUsernameField().setText("validemail@example.com");
        loginFrame.getPasswordField().setText("invalid");
        assertDoesNotThrow(loginFrame::loginEventHandler);
    }
}
