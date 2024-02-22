package utils.graphisme;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * Unit tests for the UIUtils class.
 *
 * @author Tommy Brisset
 * @version 1.0
 */
class UIUtilsTest {

    /**
     * Test the get2dGraphics method to ensure it returns a Graphics2D object with anti-aliasing enabled.
     */
    @Test
    void testGet2dGraphics() {
        Graphics graphics = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).getGraphics();

        Graphics2D g2d = UIUtils.get2dGraphics(graphics);

        Map<?, ?> renderingHints = g2d.getRenderingHints();
        assertEquals(RenderingHints.VALUE_ANTIALIAS_ON, renderingHints.get(RenderingHints.KEY_ANTIALIASING));
        assertEquals(RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB, renderingHints.get(RenderingHints.KEY_TEXT_ANTIALIASING));
    }
}
