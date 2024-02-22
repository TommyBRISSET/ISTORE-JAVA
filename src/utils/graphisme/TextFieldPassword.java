package utils.graphisme;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * An extension of JPasswordField with a custom graphical style.
 * This class provides a text field for entering passwords with a rounded background and border.
 * It includes methods for customizing the appearance and retrieving border-related properties.
 *
 * @author Tommy Brisset
 * @version 1.0
 */
public class TextFieldPassword extends JPasswordField {

    /**
     * The serial version UID.
     */
    private Shape shape;

    /**
     * The border color.
     */
    private Color borderColor = UIUtils.COLOR_OUTLINE;

    /**
     * Default constructor for TextFieldPassword.
     * Initializes the graphical style and properties of the password field.
     */
    public TextFieldPassword() {
        setOpaque(false);
        setBackground(UIUtils.COLOR_BACKGROUND);
        setForeground(Color.white);
        setCaretColor(Color.white);
        setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        setMargin(new Insets(5, 10, 2, 5));
        setHorizontalAlignment(SwingConstants.LEFT);
        setFont(UIUtils.FONT_GENERAL_UI);
    }

    /**
     * Draws the main component with a rounded background.
     *
     * @param g The Graphics object used for drawing.
     */
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = UIUtils.get2dGraphics(g);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UIUtils.ROUNDNESS, UIUtils.ROUNDNESS);
        super.paintComponent(g2);
    }

    /**
     * Draws the border of the component with a rounded outline.
     *
     * @param g The Graphics object used for drawing.
     */
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = UIUtils.get2dGraphics(g);
        g2.setColor(borderColor);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UIUtils.ROUNDNESS, UIUtils.ROUNDNESS);
    }

    /**
     * Checks if the specified coordinates are contained within the rounded shape of the password field.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @return true if the coordinates are contained, otherwise false.
     */
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, UIUtils.ROUNDNESS, UIUtils.ROUNDNESS);
        }
        return shape.contains(x, y);
    }

    /**
     * Sets the border color of the password field.
     *
     * @param color The border color to set.
     */
    public void setBorderColor(Color color) {
        borderColor = color;
        repaint();
    }

    /**
     * Gets the current border color of the password field.
     *
     * @return The current border color.
     */
    public Color getBorderColor() {
        return borderColor;
    }

}
