package admin;

import Toaster.Toaster;
import utils.connexionBDD;
import utils.graphisme.HyperlinkText;
import utils.graphisme.TextFieldUsername;
import utils.graphisme.UIUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class represents a window for deleting a store. The user can enter the name of the store
 * to be deleted, and by clicking on the "DELETE STORE" button, the store, its inventory and all associated items
 * will be deleted from the database.
 *
 * @author Tommy Brisset
 * @version 1.0
 * @see JFrame
 */
public class deleteStore extends JFrame {

    /** Toaster component to display information messages. */
    private final Toaster toaster;

    /** The text field for the user ID. */
    private TextFieldUsername nameStoreField;

    /** Information label displayed in the window. */
    private JLabel infoLabel;

    /**
     * Constructor of the `deleteStore` class. Initializes the connection window with the necessary
     * components such as text fields, buttons, etc.
     */
    public deleteStore() {
        JPanel mainJPanel = getMainJPanel();

        addLogo(mainJPanel);

        addSeparator(mainJPanel);

        addUsernameTextField(mainJPanel);


        addButton(mainJPanel);

        cancelButton(mainJPanel);


        this.add(mainJPanel);
        this.pack();
        this.setVisible(true);
        this.toFront();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width / 2 - getWidth() / 2, screenSize.height / 2 - getHeight() / 2);

        toaster = new Toaster(mainJPanel);
    }

    /**
     * Creates and returns the window's main JPanel.
     *
     * @return The main JPanel.
     */
    private JPanel getMainJPanel() {
        this.setUndecorated(true);

        Dimension size = new Dimension(800, 400);

        JPanel panel1 = new JPanel();
        panel1.setSize(size);
        panel1.setPreferredSize(size);
        panel1.setBackground(utils.graphisme.UIUtils.COLOR_BACKGROUND);
        panel1.setLayout(null);

        MouseAdapter ma = new MouseAdapter() {
            int lastX, lastY;

            @Override
            public void mousePressed(MouseEvent e) {
                lastX = e.getXOnScreen();
                lastY = e.getYOnScreen();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getXOnScreen();
                int y = e.getYOnScreen();
                setLocation(getLocationOnScreen().x + x - lastX, getLocationOnScreen().y + y - lastY);
                lastX = x;
                lastY = y;
            }
        };

        panel1.addMouseListener(ma);
        panel1.addMouseMotionListener(ma);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        infoLabel = new JLabel();
        infoLabel.setText("NAME OF THE STORE TO DELETE : ");
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel1.add(infoLabel);
        infoLabel.setBounds(410, 100, 550, 30);

        return panel1;
    }

    /**
     * Adds a vertical separation to the window.
     *
     * @param panel1 The main JPanel.
     */
    private void addSeparator(JPanel panel1) {
        JSeparator separator1 = new JSeparator();
        separator1.setOrientation(SwingConstants.VERTICAL);
        separator1.setForeground(UIUtils.COLOR_OUTLINE);
        panel1.add(separator1);
        separator1.setBounds(310, 80, 1, 240);
    }

    /**
     * Adds the application logo to the window.
     *
     * @param panel1 The main JPanel.
     */
    private void addLogo(JPanel panel1) {
        JLabel label1 = new JLabel();
        label1.setFocusable(false);
        try {
            // Load image using getResourceAsStream
            InputStream inputStream = getClass().getResourceAsStream("/logo200x200.jpg");
            if (inputStream != null) {
                label1.setIcon(new ImageIcon(ImageIO.read(inputStream)));
            } else {
                System.err.println("Image not found!");
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);  // Print the exception details
        }        panel1.add(label1);
        label1.setBounds(50, 100, 200, 200);
    }

    /**
     * Adds a text field for the name of the store to be destroyed.
     *
     * @param panel1 The main JPanel.
     */
    private void addUsernameTextField(JPanel panel1) {
        nameStoreField = new TextFieldUsername();

        nameStoreField.setBounds(423, 169, 260, 44);
        nameStoreField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (nameStoreField.getText().equals(UIUtils.PLACEHOLDER_TEXT_USERNAME)) {
                    nameStoreField.setText("");
                }
                nameStoreField.setForeground(Color.white);
                nameStoreField.setBorderColor(UIUtils.COLOR_INTERACTIVE);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (nameStoreField.getText().isEmpty()) {
                    nameStoreField.setText(UIUtils.PLACEHOLDER_TEXT_USERNAME);
                }
                nameStoreField.setForeground(UIUtils.COLOR_OUTLINE);
                nameStoreField.setBorderColor(UIUtils.COLOR_OUTLINE);
            }
        });
        nameStoreField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER)
                    buttonEventHandler();
            }
        });
        panel1.add(nameStoreField);
    }

    /**
     * Adds the "DELETE STORE" button to the connection window.
     *
     * @param panel1 The main JPanel.
     */
    private void addButton(JPanel panel1) {
        final Color[] loginButtonColors = {UIUtils.COLOR_INTERACTIVE, Color.white};

        JLabel loginButton = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = UIUtils.get2dGraphics(g);
                super.paintComponent(g2);

                Insets insets = getInsets();
                int w = getWidth() - insets.left - insets.right;
                int h = getHeight() - insets.top - insets.bottom;
                g2.setColor(loginButtonColors[0]);
                g2.fillRoundRect(insets.left, insets.top, w, h, UIUtils.ROUNDNESS, UIUtils.ROUNDNESS);

                FontMetrics metrics = g2.getFontMetrics(UIUtils.FONT_GENERAL_UI);
                int x2 = (getWidth() - metrics.stringWidth(UIUtils.BUTTON_DELE_STORE)) / 2;
                int y2 = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
                g2.setFont(UIUtils.FONT_GENERAL_UI);
                g2.setColor(loginButtonColors[1]);
                g2.drawString(UIUtils.BUTTON_DELE_STORE, x2, y2);
            }
        };

        loginButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                buttonEventHandler();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                loginButtonColors[0] = UIUtils.COLOR_INTERACTIVE_DARKER;
                loginButtonColors[1] = UIUtils.OFFWHITE;
                loginButton.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginButtonColors[0] = UIUtils.COLOR_INTERACTIVE;
                loginButtonColors[1] = Color.white;
                loginButton.repaint();
            }
        });

        loginButton.setBackground(UIUtils.COLOR_BACKGROUND);
        loginButton.setBounds(423, 247, 250, 44);
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel1.add(loginButton);
    }

    /**
     * Adds the "CANCEL" button to the connection window.
     *
     * @param panel1 The main JPanel.
     */
    private void cancelButton(JPanel panel1) {
        panel1.add(new HyperlinkText(UIUtils.BUTTON_TEXT_BACK, 423, 300, this::dispose));
    }

    /**
     * Handles the event when a user presses the
     * or presses the "Enter" key in the namestorefield.
     */
    private void buttonEventHandler() {
        String name = nameStoreField.getText();
        if (name.isEmpty()) {
            toaster.warn("Please enter the name of the store.");
            return;
        }
        // on recupere l'id du store
        int id_st = 0;
        int id_inv = 0;
        try {
            Connection connexion = connexionBDD.obtenirConnexion();
            String sql = "SELECT * FROM store WHERE name = ?";
            PreparedStatement ps = connexion.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                toaster.warn("Store not found.");
                connexion.close();
                return;
            }

            id_st = rs.getInt("id");
            connexion.close();

            Connection connexion2 = connexionBDD.obtenirConnexion();
            // on recupere l'id de l'inventaire
            sql = "SELECT * FROM inventory WHERE store_id = ?";
            ps = connexion2.prepareStatement(sql);
            ps.setInt(1, id_st);
            rs = ps.executeQuery();
            while (rs.next()) {
                id_inv = rs.getInt("id");
            }
            connexion2.close();

            Connection connexion3 = connexionBDD.obtenirConnexion();
            // on supprime tout les items de l'inventaire
            sql = "DELETE FROM item WHERE inventory_id = ?";
            ps = connexion3.prepareStatement(sql);
            ps.setInt(1, id_inv);
            ps.executeUpdate();
            connexion3.close();

            Connection connexion4 = connexionBDD.obtenirConnexion();
            // on supprime l'inventaire
            sql = "DELETE FROM inventory WHERE id = ?";
            ps = connexion4.prepareStatement(sql);
            ps.setInt(1, id_inv);
            ps.executeUpdate();
            connexion4.close();

            Connection connexion5 = connexionBDD.obtenirConnexion();
            // on supprime le store
            sql = "DELETE FROM store WHERE id = ?";
            ps = connexion5.prepareStatement(sql);
            ps.setInt(1, id_st);
            ps.executeUpdate();
            toaster.success("The store has been deleted");
            nameStoreField.setText("");
            connexion5.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace(System.out);
        }
    }
}
