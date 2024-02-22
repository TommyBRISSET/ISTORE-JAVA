package utils;

import Toaster.Toaster;
import admin.createItem;
import utils.graphisme.HyperlinkText;
import utils.graphisme.UIUtils;
import utils.graphisme.intFieldId;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

/**
 * The <code>askIDInventory</code> class represents a window allowing the user to enter an inventory ID.
 *
 * <p>
 * The window displays a text field for entering the inventory ID, as well as a "Next" button to validate the entry.
 * When the user clicks the "Next" button, the class checks if the ID is a valid number and if it exists in the database.
 * If the ID is valid and exists, a dialog box appears with the message "Valid ID"; otherwise, an error message is displayed.
 * </p>
 *
 * <p>
 * The class is currently used to create a new item linked to an existing inventory (action 20).
 * </p>
 *
 * <p>
 * Example of usage:
 * </p>
 *
 * <pre>
 * new askIDInventory(20); // To create a new item linked to an existing inventory
 * </pre>
 *
 * @see createItem
 * @see connexionBDD
 * @author Tommy Brisset
 * @version 1.0
 */
public class askIDInventory extends JFrame {

    /** The Toaster component to display information messages. */
    private final Toaster toaster;

    /** The text field for the user's ID. */
    intFieldId idField;

    /** Information label displayed in the window. */
    private JLabel infoLabel;

    /** Action to perform after entering the ID. */
    private int action;

    /**
     * Constructor for the `askId` class. Initializes the window to request an ID.
     *
     * @param action The specified action code (1 for update, 2 for deletion, 3 for another action).
     */
    public askIDInventory(int action) {

        this.action = action;

        JPanel mainJPanel = getMainJPanel();

        addLogo(mainJPanel);

        addSeparator(mainJPanel);

        addIdTextField(mainJPanel);

        addactionButton(mainJPanel);

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
     * Creates and returns the main JPanel of the ID entry window.
     *
     * @return The main JPanel.
     */
    private JPanel getMainJPanel() {
        this.setUndecorated(true);

        Dimension size = new Dimension(800, 400);

        JPanel panel1 = new JPanel();
        panel1.setSize(size);
        panel1.setPreferredSize(size);
        panel1.setBackground(UIUtils.COLOR_BACKGROUND);
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

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        infoLabel = new JLabel();
        infoLabel.setText("ENTER THE ID OF THE INVENTORY : ");
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel1.add(infoLabel);
        infoLabel.setBounds(420, 90, 550, 30);

        return panel1;
    }

    /**
     * Adds a vertical separator to the login window.
     *
     * @param panel1 The main JPanel.
     */
    private void addSeparator(JPanel panel1) {
        JSeparator separator1 = new JSeparator();
        separator1.setOrientation(SwingConstants.VERTICAL);
        separator1.setForeground(UIUtils.COLOR_OUTLINE);
        panel1.add(separator1);
        separator1.setBounds(310, 80, 2, 240);
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
            InputStream inputStream = getClass().getResourceAsStream("/logo200x200.jpg");
            if (inputStream != null) {
                label1.setIcon(new ImageIcon(ImageIO.read(inputStream)));
            } else {
                System.err.println("Image not found!");
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        panel1.add(label1);
        label1.setBounds(50, 100, 200, 200);
    }

    /**
     * Adds the text field for the user's ID to the window.
     *
     * @param panel1 The main JPanel.
     */
    private void addIdTextField(JPanel panel1) {
        idField = new intFieldId();

        idField.setBounds(423, 165, 250, 44);

        idField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER)
                    buttonEventHandler();
            }
        });

        idField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    dispose();
                }
            }
        });
        panel1.add(idField);
    }

    /**
     * Adds the button to perform the action to the window.
     *
     * @param panel1 The main JPanel.
     */
    private void addactionButton(JPanel panel1) {
        final Color[] actionButtonColors = {UIUtils.COLOR_INTERACTIVE, Color.white};

        JLabel actionButton = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = UIUtils.get2dGraphics(g);
                super.paintComponent(g2);

                Insets insets = getInsets();
                int w = getWidth() - insets.left - insets.right;
                int h = getHeight() - insets.top - insets.bottom;
                g2.setColor(actionButtonColors[0]);
                g2.fillRoundRect(insets.left, insets.top, w, h, UIUtils.ROUNDNESS, UIUtils.ROUNDNESS);

                FontMetrics metrics = g2.getFontMetrics(UIUtils.FONT_GENERAL_UI);
                int x2 = (getWidth() - metrics.stringWidth(UIUtils.BUTTON_TEXT_LOGIN)) / 2;
                int y2 = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
                g2.setFont(UIUtils.FONT_GENERAL_UI);
                g2.setColor(actionButtonColors[1]);
                g2.drawString(UIUtils.BUTTON_INT_ID, x2-20, y2);
            }
        };

        actionButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                buttonEventHandler();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                actionButtonColors[0] = UIUtils.COLOR_INTERACTIVE_DARKER;
                actionButtonColors[1] = UIUtils.OFFWHITE;
                actionButton.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                actionButtonColors[0] = UIUtils.COLOR_INTERACTIVE;
                actionButtonColors[1] = Color.white;
                actionButton.repaint();
            }
        });

        actionButton.setBackground(UIUtils.COLOR_BACKGROUND);
        actionButton.setBounds(423, 220, 250, 44);
        actionButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel1.add(actionButton);
    }

    /**
     * Adds the cancel button to the window.
     *
     * @param panel1 The main JPanel.
     */
    private void cancelButton(JPanel panel1) {
        panel1.add(new HyperlinkText(UIUtils.BUTTON_TEXT_BACK, 423, 270, this::dispose));
    }

    /**
     * Handles the button event when a user presses the button or presses the "Enter" key.
     */
    void buttonEventHandler() {
        if (isNumberValid.isAValidNumber(idField.getText())) {
            try {
                Connection connection = connexionBDD.obtenirConnexion();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM inventory WHERE id = ?");
                preparedStatement.setInt(1, Integer.parseInt(idField.getText()));
                ResultSet resultSet = preparedStatement.executeQuery();

                if (!resultSet.next()) {
                    toaster.error("ID not found in the database.");
                    return;
                }
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace(System.out);
            }

            JOptionPane.showMessageDialog(null, "Valid ID: " + idField.getText());
            dispose();
            if (action == 20) {
                new createItem(Integer.parseInt(idField.getText()));
            }

        } else {
            toaster.warn("Invalid ID. Please enter a number.");
        }
    }
}
