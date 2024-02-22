package principal;

import admin.*;

import utils.askIDInventory;
import utils.askIDItem;
import utils.askId;

import javax.swing.*;
import java.awt.*;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.KeyStroke;

import utils.popMenu;
import utils.graphisme.UIUtils;

/**
 * This class represents the application's administration panel.
 *
 * <p>The administration panel allows the user to perform specific actions related to
 * application administration, such as user, store and item management.</p>
 *
 * @author Tommy Brisset
 * @version 1.0
 * @see JFrame
 */
public class adminPanel extends JFrame {

    /** Administration panel background image. */
    private BufferedImage backgroundImage;

    /**
     * Constructor of the `AdminPanel` class. Initializes panel with title, size,
     * and positions it at the center of the screen. Loads the background image from a file.
     * Configures the menu bar with administration-related actions.
     */
    public adminPanel() {
        super();

        setTitle("ADMIN PANEL");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 700);
        setLocationRelativeTo(null);

        try {
            backgroundImage = ImageIO.read(new File(Objects.requireNonNull(getClass().getClassLoader().getResource("admin.jpg")).getFile()));
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        panel.setLayout(null);

        JMenuBar menuBar = new JMenuBar();
        UIUtils.styleJMenuBar(menuBar);


        JMenu fileMenu = new JMenu("ADMIN ACTIONS");
        UIUtils.styleJMenu(fileMenu);

        JMenuItem whitelistItem = new JMenuItem("WHITELIST A USER");
        whitelistItem.addActionListener(e -> new whiteListUser().setVisible(true));
        whitelistItem.setAccelerator(KeyStroke.getKeyStroke('W', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        UIUtils.styleJMenuItem(whitelistItem);

        JMenuItem promoteItem = new JMenuItem("PROMOTE A USER TO ADMIN");
        promoteItem.addActionListener(e -> new updateRole().setVisible(true));
        promoteItem.setAccelerator(KeyStroke.getKeyStroke('L', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        UIUtils.styleJMenuItem(promoteItem);

        JMenuItem messageItem = new JMenuItem("MESSAGE CENTER");
        messageItem.addActionListener(e -> new messageAdmin().setVisible(true));
        messageItem.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        UIUtils.styleJMenuItem(messageItem);

        JMenuItem registerItem = new JMenuItem("REGISTER A NEW USER");
        registerItem.addActionListener(e -> new registerFrame(true).setVisible(true));
        registerItem.setAccelerator(KeyStroke.getKeyStroke('R', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        UIUtils.styleJMenuItem(registerItem);

        JMenuItem updateItem = new JMenuItem("UPDATE USER INFORMATIONS");
        updateItem.addActionListener(e -> new askId(1).setVisible(true));
        updateItem.setAccelerator(KeyStroke.getKeyStroke('U', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        UIUtils.styleJMenuItem(updateItem);

        JMenuItem deleteitem = new JMenuItem("DELETE A USER");
        deleteitem.addActionListener(e -> new askId(2).setVisible(true));
        deleteitem.setAccelerator(KeyStroke.getKeyStroke('D', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        UIUtils.styleJMenuItem(deleteitem);

        JMenuItem exitItem = new JMenuItem("EXIT");
        UIUtils.styleJMenuItem(exitItem);
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));

        fileMenu.add(whitelistItem);
        fileMenu.add(promoteItem);
        fileMenu.add(messageItem);
        fileMenu.add(registerItem);
        fileMenu.add(updateItem);
        fileMenu.add(deleteitem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        setContentPane(panel);

        setLocationRelativeTo(null);

        menuBar.add(fileMenu);

        setJMenuBar(menuBar);



        JMenu editMenu = new JMenu("STORE MANAGEMENT");
        UIUtils.styleJMenu(editMenu);


        JMenuItem createStore = new JMenuItem("CREATE A NEW STORE");
        createStore.addActionListener(e -> new createStore().setVisible(true));
        createStore.setAccelerator(KeyStroke.getKeyStroke('C', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        UIUtils.styleJMenuItem(createStore);

        JMenuItem deleteStore = new JMenuItem("DELETE A STORE");
        deleteStore.addActionListener(e -> new deleteStore().setVisible(true));
        deleteStore.setAccelerator(KeyStroke.getKeyStroke('D', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        UIUtils.styleJMenuItem(deleteStore);

        JMenuItem createItem = new JMenuItem("CREATE A NEW ITEM");
        createItem.addActionListener(e -> new askIDInventory(20).setVisible(true));
        createItem.setAccelerator(KeyStroke.getKeyStroke('I', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        UIUtils.styleJMenuItem(createItem);

        JMenuItem deleteItem = new JMenuItem("DELETE AN ITEM");
        deleteItem.addActionListener(e -> new askIDItem(10).setVisible(true));
        deleteItem.setAccelerator(KeyStroke.getKeyStroke('Q', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        UIUtils.styleJMenuItem(deleteItem);

        JMenuItem addEmploye = new JMenuItem("ADD AN EMPLOYEE TO A STORE");
        addEmploye.addActionListener(e -> new askId(3).setVisible(true));
        addEmploye.setAccelerator(KeyStroke.getKeyStroke('E', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        UIUtils.styleJMenuItem(addEmploye);

        JMenuItem listItemsStore= new JMenuItem("MODIFY AN ITEM IN A STORE");
        listItemsStore.addActionListener(e -> new listAllStore(1).setVisible(true));
        listItemsStore.setAccelerator(KeyStroke.getKeyStroke('M', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        UIUtils.styleJMenuItem(listItemsStore);

        JMenuItem listPersonAcess = new JMenuItem("LIST ALL USER ACCESS TO SPECIFIC STORE");
        listPersonAcess.addActionListener(e -> new listAllStore(2).setVisible(true));
        listPersonAcess.setAccelerator(KeyStroke.getKeyStroke('L', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        UIUtils.styleJMenuItem(listPersonAcess);

        JMenuItem exitItem2 = new JMenuItem("EXIT");
        UIUtils.styleJMenuItem(exitItem2);
        exitItem2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));

        editMenu.add(createStore);
        editMenu.add(deleteStore);
        editMenu.add(createItem);
        editMenu.add(deleteItem);
        editMenu.add(addEmploye);
        editMenu.add(listItemsStore);
        editMenu.add(listPersonAcess);
        editMenu.addSeparator();
        editMenu.add(exitItem2);

        menuBar.add(editMenu);

        panel.setComponentPopupMenu(popMenu.createPopupMenu(this));

        setResizable(false);

        setVisible(true);
    }
}
