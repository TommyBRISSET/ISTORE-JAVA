package Toaster;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.*;
import java.awt.*;

/**
 * JUnit test class for the ToasterBody class.
 *
 * @author Tommy Brisset
 * @version 1.0
 */
class ToasterBodyTest {

    /**
     * Test the getHeightOfToast method.
     */
    @Test
    void getHeightOfToast() {
        JPanel testPanel = new JPanel();
        ToasterBody toasterBody = new ToasterBody(testPanel, "Test Message", Color.RED, 50);

        assertEquals(toasterBody.getHeightOfToast(), toasterBody.getHeightOfToast());
    }

    /**
     * Test the getStopDisplaying method.
     */
    @Test
    void getStopDisplaying() {
        JPanel testPanel = new JPanel();
        ToasterBody toasterBody = new ToasterBody(testPanel, "Test Message", Color.RED, 50);

        assertFalse(toasterBody.getStopDisplaying());

        toasterBody.setStopDisplaying(true);

        assertTrue(toasterBody.getStopDisplaying());
    }

    /**
     * Test the setStopDisplaying method.
     */
    @Test
    void setStopDisplaying() {
        JPanel testPanel = new JPanel();
        ToasterBody toasterBody = new ToasterBody(testPanel, "Test Message", Color.RED, 50);

        assertFalse(toasterBody.getStopDisplaying());

        toasterBody.setStopDisplaying(true);

        assertTrue(toasterBody.getStopDisplaying());
    }

    /**
     * Test the setyPos method.
     */
    @Test
    void setyPos() {
        JPanel testPanel = new JPanel();
        ToasterBody toasterBody = new ToasterBody(testPanel, "Test Message", Color.RED, 50);

        int newYPos = 100;
        toasterBody.setyPos(newYPos);

        assertEquals(newYPos, toasterBody.getyPos());
    }

    /**
     * Test the getyPos method.
     */
    @Test
    void getyPos() {
        JPanel testPanel = new JPanel();
        ToasterBody toasterBody = new ToasterBody(testPanel, "Test Message", Color.RED, 50);

        assertEquals(50, toasterBody.getyPos());

        int newYPos = 100;
        toasterBody.setyPos(newYPos);

        assertEquals(newYPos, toasterBody.getyPos());
    }
}
