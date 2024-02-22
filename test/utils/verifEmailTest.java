package utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Test class for the {@link verifEmail} class.
 *
 * @author Tommy Brisset
 * @version 1.0
 */
public class verifEmailTest {

    /**
     * Default constructor.
     */
    public verifEmailTest() {
    }

    /**
     * Tests the {@link verifEmail#isValidEmail(String)} method with valid e-mail addresses.
     */
    @Test
    public void testValidEmail() {
        assertTrue(verifEmail.isValidEmail("test@example.com"));
        assertTrue(verifEmail.isValidEmail("john.doe123@company.co"));
        assertTrue(verifEmail.isValidEmail("user+name@domain.net"));
    }

    /**
     * Tests the {@link verifEmail#isValidEmail(String)} method with invalid e-mail addresses.
     */
    @Test
    public void testInvalidEmail() {
        assertFalse(verifEmail.isValidEmail("invalid-email"));
        assertFalse(verifEmail.isValidEmail("user@domain"));
        assertFalse(verifEmail.isValidEmail("user@.com"));
        assertFalse(verifEmail.isValidEmail("user@domain."));
    }

    /**
     * Tests the {@link verifEmail#isValidEmail(String)} method with an empty e-mail address.
     */
    @Test
    public void testEmptyEmail() {
        assertFalse(verifEmail.isValidEmail(""));
    }
}
