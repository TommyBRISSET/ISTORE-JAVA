package utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.swing.*;

/**
 * Test class for the {@link popMenu} class.
 * <p>
 * The tests mainly check whether the contextual menu is created correctly.
 * </p>
 * @author Tommy Brisset
 * @version 1.0
 */
public class popMenuTest {

    /**
     * * Default constructor.
     */
    public popMenuTest() {
    }
    /**
     * Test the {@link popMenu#createPopupMenu(JFrame)} method by checking whether the context menu
     * is created correctly.
     */
    @Test
    public void testPopupMenu() {
        JFrame dummyFrame = new JFrame();
        JPopupMenu popupMenu = popMenu.createPopupMenu(dummyFrame);

        assertNotNull(popupMenu, "Le menu contextuel ne doit pas être null");

        assertEquals(1, popupMenu.getComponentCount(), "Le menu contextuel doit contenir un élément");

        JMenuItem menuItem = (JMenuItem) popupMenu.getComponent(0);
        assertNotNull(menuItem, "L'élément dans le menu contextuel ne doit pas être null");

        assertEquals(20f, menuItem.getFont().getSize(), 0.1, "Taille de la police incorrecte");
    }
}
