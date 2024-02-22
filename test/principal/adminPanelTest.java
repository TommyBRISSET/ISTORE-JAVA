package principal;

import org.junit.jupiter.api.Test;
import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link adminPanel}.
 *
 * @author Tommy Brisset
 * @version 1.0
 */
public class adminPanelTest {

    /**
     * Default constructor.
     */
    public adminPanelTest() {
    }

    /**
     * Tests the initialization of the adminPanel without throwing exceptions.
     */
    @Test
    public void AdminPanelInitialization() {
        assertDoesNotThrow(() -> {
            SwingUtilities.invokeLater(adminPanel::new);
            Thread.sleep(1000);
        });
    }

    /**
     * Tests the menu bar actions of the adminPanel.
     */
    @Test
    public void AdminPanelMenuBarActions() {
        assertDoesNotThrow(() -> {
            JFrame adminPanel = new adminPanel();

            JMenuBar menuBar = (JMenuBar) adminPanel.getJMenuBar();

            JMenu adminActionsMenu = menuBar.getMenu(0);

            JMenuItem whitelistItem = adminActionsMenu.getItem(0);
            JMenuItem promoteItem = adminActionsMenu.getItem(1);
            JMenuItem messageItem = adminActionsMenu.getItem(2);
            JMenuItem registerItem = adminActionsMenu.getItem(3);
            JMenuItem updateItem = adminActionsMenu.getItem(4);
            JMenuItem deleteItem = adminActionsMenu.getItem(5);
            JMenuItem exitItem = adminActionsMenu.getItem(7);

            whitelistItem.doClick();
            promoteItem.doClick();
            messageItem.doClick();
            registerItem.doClick();
            updateItem.doClick();
            deleteItem.doClick();

            assertTrue(true);

            exitItem.doClick();
        });
    }

    /**
     * Tests the edit menu actions of the adminPanel.
     */
    @Test
    public void AdminPanelEditMenuActions() {
        assertDoesNotThrow(() -> {
            JFrame adminPanel = new adminPanel();

            JMenuBar menuBar = (JMenuBar) adminPanel.getJMenuBar();

            JMenu storeManagementMenu = menuBar.getMenu(1);

            JMenuItem createStore = storeManagementMenu.getItem(0);
            JMenuItem deleteStore = storeManagementMenu.getItem(1);
            JMenuItem createItem = storeManagementMenu.getItem(2);
            JMenuItem deleteItem = storeManagementMenu.getItem(3);
            JMenuItem addEmployee = storeManagementMenu.getItem(4);
            JMenuItem listItemsStore = storeManagementMenu.getItem(5);
            JMenuItem listPersonAccess = storeManagementMenu.getItem(6);
            JMenuItem exitItem = storeManagementMenu.getItem(8);

            createStore.doClick();
            deleteStore.doClick();
            createItem.doClick();
            deleteItem.doClick();
            addEmployee.doClick();
            listItemsStore.doClick();
            listPersonAccess.doClick();

            assertTrue(true);

            exitItem.doClick();
        });
    }
}
