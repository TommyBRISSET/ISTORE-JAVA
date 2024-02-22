package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The <code>PasswordValidator</code> class provides a method to validate the strength of a password
 * using a regular expression.
 *
 * @author Tommy Brisset
 * @version 1.0
 */
public class verifPassword {

    /**
     * Default constructor.
     */
    public verifPassword() {
    }

    /**
     * Checks if a given password is valid using a regular expression.
     *
     * @param password The password to validate.
     * @return <code>true</code> if the password is valid, <code>false</code> otherwise.
     */
    public static boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[A-Z])(?=.*[!@#$%^&*()-+=])[A-Za-z\\d!@#$%^&*()-+=]{8,}$";

        Pattern pattern = Pattern.compile(passwordRegex);

        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }
}
