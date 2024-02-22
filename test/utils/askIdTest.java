package utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The <code>AskIdTest</code> test class tests the <code>isValidNumber</code> method of the <code>askId</code> class.
 *
 * @see utils.askId
 * @author Tommy Brisset
 * @version 1.0
 */
public class askIdTest {

    /**
     * Default constructor.
     */
    public askIdTest() {
    }

    /**
     * Tests the <code>isValidNumber</code> method with a string representing a valid number.
     */
    @Test
    public void testIsValidNumber1() {
        boolean result = isNumberValid.isAValidNumber("123");

        assertTrue(result, "Should return true for a valid number input");
    }

    /**
     * Tests the <code>isValidNumber</code> method with a character string that does not represent a valid number.
     */
    @Test
    public void testIsValidNumber2() {
        boolean result = isNumberValid.isAValidNumber("abc");

        assertFalse(result, "Should return false for an invalid input");
    }

    /**
     * Tests the <code>isValidNumber</code> method with a null string.
     */
    @Test
    public void testIsValidNumber3() {
        boolean result = isNumberValid.isAValidNumber(null);

        assertFalse(result, "Should return false for a null input");
    }

    /**
     * Tests the <code>isValidNumber</code> method with an empty string.
     */
    @Test
    public void testIsValidNumber4() {
        boolean result = isNumberValid.isAValidNumber(" ");

        assertFalse(result, "Should return false for an empty input");
    }
}
