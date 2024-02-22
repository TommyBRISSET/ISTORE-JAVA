package Toaster;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The Toaster class provides a simple way to display toast messages on a JPanel.
 * Toast messages can be of different types (error, success, info, warn) and
 * are displayed with a specific background color.
 *
 * <p>
 * Usage example:
 * <pre>
 * JPanel myPanel = new JPanel();
 * Toaster toaster = new Toaster(myPanel);
 * toaster.error("This is an error message.");
 * </pre>

 *
 * <p>
 * The Toaster class uses ToasterBody instances to display individual toast messages.
 * Each toast message is displayed in a separate ToasterBody component.
 * </p>
 *
 * @author Tommy Brisset
 * @version 1.0
 */
public class Toaster {

    private static final int STARTING_Y_POS = 15;
    private static final int SPACER_DISTANCE = 15;
    private static final ArrayList<ToasterBody> toasterBodies = new ArrayList<>();
    private final static AtomicInteger CURRENT_Y_OFFSET = new AtomicInteger();
    private final JPanel panelToToastOn;

    /**
     * Constructs a Toaster instance associated with a specific JPanel.
     *
     * @param panelToToastOn The JPanel on which toast messages will be displayed.
     */
    public Toaster(JPanel panelToToastOn) {
        this.panelToToastOn = panelToToastOn;
    }

    /**
     * Displays an error toast message.
     *
     * @param messages The error messages to display.
     */
    public void error(String... messages) {
        for (String s : messages) {
            toast(s, new Color(181, 59, 86));
        }
    }

    /**
     * Displays a success toast message.
     *
     * @param messages The success messages to display.
     */
    public void success(String... messages) {
        for (String s : messages) {
            toast(s, new Color(33, 181, 83));
        }
    }

    /**
     * Displays an info toast message.
     *
     * @param messages The info messages to display.
     */
    public void info(String... messages) {
        for (String s : messages) {
            toast(s, new Color(13, 116, 181));
        }
    }

    /**
     * Displays a warning toast message.
     *
     * @param messages The warning messages to display.
     */
    public void warn(String... messages) {
        for (String s : messages) {
            toast(s, new Color(181, 147, 10));
        }
    }

    /**
     * Displays a toast message with the specified content and background color.
     * Creates a new ToasterBody and adds it to the display. Each toast message
     * is associated with a new thread, allowing it to automatically disappear
     * after a specified duration.
     *
     * @param message The content of the toast message.
     * @param bgColor The background color of the toast message.
     */
    private void toast(String message, Color bgColor) {
        ToasterBody toasterBody;

        if (toasterBodies.isEmpty()) {
            toasterBody = new ToasterBody(panelToToastOn, message, bgColor, STARTING_Y_POS);
            CURRENT_Y_OFFSET.set(STARTING_Y_POS + toasterBody.getHeightOfToast());
        } else {
            toasterBody = new ToasterBody(panelToToastOn, message, bgColor, CURRENT_Y_OFFSET.get() + SPACER_DISTANCE);
            CURRENT_Y_OFFSET.addAndGet(SPACER_DISTANCE + toasterBody.getHeightOfToast());
        }

        toasterBodies.add(toasterBody);

        new Thread(() -> {
            toasterBody.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    removeToast(toasterBody);
                }
            });

            panelToToastOn.add(toasterBody, 0);
            panelToToastOn.repaint();

            try {
                Thread.sleep(6000);
                removeToast(toasterBody);
            } catch (InterruptedException e) {
                e.printStackTrace(System.out);
            }
        }).start();
    }

    /**
     * Removes a toast message from the display.
     * If the specified ToasterBody is not set to stop displaying,
     * it marks it for removal, adjusts the positions of remaining toast messages,
     * and updates the display accordingly.
     *
     * @param toasterBody The ToasterBody to be removed.
     */
    private synchronized void removeToast(ToasterBody toasterBody) {
        if (!toasterBody.getStopDisplaying()) {
            toasterBody.setStopDisplaying(true);

            toasterBodies.forEach(toasterBody1 -> {
                if (toasterBodies.indexOf(toasterBody1) >= toasterBodies.indexOf(toasterBody)) {
                    toasterBody1.setyPos(toasterBody1.getyPos() - toasterBody.getHeightOfToast() - SPACER_DISTANCE);
                }
            });

            toasterBodies.remove(toasterBody);

            CURRENT_Y_OFFSET.set(CURRENT_Y_OFFSET.get() - SPACER_DISTANCE - toasterBody.getHeightOfToast());

            panelToToastOn.remove(toasterBody);
            panelToToastOn.repaint();
        }
    }
}
