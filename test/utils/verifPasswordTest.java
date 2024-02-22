package utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Test class for the {@link verifPassword} class.
 *
 * <p>
 * This class tests the {@link verifPassword#isValidPassword(String)} method with different scenarios.
 * </p>
 *
 * @author Tommy Brisset
 * @version 1.0
 */
public class verifPasswordTest {

    /**
     * Default constructor.
     */
    public verifPasswordTest() {
    }

    /**
     * Test the {@link verifPassword#isValidPassword(String)} method with different scenarios.
     */
    @Test
    public void testIsValidPassword() {
        String validPassword = "SecureP@ss";
        assertTrue(verifPassword.isValidPassword(validPassword));

        String shortPassword = "Abcd123";
        assertFalse(verifPassword.isValidPassword(shortPassword));

        String noUpperCasePassword = "abcd123!";
        assertFalse(verifPassword.isValidPassword(noUpperCasePassword));

        String noSpecialCharPassword = "Abcd1234";
        assertFalse(verifPassword.isValidPassword(noSpecialCharPassword));
    }
}
