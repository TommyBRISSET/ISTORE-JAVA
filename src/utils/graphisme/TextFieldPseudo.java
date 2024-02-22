package utils.graphisme;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * A custom text field class for entering pseudonyms.
 * This class provides a text field for entering pseudonyms with a rounded background and border.
 * It includes methods for customizing the appearance and retrieving border-related properties.
 *
 * @author Tommy Brisset
 * @version 1.0
 */
public class TextFieldPseudo extends JTextField {

    /** The serial version UID. */
    private Shape shape;

    /** The border color. */
    private Color borderColor = UIUtils.COLOR_OUTLINE;

    /**
     * Default constructor for TextFieldPseudo.
     * Initializes the graphical style of the pseudo text field.
     */
    public TextFieldPseudo() {
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
     * Overrides the method to paint the component with a rounded border.
     *
     * @param g The Graphics object used for painting.
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = UIUtils.get2dGraphics(g);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UIUtils.ROUNDNESS, UIUtils.ROUNDNESS);
        super.paintComponent(g2);
    }

    /**
     * Overrides the method to paint the rounded border of the component.
     *
     * @param g The Graphics object used for painting the border.
     */
    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = UIUtils.get2dGraphics(g);
        g2.setColor(borderColor);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UIUtils.ROUNDNESS, UIUtils.ROUNDNESS);
    }

    /**
     * Overrides the method to determine if the specified point is contained within the shape of the component.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     * @return true if the point is contained, otherwise false.
     */
    @Override
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, UIUtils.ROUNDNESS, UIUtils.ROUNDNESS);
        }
        return shape.contains(x, y);
    }

    /**
     * Sets the border color of the pseudo text field.
     *
     * @param color The color to set for the border.
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
