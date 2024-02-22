package utils.graphisme;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.*;

/**
 * Unit tests for the {@link TextFieldPseudo} class.
 *
 * @author Tommy Brisset
 * @version 1.0
 */
class TextFieldPseudoTest {

    /**
     * Tests the {@link TextFieldPseudo#getBorderColor()} and {@link TextFieldPseudo#setBorderColor(Color)} methods.
     */
    @Test
    void testBorderColor() {
        TextFieldPseudo textField = new TextFieldPseudo();

        assertEquals(UIUtils.COLOR_OUTLINE, textField.getBorderColor());

        Color newColor = Color.RED;
        textField.setBorderColor(newColor);

        assertEquals(newColor, textField.getBorderColor());
    }
}
