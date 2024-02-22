package utils.graphisme;

import javax.swing.*;
import javax.swing.JButton;
import java.awt.*;
import java.util.HashMap;

/**
 * Utility class for common UI-related constants and methods.
 *
 * @author Tommy Brisset
 * @version 1.0
 */
public class UIUtils {

    /**
     * Default constructor.
     */
    public UIUtils() {
    }
    /**
     * The general UI font.
     */
    public static final Font FONT_GENERAL_UI = new Font("Segoe UI", Font.PLAIN, 20);

    /**
     * The general UI font for sub menu.
     */
    public static final Font FONT_GENERAL_SUB_UI = new Font("Segoe UI", Font.BOLD, 13);

    /**
     * The font for forgot password components.
     */
    public static final Font FONT_FORGOT_PASSWORD = new Font("Segoe UI", Font.PLAIN, 12);

    /**
     * Color for outline elements (dark gray).
     */
    public static final Color COLOR_OUTLINE = new Color(255, 255, 255);

    /**
     * Background color for UI components (dark blue).
     */
    public static final Color COLOR_BACKGROUND = new Color(51, 78, 95);

    /**
     * Interactive color (red).
     */
    public static final Color COLOR_INTERACTIVE = new Color(220, 53, 69);

    /**
     * Darker version of interactive color (darker red).
     */
    public static final Color COLOR_INTERACTIVE_DARKER = new Color(176, 43, 56);

    /**
     * Off-white color.
     */
    public static final Color OFFWHITE = new Color(255, 255, 255);

    /**
     * Default button text for login.
     */
    public static final String BUTTON_TEXT_LOGIN = "LOGIN";

    /**
     * Default button text for search id.
     */
    public static final String BUTTON_INT_ID = "SEARCH ID";

    /**
     * Default button text for whitelist user.
     */
    public static final String BUTTON_WHITELIST = "WHITELIST USER";

    /**
     * Default button text for whitelist user.
     */
    public static final String BUTTON_CREA_STORE = "CREATE STORE";

    /**
     * Default button text for delete store.
     */
    public static final String BUTTON_DELE_STORE = "DELETE STORE";

    /**
     * Default button text for send message.
     */
    public static final String BUTTON_SEND_MESS = "SEND MESSAGE";

    /**
     * Default button text for forgot password.
     */
    public static final String BUTTON_TEXT_CANCEL = "CANCEL APPLICATION";

    /**
     * Default button text for forgot password.
     */
    public static final String BUTTON_TEXT_BACK = "BACK";

    /**
     * Default button text for forgot password.
     */
    public static final String BUTTON_TEXT_ADMIN = "PROMOTE TO ADMIN";

    /**
     * Default button text for registration.
     */
    public static final String BUTTON_TEXT_REGISTER = "REGISTER";

    /**
     * Default placeholder text for the username input field.
     */
    public static final String PLACEHOLDER_TEXT_USERNAME = "LOGIN/EMAIL";

    /**
     * Default placeholder text for the title input field.
     */
    public static final String PLACEHOLDER_TEXT_TITLE = "TITLE";

    /**
     * Default placeholder text for the message  input field.
     */
    public static final String PLACEHOLDER_TEXT_MESS = "MESSAGE TEXT";

    /**
     * Default placeholder text for the pseudo input field.
     */
    public static final String PLACEHOLDER_TEXT_PSEUDO = "PSEUDO";

    /**
     * The roundness value for rounded components.
     */
    public static final int ROUNDNESS = 50;

    /**
     * Gets a Graphics2D object with anti-aliasing enabled.
     *
     * @param g The original Graphics object.
     * @return The Graphics2D object with anti-aliasing.
     */
    public static Graphics2D get2dGraphics(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.addRenderingHints(new HashMap<RenderingHints.Key, Object>() {{
            put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        }});
        return g2;
    }

    /**
     * Styles a JMenuItem with the general UI font and colors.
     *
     * @param item The JMenuItem to style.
     */
    public static void styleJMenuItem(JMenuItem item) {
        item.setForeground(Color.BLACK);
        item.setFont(FONT_GENERAL_SUB_UI);
        item.setBorderPainted(false);
        item.setBorder(null);
        item.setOpaque(true);
        item.setText(item.getText() + "   ");
    }

    /**
     * Styles a JMenuBar with the general UI font and colors.
     *
     * @param menuBar The JMenuBar to style.
     */
    public static void styleJMenuBar(JMenuBar menuBar) {
        menuBar.setBackground(Color.WHITE);
        menuBar.setOpaque(true);
        menuBar.setFont(FONT_GENERAL_UI);
        menuBar.setBorderPainted(false);
        menuBar.setBorder(null);
        menuBar.setPreferredSize(new Dimension(menuBar.getWidth(), 35));
        Insets margin = new Insets(25, 50, 25, 50);
        menuBar.setMargin(margin);
    }

    /**
     * Styles a JMenu with the general UI font and colors.
     *
     * @param menu The JMenu to style.
     */
    public static void styleJMenu(JMenu menu) {
        menu.setFont(FONT_GENERAL_UI);
        menu.setForeground(Color.BLACK);
        menu.setBorderPainted(false);
        menu.setBorder(null);
        menu.setOpaque(true);
        menu.setBackground(Color.WHITE);
        // add space between menu items
        menu.setText(menu.getText() + "   ");
    }

    /**
     * Styles a JButton with the general UI font and colors.
     *
     * @param button The JButton to style.
     */
    public static void styleJButton(JButton button) {
        button.setFont(FONT_GENERAL_UI);
        button.setBackground(COLOR_INTERACTIVE);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setBorder(null);
        button.setPreferredSize(new Dimension(200, 50));
        button.setMargin(new Insets(10, 10, 10, 10));
    }

    /**
     * Styles a JList with the general UI font and colors.
     *
     * @param list The JList to style.
     */
    public static void styleJList(JList<String> list) {
        list.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        list.setBackground(COLOR_BACKGROUND);
        list.setForeground(Color.WHITE);
        list.setSelectionBackground(COLOR_INTERACTIVE);
        list.setSelectionForeground(Color.WHITE);
        list.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    /**
     * Styles a JTextArea with the general UI font and colors.
     *
     * @param textArea The JTextArea to style.
     */
    public static void styleJTextArea(JTextArea textArea) {
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textArea.setBackground(COLOR_BACKGROUND);
        textArea.setForeground(Color.WHITE);
        textArea.setCaretColor(Color.WHITE);
        textArea.setMargin(new Insets(10, 10, 10, 10));
    }
}
