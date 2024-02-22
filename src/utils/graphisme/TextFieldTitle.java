package utils.graphisme;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Custom JTextField for title input with rounded borders and styling.
 *
 * @author Tommy Brisset
 * @version 1.0
 */
public class TextFieldTitle extends JTextField {

    /** The serial version UID. */
    private Shape shape;

    /** The color of the text field's border. */
    private Color borderColor = UIUtils.COLOR_INTERACTIVE;

    /**
     * Constructs a new textFieldTitle.
     */
    public TextFieldTitle() {
        setOpaque(false);
        setBackground(UIUtils.COLOR_BACKGROUND);
        setForeground(Color.white);
        setCaretColor(Color.white);
        setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        setMargin(new Insets(2, 10, 2, 2));
        setHorizontalAlignment(SwingConstants.LEFT);
        setFont(UIUtils.FONT_GENERAL_UI);
    }

    /**
     * Overrides the paintComponent method to customize the appearance of the text field.
     *
     * @param g The graphics context.
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = UIUtils.get2dGraphics(g);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UIUtils.ROUNDNESS, UIUtils.ROUNDNESS);
        super.paintComponent(g2);
    }

    /**
     * Overrides the paintBorder method to customize the appearance of the text field's border.
     *
     * @param g The graphics context.
     */
    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = UIUtils.get2dGraphics(g);
        g2.setColor(borderColor);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UIUtils.ROUNDNESS, UIUtils.ROUNDNESS);
    }

    /**
     * Checks if the given coordinates are contained within the shape of the text field.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @return True if the coordinates are within the shape, false otherwise.
     */
    @Override
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, UIUtils.ROUNDNESS, UIUtils.ROUNDNESS);
        }
        return shape.contains(x, y);
    }

    /**
     * Sets the border color of the text field.
     *
     * @param color The new border color.
     */
    public void setBorderColor(Color color) {
        borderColor = color;
        repaint();
    }

    /**
     * Gets the current border color of the component.
     *
     * @return The current border color.
     */
    public Color getBorderColor() {
        return borderColor;
    }
}

