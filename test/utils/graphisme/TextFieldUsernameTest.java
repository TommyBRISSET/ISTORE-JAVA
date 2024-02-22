package utils.graphisme;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.*;

/**
 * Unit tests for the TextFieldUsername class.
 *
 * @author Tommy Brisset
 * @version 1.0
 */
class TextFieldUsernameTest {

    /**
     * Tests the getBorderColor method of TextFieldUsername.
     */
    @Test
    void testGetBorderColor() {
        TextFieldUsername textField = new TextFieldUsername();

        assertEquals(UIUtils.COLOR_INTERACTIVE, textField.getBorderColor());

        Color newColor = Color.BLUE;
        textField.setBorderColor(newColor);

        assertEquals(newColor, textField.getBorderColor());
    }
}
