package principal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.*;


/**
 * JUnit tests for the RegisterFrame class.
 *
 * @author Tommy Brisset
 * @version 1.0
 */
class RegisterFrameTest {

    /**
     * The RegisterFrame instance to be tested.
     */
    private registerFrame registerFrame;

    /**
     * Default constructor.
     */
    public RegisterFrameTest() {
    }

    /**
     * Sets up the test environment by initializing the RegisterFrame object.
     * It ensures that Swing components are initialized on the Event Dispatch Thread.
     */
    @BeforeEach
    void setUp() {
        SwingUtilities.invokeLater(() -> {
            registerFrame = new registerFrame(false);
        });
        try {
            SwingUtilities.invokeAndWait(() -> {});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Tests the register event handler with all fields empty.
     */
    @Test
    void testRegisterEventHandlerWithAllFieldsEmpty() {
        assertNotNull(registerFrame, "Failed to initialize registerFrame");
    }

    /**
     * Tests the register event handler with valid credentials.
     * It sets the username, password, and pseudo fields with valid values.
     * Expects the event handler not to throw an exception.
     */
    @Test
    void testRegisterEventHandlerWithValidCredentials() {
        registerFrame.getUsernameField().setText("validemail@example.com");
        registerFrame.getPasswordField().setText("validPassword");
        registerFrame.getPseudoField().setText("validPseudo");
        assertDoesNotThrow(registerFrame::loginEventHandler);
    }

    /**
     * Tests the register event handler with an invalid email.
     * It sets an invalid email address in the username field.
     * Expects the event handler not to throw an exception.
     */
    @Test
    void testRegisterEventHandlerWithInvalidEmail() {
        registerFrame.getUsernameField().setText("invalidemail");
        registerFrame.getPasswordField().setText("validPassword");
        registerFrame.getPseudoField().setText("validPseudo");
        assertDoesNotThrow(registerFrame::loginEventHandler);
    }

    /**
     * Tests the register event handler with an invalid password.
     * It sets an invalid password in the password field.
     * Expects the event handler not to throw an exception.
     */
    @Test
    void testRegisterEventHandlerWithInvalidPassword() {
        registerFrame.getUsernameField().setText("validemail@example.com");
        registerFrame.getPasswordField().setText("invalid");
        registerFrame.getPseudoField().setText("validPseudo");
        assertDoesNotThrow(registerFrame::loginEventHandler);
    }

    /**
     * Tests the register event handler with an invalid pseudo.
     * It sets an empty pseudo field.
     * Expects the event handler not to throw an exception.
     */
    @Test
    void testRegisterEventHandlerWithInvalidPseudo() {
        registerFrame.getUsernameField().setText("validemail@example.com");
        registerFrame.getPasswordField().setText("validPassword");
        registerFrame.getPseudoField().setText("");
        assertDoesNotThrow(registerFrame::loginEventHandler);
    }

    /**
     * Tests the register event handler when the admin exists.
     * It sets the adminExists property to true and provides valid credentials.
     * Expects the event handler not to throw an exception.
     */
    @Test
    void testRegisterEventHandlerWithAdminExists() {
        registerFrame.setAdminExists(true);
        registerFrame.getUsernameField().setText("validemail@example.com");
        registerFrame.getPasswordField().setText("validPassword");
        registerFrame.getPseudoField().setText("validPseudo");
        assertDoesNotThrow(registerFrame::loginEventHandler);
    }

    /**
     * Tests the register event handler when the admin does not exist.
     * It sets the adminExists property to false and provides valid credentials.
     * Expects the event handler not to throw an exception.
     */
    @Test
    void testRegisterEventHandlerWithAdminNotExists() {
        registerFrame.setAdminExists(false);
        registerFrame.getUsernameField().setText("validemail@example.com");
        registerFrame.getPasswordField().setText("validPassword");
        registerFrame.getPseudoField().setText("validPseudo");
        assertDoesNotThrow(registerFrame::loginEventHandler);
    }
}
