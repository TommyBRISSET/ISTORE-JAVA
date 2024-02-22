package utils.graphisme;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.swing.*;
import java.awt.*;

/**
 * Tests for the {@link TextFieldPassword} class.
 *
 * @author Tommy Brisset
 * @version 1.0
 */
public class TextFieldPasswordTest {

    /**
     * Default constructor.
     */
    public TextFieldPasswordTest() {
    }

    /**
     * Tests creating a TextFieldPassword instance with default settings.
     */
    @Test
    public void TextFieldPassword() {
        TextFieldPassword textField = new TextFieldPassword();

        assertEquals(UIUtils.COLOR_BACKGROUND, textField.getBackground());

        assertEquals(Color.white, textField.getForeground());

        assertEquals(Color.white, textField.getCaretColor());

        assertEquals(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR), textField.getCursor());

        assertEquals(new Insets(5, 10, 2, 5), textField.getMargin());

        assertEquals(SwingConstants.LEFT, textField.getHorizontalAlignment());

        assertEquals(UIUtils.FONT_GENERAL_UI, textField.getFont());
    }

    /**
     * Teste la méthode getBorderColor de TextFieldPassword.
     */
    @Test
    public void testGetBorderColor() {
        TextFieldPassword textField = new TextFieldPassword();
        Color newColor = Color.RED;
        textField.setBorderColor(newColor);

        assertEquals(newColor, textField.getBorderColor());
    }
}
