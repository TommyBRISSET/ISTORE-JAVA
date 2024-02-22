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
 * This class represents a window for creating a new store. The user can enter the name
 * of the new store, and by clicking on the "CREATE STORE" button, the store is added to the database.
 *
 * @author Tommy Brisset
 * @version 1.0
 * @see JFrame
 */
public class createStore extends JFrame {

    /** The toaster used to display messages to the user. */
    private final Toaster toaster;

    /** The text field for the name of the new store. */
    private TextFieldUsername nameStoreField;

    /** The label for the information message. */
    private JLabel infoLabel;

    /**
     * Constructor of the `createStore` class. Initializes the connection window with the necessary
     * components such as text fields, buttons, etc.
     */
    public createStore() {
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
        infoLabel.setText("NAME OF THE NEW STORE : ");
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel1.add(infoLabel);
        infoLabel.setBounds(420, 100, 550, 30);

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
            InputStream inputStream = getClass().getResourceAsStream("/logo200x200.jpg");
            if (inputStream != null) {
                label1.setIcon(new ImageIcon(ImageIO.read(inputStream)));
            } else {
                System.err.println("Image not found!");
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }        panel1.add(label1);
        label1.setBounds(50, 100, 200, 200);
    }

    /**
     * Adds the text field for the new store name.
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
     * Adds the "CREATE STORE" button to the window.
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
                int x2 = (getWidth() - metrics.stringWidth(UIUtils.BUTTON_CREA_STORE)) / 2;
                int y2 = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
                g2.setFont(UIUtils.FONT_GENERAL_UI);
                g2.setColor(loginButtonColors[1]);
                g2.drawString(UIUtils.BUTTON_CREA_STORE, x2, y2);
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
     * Adds the "CANCEL" button to the window.
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
        if (name.length() < 3) {
            toaster.warn("The name of the store must contain at least 2 characters");
        } else {
            try {
                Connection connexion = connexionBDD.obtenirConnexion();
                String sql = "SELECT * FROM store WHERE name = ?";
                PreparedStatement preparedStatement = connexion.prepareStatement(sql);
                preparedStatement.setString(1, name);
                preparedStatement.executeQuery();

                if (preparedStatement.getResultSet().next()) {
                    toaster.warn("This store already exists");
                } else {
                    Connection connexion2 = connexionBDD.obtenirConnexion();
                    sql = "INSERT INTO store (name) VALUES (?)";
                    preparedStatement = connexion2.prepareStatement(sql);
                    preparedStatement.setString(1, name);
                    preparedStatement.executeUpdate();
                    toaster.success("The store has been created");
                    nameStoreField.setText("");
                    connexion2.close();

                    Connection connexion3 = connexionBDD.obtenirConnexion();
                    sql = "SELECT id FROM store WHERE name = ?";
                    preparedStatement = connexion3.prepareStatement(sql);
                    preparedStatement.setString(1, name);


                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            int id = (resultSet).getInt("id");

                            Connection connexion4 = connexionBDD.obtenirConnexion();
                            sql = "INSERT INTO inventory (store_id) VALUES (?)";
                            preparedStatement = connexion4.prepareStatement(sql);
                            preparedStatement.setInt(1, id);
                            preparedStatement.executeUpdate();
                            connexion4.close();
                        } else {
                            toaster.error("Error while creating the inventory");
                        }

                        connexion3.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace(System.out);
                    }
                    connexion.close();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
