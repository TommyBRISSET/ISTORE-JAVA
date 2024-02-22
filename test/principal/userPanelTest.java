package principal;

import org.junit.jupiter.api.Test;
import javax.swing.*;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the {@link userPanel} class, covering its initialization, menu bar actions, and background image loading.
 *
 * @author Tommy Brisset
 * @version 1.0
 */
public class userPanelTest {

    /**
     * Default constructor.
     */
    public userPanelTest() {
    }

    /**
     * Tests the initialization of the {@link userPanel} class.
     */
    @Test
    public void userPanelInit() {
        assertDoesNotThrow(() -> {
            SwingUtilities.invokeLater(() -> new userPanel(1));
            Thread.sleep(1000);
        });
    }

    /**
     * Tests the menu bar actions of the {@link userPanel} class.
     */
    @Test
    public void userPanelMenuBarActions() {
        assertDoesNotThrow(() -> {
            JFrame userPanel = new userPanel(1);

            JMenuBar menuBar = (JMenuBar) userPanel.getJMenuBar();

            JMenu userActionsMenu = menuBar.getMenu(0);

            JMenuItem updateItem = userActionsMenu.getItem(0);
            JMenuItem registerItem = userActionsMenu.getItem(1);
            JMenuItem deleteItem = userActionsMenu.getItem(2);
            JMenuItem sendMessageItem = userActionsMenu.getItem(3);
            JMenuItem viewMessagesItem = userActionsMenu.getItem(4);
            JMenuItem exitItem = userActionsMenu.getItem(6);

            updateItem.doClick();
            registerItem.doClick();
            deleteItem.doClick();
            sendMessageItem.doClick();
            viewMessagesItem.doClick();

            assertTrue(true);

            exitItem.doClick();
        });
    }

    /**
     * Tests the user access menu actions of the {@link userPanel} class.
     */
    @Test
    public void userPanelEditMenuActions() {
        assertDoesNotThrow(() -> {
            JFrame userPanel = new userPanel(1);

            JMenuBar menuBar = (JMenuBar) userPanel.getJMenuBar();

            JMenu userAccessMenu = menuBar.getMenu(1);

            JMenuItem viewAllUsersItem = userAccessMenu.getItem(0);
            JMenuItem modifyItem = userAccessMenu.getItem(1);
            JMenuItem listPersonAccessItem = userAccessMenu.getItem(2);
            JMenuItem exitItem = userAccessMenu.getItem(4);

            viewAllUsersItem.doClick();
            modifyItem.doClick();
            listPersonAccessItem.doClick();

            assertTrue(true);

            exitItem.doClick();
        });
    }

    /**
     * Tests the loading of the background image in the {@link userPanel} class.
     */
    @Test
    public void BackgroundImageLoading() {
        assertDoesNotThrow(() -> {
            userPanel userPanel = new userPanel(1);
            BufferedImage backgroundImage = userPanel.getBackgroundImage();

            assertNotNull(backgroundImage, "Background image should not be null");
        });
    }
}
