package utils.graphisme;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The TextFieldTitleTest class contains unit tests for the TextFieldTitle class.
 * It ensures that the custom text field behaves as expected in different scenarios.
 *
 * @author Tommy Brisset
 * @version 1.0
 */
public class TextFieldTitleTest {

    /**
     * Default constructor.
     */
    public TextFieldTitleTest() {
    }

    /**
     * Tests the default constructor of TextFieldTitle.
     * It checks if the created instance has the expected initial properties.
     */
    @Test
    public void testDefaultConstructor() {
        TextFieldTitle textField = new TextFieldTitle();

        assertFalse(textField.isOpaque());

        assertEquals(UIUtils.COLOR_BACKGROUND, textField.getBackground());

        assertEquals(Color.white, textField.getForeground());

        assertEquals(Color.white, textField.getCaretColor());

        assertEquals(Cursor.TEXT_CURSOR, textField.getCursor().getType());

        assertEquals(new Insets(2, 10, 2, 2), textField.getMargin());

        assertEquals(SwingConstants.LEFT, textField.getHorizontalAlignment());

        assertEquals(UIUtils.FONT_GENERAL_UI, textField.getFont());
    }

    /**
     * Tests the contains method of TextFieldTitle.
     * It checks if the overridden method correctly determines whether a point is within the shape.
     */
    @Test
    public void testContains() {
        TextFieldTitle textField = new TextFieldTitle();

        textField.setSize(100, 50);

        assertTrue(textField.contains(10, 10));
        assertFalse(textField.contains(110, 10));
        assertFalse(textField.contains(10, 60));

        textField.setSize(200, 100);
        assertTrue(textField.contains(50, 50));
        assertFalse(textField.contains(250, 50));
        assertFalse(textField.contains(50, 150));
    }

    /**
     * Tests the setBorderColor and getBorderColor methods of TextFieldTitle.
     * It checks if the border color can be set and retrieved correctly.
     */
    @Test
    public void testSetGetBorderColor() {
        TextFieldTitle textField = new TextFieldTitle();

        textField.setBorderColor(Color.red);

        assertEquals(Color.red, textField.getBorderColor());
    }

}

