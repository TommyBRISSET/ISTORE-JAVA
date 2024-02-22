package utils.graphisme;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;

/**
 * Test class for the HyperlinkText class.
 *
 * @author Tommy Brisset
 * @version 1.0
 */
public class HyperlinkTextTest {

    /**
     * Default constructor.
     */
    public HyperlinkTextTest() {
    }

    /**
     * Tests creating an instance of HyperlinkText with specified parameters.
     */
    @Test
    public void testHyperlinkText() {
        Runnable action = () -> System.out.println("Action performed");

        HyperlinkText hyperlink = new HyperlinkText("Test", 10, 10, action);

        assertEquals("Test", hyperlink.getText());

        assertEquals(UIUtils.COLOR_OUTLINE, hyperlink.getForeground());

        assertEquals(UIUtils.FONT_FORGOT_PASSWORD, hyperlink.getFont());

        assertEquals(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR), hyperlink.getCursor());
    }
}
