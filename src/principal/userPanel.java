package principal;

import admin.messageAdmin;
import employe.*;
import utils.graphisme.UIUtils;
import utils.popMenu;

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

/**
 * This class represents the main panel for a user. It provides a graphical interface
 * allowing the user to access various actions related to their account and permissions.
 *
 * <p>Main features include updating user information, registering a new user, deleting
 * the current account, viewing all users, modifying an item in a store, and listing users
 * with access to a specific store.</p>
 *
 * <p>This panel displays a background image and offers a menu for each category of actions
 * accessible to the user. The "USER ACTIONS" and "USER ACCESS" menus provide specific options
 * for managing the user account and access permissions.</p>
 *
 * <p>The window is designed to be non-resizable to maintain a consistent appearance.</p>
 *
 * @author Tommy Brisset
 * @version 1.0
 * @see JFrame
 */
public class userPanel extends JFrame  {

    /** The background image of the panel. */
    private BufferedImage backgroundImage;

    /**
     * Constructor for the `UserPanel` class. Initializes the window with a title, size,
     * and positions it in the center of the screen.
     *
     * @param ID The unique identifier of the user.
     */
    public userPanel(int ID){
        super();

        setTitle("PANEL PANEL");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 700);
        setLocationRelativeTo(null);

        try {
            backgroundImage = ImageIO.read(new File((Objects.requireNonNull(getClass().getClassLoader().getResource("user.jpg")).getFile())));
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

        JMenu fileMenu = new JMenu("USER ACTIONS");
        UIUtils.styleJMenu(fileMenu);

        JMenuItem updateItem = new JMenuItem("UPDATE MY INFORMATIONS");
        updateItem.addActionListener(e -> new updateUser(ID).setVisible(true));
        updateItem.setAccelerator(KeyStroke.getKeyStroke('U', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        UIUtils.styleJMenuItem(updateItem);

        JMenuItem registerItem= new JMenuItem("REGISTER A NEW USER");
        registerItem.addActionListener(e -> new registerFrame(true).setVisible(true));
        registerItem.setAccelerator(KeyStroke.getKeyStroke('R', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        UIUtils.styleJMenuItem(registerItem);

        JMenuItem deleteItem = new JMenuItem("DELETE MY ACCOUNT");
        deleteItem.addActionListener(e -> new deleteUser(ID).setVisible(true));
        deleteItem.setAccelerator(KeyStroke.getKeyStroke('D', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        UIUtils.styleJMenuItem(deleteItem);

        JMenuItem sendItem = new JMenuItem("SEND MESSAGE TO ADMIN");
        sendItem.addActionListener(e -> new sendMessage(ID).setVisible(true));
        sendItem.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        UIUtils.styleJMenuItem(sendItem);

        JMenuItem messageItem = new JMenuItem("VIEW MESSAGES");
        messageItem.addActionListener(e -> new messageUser(ID).setVisible(true));
        messageItem.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        UIUtils.styleJMenuItem(messageItem);

        JMenuItem exitItem = new JMenuItem("EXIT");
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
        UIUtils.styleJMenuItem(exitItem);

        fileMenu.add(updateItem);
        fileMenu.add(registerItem);
        fileMenu.add(deleteItem);
        fileMenu.add(sendItem);
        fileMenu.add(messageItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        setContentPane(panel);

        setLocationRelativeTo(null);

        menuBar.add(fileMenu);

        setJMenuBar(menuBar);


        JMenu editMenu = new JMenu("USER ACCESS");
        UIUtils.styleJMenu(editMenu);

        JMenuItem cutItem = new JMenuItem("VIEW ALL USERS");
        cutItem.addActionListener(e -> new listAllUsers().setVisible(true));
        cutItem.setAccelerator(KeyStroke.getKeyStroke('V', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        UIUtils.styleJMenuItem(cutItem);

        JMenuItem modifyItem = new JMenuItem("MODIFY AN ITEM IN A STORE");
        modifyItem.addActionListener(e -> new listEmployeStore(ID,1).setVisible(true));
        modifyItem.setAccelerator(KeyStroke.getKeyStroke('M', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        UIUtils.styleJMenuItem(modifyItem);

        JMenuItem listPersonAcess = new JMenuItem("LIST ALL USER ACCESS TO SPECIFIC STORE");
        listPersonAcess.addActionListener(e -> new listEmployeStore(ID,2).setVisible(true));
        listPersonAcess.setAccelerator(KeyStroke.getKeyStroke('L', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        UIUtils.styleJMenuItem(listPersonAcess);

        JMenuItem exitItem2 = new JMenuItem("EXIT");
        exitItem2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
        UIUtils.styleJMenuItem(exitItem2);

        editMenu.add(cutItem);
        editMenu.add(modifyItem);
        editMenu.add(listPersonAcess);
        editMenu.addSeparator();
        editMenu.add(exitItem2);

        menuBar.add(editMenu);

        setResizable(false);

        panel.setComponentPopupMenu(popMenu.createPopupMenu(this));

        setVisible(true);
    }

    /**
     * Returns the background image of the panel.
     *
     * @return The background image of the panel.
     */
    public BufferedImage getBackgroundImage() {
        return backgroundImage;
    }
}
