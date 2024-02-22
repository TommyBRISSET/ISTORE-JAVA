package admin;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

import Toaster.Toaster;
import utils.graphisme.HyperlinkText;
import utils.graphisme.TextFieldUsername;
import utils.graphisme.UIUtils;
import utils.verifEmail;

import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utils.connexionBDD;

/**
 * The `updateRole` class represents a window for updating user roles, specifically making a user an admin.
 *
 * <p>This window provides a graphical interface with components such as text fields, buttons, and labels.
 * It allows the admin to enter an email address, check if the user exists, and if not already an admin, update their role to admin.</p>
 *
 * <p>The window includes a draggable area, a separator, a logo, a text field for the email, and buttons for admin actions.
 * The user interface follows the UIUtils color scheme and styling.</p>
 *
 * <p>It uses the Toaster component for displaying information messages in a toast-like manner.</p>
 *
 *
 * @author Tommy Brisset
 * @version 1.0
 * @see JFrame
 */
public class updateRole extends JFrame {

    /** Toaster component to display information messages. */
    private final Toaster toaster;

    /** The text field for the user ID. */
    private TextFieldUsername usernameField;

    /** Information label displayed in the window. */
    private JLabel infoLabel;

    /**
     * Constructor of the `updateRole` class. Initializes the login window with the necessary
     * components such as text fields, buttons, etc.
     */
    public updateRole() {
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
        infoLabel.setText("ENTER EMAIL TO MAKE ADMIN : ");
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
     * Adds the text field for the user ID to the window.
     *
     * @param panel1 The main JPanel.
     */
    private void addUsernameTextField(JPanel panel1) {
        usernameField = new TextFieldUsername();

        usernameField.setBounds(423, 169, 260, 44);
        usernameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (usernameField.getText().equals(UIUtils.PLACEHOLDER_TEXT_USERNAME)) {
                    usernameField.setText("");
                }
                usernameField.setForeground(Color.white);
                usernameField.setBorderColor(UIUtils.COLOR_INTERACTIVE);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (usernameField.getText().isEmpty()) {
                    usernameField.setText(UIUtils.PLACEHOLDER_TEXT_USERNAME);
                }
                usernameField.setForeground(UIUtils.COLOR_OUTLINE);
                usernameField.setBorderColor(UIUtils.COLOR_OUTLINE);
            }
        });
        usernameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER)
                    buttonEventHandler();
            }
        });
        panel1.add(usernameField);
    }

    /**
     * Adds the button to the window.
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
                int x2 = (getWidth() - metrics.stringWidth(UIUtils.BUTTON_TEXT_ADMIN)) / 2;
                int y2 = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
                g2.setFont(UIUtils.FONT_GENERAL_UI);
                g2.setColor(loginButtonColors[1]);
                g2.drawString(UIUtils.BUTTON_TEXT_ADMIN, x2, y2);
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
     * Adds the cancel button to the window.
     *
     * @param panel1 The main JPanel.
     */
    private void cancelButton(JPanel panel1) {
        panel1.add(new HyperlinkText(UIUtils.BUTTON_TEXT_BACK, 423, 300, this::dispose));
    }

    /**
     * Handles the event when a user presses the button or presses the "Enter" key in the usernameField.
     *
     * <p>It checks if the entered email is valid, then queries the database to find the user.
     * If the user is found and not already an admin, their role is updated to admin.</p>
     */
    private void buttonEventHandler() {
        String login = usernameField.getText();
        if (!verifEmail.isValidEmail(login)) {
            toaster.warn("Please enter a valid email.");
            return;
        }
        try (Connection connexion = connexionBDD.obtenirConnexion()) {
            String query = "SELECT * FROM user WHERE email = ?";
            PreparedStatement preparedStatement = connexion.prepareStatement(query);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String role = resultSet.getString("role");
                if (role.equals("admin")) {
                    toaster.warn("User is already an admin.");
                } else {
                    query = "UPDATE user SET role = 'admin' WHERE email = ?";
                    preparedStatement = connexion.prepareStatement(query);
                    preparedStatement.setString(1, login);
                    preparedStatement.executeUpdate();
                    toaster.success("User is now an admin.");
                    connexion.close();
                }
            } else {
                toaster.warn("User not found.");
                connexion.close();
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }
}
