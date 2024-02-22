package utils.graphisme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static utils.graphisme.UIUtils.*;
import static java.awt.Cursor.HAND_CURSOR;

/**
 * A custom JLabel representing a hyperlink text that performs an action when pressed.
 *
 * @author Tommy Brisset
 * @version 1.0
 */
public class HyperlinkText extends JLabel {

    /**
     * Constructor for the <code>HyperlinkText</code> class.
     *
     * @param hyperlinkText   The text to be displayed.
     * @param xPos            The x-coordinate position of the text.
     * @param yPos            The y-coordinate position of the text.
     * @param hyperlinkAction The action to be executed when the text is pressed.
     */
    public HyperlinkText(String hyperlinkText, int xPos, int yPos, Runnable hyperlinkAction) {
        super(hyperlinkText);
        setForeground(COLOR_OUTLINE);
        setFont(FONT_FORGOT_PASSWORD);
        setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                hyperlinkAction.run();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setForeground(COLOR_OUTLINE.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setForeground(COLOR_OUTLINE);
            }
        });

        Dimension prefSize = getPreferredSize();
        setBounds(xPos, yPos, prefSize.width, prefSize.height);
    }
}
