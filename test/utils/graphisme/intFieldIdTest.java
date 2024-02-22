package utils.graphisme;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.*;
import java.awt.*;



/**
 * Tests for the {@link intFieldId} class.
 *
 * @see intFieldId
 * @see UIUtils
 * @author Tommy Brisset
 * @version 1.0
 */
class intFieldIdTest {

    /**
     * Test the constructor of the {@link intFieldId} class.
     * Verifies that the text field is correctly initialized with default values.
     */
    @Test
    void IntFieldIdConstructor() {
        intFieldId intField = new intFieldId();

        assertNotNull(intField);
        assertEquals(UIUtils.COLOR_BACKGROUND, intField.getBackground());
        assertEquals(Color.white, intField.getForeground());
        assertEquals(Color.white, intField.getCaretColor());
        assertEquals(Cursor.TEXT_CURSOR, intField.getCursor().getType());
        assertEquals(new Insets(2, 10, 2, 2), intField.getMargin());
        assertEquals(SwingConstants.LEFT, intField.getHorizontalAlignment());
        assertEquals(UIUtils.FONT_GENERAL_UI, intField.getFont());
    }

    /**
     * Test the {@link intFieldId#contains(int, int)} method.
     * Verifies that the specified point is contained within the shape of the component.
     */
    @Test
    void IntFieldIdContains() {
        intFieldId intField = new intFieldId();
        intField.setSize(100, 30);

        assertTrue(intField.contains(10, 10));
        assertFalse(intField.contains(200, 200));
    }

    /**
     * Test the {@link intFieldId#setBorderColor(Color)} and {@link intFieldId#getBorderColor()} methods.
     * Verifies that the border color can be set and retrieved correctly.
     */
    @Test
    void IntFieldIdBorderColor() {
        intFieldId intField = new intFieldId();
        Color newBorderColor = Color.RED;

        intField.setBorderColor(newBorderColor);

        assertEquals(newBorderColor, intField.getBorderColor());
    }
}


