package utils;

/**
 * Utility class to check if a string is a valid number.
 *
 * @author Tommy Brisset
 * @version 1.0
 */
public class isNumberValid {

    /**
     * Default constructor.
     */
    public isNumberValid() {
    }

    /**
     * Checks if a string is a valid number.
     *
     * @param input The string to check.
     * @return true if the string is a valid number, false otherwise.
     */
    public static boolean isAValidNumber(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
