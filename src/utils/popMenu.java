package utils;

import principal.LoginFrame;
import utils.graphisme.UIUtils;

import javax.swing.*;
import java.util.Objects;

/**
 * This class provides a static method to create a context menu with an option
 * to close the session and log out.
 *
 * @author Tommy Brisset
 * @version 1.0
 */
public class popMenu {

    /**
     * Default constructor.
     */
    public popMenu() {

    }

    /**
     * Creates a context menu with an option to close the session and log out.
     *
     * @param frame The JFrame to which the context menu will be associated.
     * @return The created JPopupMenu.
     */
    public static JPopupMenu createPopupMenu(JFrame frame) {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem closeMenuItem = new JMenuItem("Close Session and Logout");
        closeMenuItem.setFont(UIUtils.FONT_GENERAL_UI);
        closeMenuItem.setForeground(UIUtils.COLOR_INTERACTIVE);

        closeMenuItem.addActionListener(e -> {
            frame.dispose();
            new LoginFrame();
        });

        popupMenu.add(closeMenuItem);

        UIManager.put("PopupMenu.background", UIUtils.COLOR_BACKGROUND);
        UIManager.put("PopupMenu.border", BorderFactory.createLineBorder(UIUtils.COLOR_OUTLINE));
        UIManager.put("MenuItem.foreground", UIUtils.COLOR_OUTLINE);
        UIManager.put("MenuItem.selectionBackground", UIUtils.COLOR_INTERACTIVE);
        UIManager.put("MenuItem.selectionForeground", UIUtils.OFFWHITE);

        return popupMenu;
    }
}
