package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The <code>EmailValidator</code> class provides a method to validate an email address
 * using a regular expression.
 *
 * @author Tommy Brisset
 * @version 1.0
 */
public class verifEmail {

    /**
     * Default constructor.
     */
    public verifEmail() {
    }

    /**
     * Checks if a given email address is valid using a regular expression.
     *
     * @param email The email address to validate.
     * @return <code>true</code> if the email is valid, <code>false</code> otherwise.
     */
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pattern = Pattern.compile(emailRegex);

        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
}
