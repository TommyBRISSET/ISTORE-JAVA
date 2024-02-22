package principal;
import Toaster.Toaster;
import org.mindrot.jbcrypt.BCrypt;
import utils.connexionBDD;
import utils.graphisme.*;
import utils.graphisme.UIUtils;
import utils.verifEmail;
import utils.verifPassword;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * This class represents the application's login window.
 *
 * <p>The login window allows users to connect to the application by providing
 * their login (email) and password.</p>
 *
 * @author Tommy Brisset
 * @version 1.0
 * @see JFrame
 */
public class LoginFrame extends JFrame {

    /** Toaster component to display information messages. */
    private final Toaster toaster;

    /** The text field for the user ID. */
    private TextFieldUsername usernameField;

    /** The text field for the user's password. */
    private TextFieldPassword passwordField;

    /** Information label displayed in the window. */
    private JLabel infoLabel;

    /**
     * Constructor of the `LoginFrame` class. Initializes the connection window with
     * such as text fields, buttons, etc.
     */
    public LoginFrame() {
        JPanel mainJPanel = getMainJPanel();

        addLogo(mainJPanel);

        addSeparator(mainJPanel);

        addUsernameTextField(mainJPanel);

        addPasswordTextField(mainJPanel);

        addLoginButton(mainJPanel);

        cancelButton(mainJPanel);

        addRegisterButton(mainJPanel);

        this.add(mainJPanel);
        this.pack();
        this.setVisible(true);
        this.toFront();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width / 2 - getWidth() / 2, screenSize.height / 2 - getHeight() / 2);

        toaster = new Toaster(mainJPanel);
    }

    /**
     * Creates and returns the main JPanel of the connection window.
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

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        infoLabel = new JLabel();
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setText("WELCOME TO THE APPLICATION ISTORE");
        infoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel1.add(infoLabel);
        infoLabel.setBounds(40, 20, 550, 30);

        return panel1;
    }

    /**
     * Adds a vertical separation to the connection window.
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
     * Adds the application logo to the login window.
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
            e.printStackTrace();  // Print the exception details
        }        panel1.add(label1);
        label1.setBounds(50, 100, 200, 200);
    }

    /**
     * Adds text field for user ID to login window.
     *
     * @param panel1 The main JPanel.
     */
    private void addUsernameTextField(JPanel panel1) {
        usernameField = new TextFieldUsername();

        usernameField.setBounds(423, 109, 260, 44);
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
                    loginEventHandler();
            }
        });
        panel1.add(usernameField);
    }

    /**
     * Adds the user password text field to the login window.
     *
     * @param panel1 The main JPanel.
     */
    private void addPasswordTextField(JPanel panel1) {
        passwordField = new TextFieldPassword();

        passwordField.setBounds(423, 168, 260, 44);
        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                passwordField.setForeground(Color.white);
                passwordField.setBorderColor(UIUtils.COLOR_INTERACTIVE);
            }

            @Override
            public void focusLost(FocusEvent e) {
                passwordField.setForeground(UIUtils.COLOR_OUTLINE);
                passwordField.setBorderColor(UIUtils.COLOR_OUTLINE);
            }
        });

        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER)
                    loginEventHandler();
            }
        });
        panel1.add(passwordField);
    }

    /**
     * Adds the login button to the login window.
     *
     * @param panel1 The main JPanel.
     */
    private void addLoginButton(JPanel panel1) {
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
                int x2 = (getWidth() - metrics.stringWidth(UIUtils.BUTTON_TEXT_LOGIN)) / 2;
                int y2 = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
                g2.setFont(UIUtils.FONT_GENERAL_UI);
                g2.setColor(loginButtonColors[1]);
                g2.drawString(UIUtils.BUTTON_TEXT_LOGIN, x2, y2);
            }
        };

        loginButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                loginEventHandler();
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
     * Adds a "Forgot password" button to the login window.
     *
     * @param panel1 The main JPanel.
     */
    private void cancelButton(JPanel panel1) {
        panel1.add(new HyperlinkText(UIUtils.BUTTON_TEXT_CANCEL, 423, 300, this::dispose));
    }

    /**
     * Adds the registration button to the login window.
     *
     * @param panel1 The main JPanel.
     */
    private void addRegisterButton(JPanel panel1) {
        panel1.add(new HyperlinkText(UIUtils.BUTTON_TEXT_REGISTER, 631, 300, () -> {
            dispose();
            new registerFrame(false);
        }));
    }

    /**
     * Handles the login event when a user presses the login button
     * or presses the "Enter" key in the password field.
     */
    void loginEventHandler() {
        String login = usernameField.getText();
        if (!verifEmail.isValidEmail(login)) {
            toaster.warn("Please enter a valid email.");
            return;
        }
        String password = String.valueOf(passwordField.getPassword());
        if (!verifPassword.isValidPassword(password)) {
            toaster.warn("Please enter a valid password.");
            return;
        }

        try (Connection connexion = connexionBDD.obtenirConnexion()) {
            String sql = "SELECT * FROM user WHERE email = ?";
            try (PreparedStatement preparedStatement = connexion.prepareStatement(sql)) {
                preparedStatement.setString(1, login);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int userId = resultSet.getInt("id");
                        String userName = resultSet.getString("email");
                        String userRole = resultSet.getString("role");
                        String hashedPasswordFromDB = resultSet.getString("password_hash");

                        // Vérification du mot de passe saisi avec le mot de passe haché stocké
                        if (BCrypt.checkpw(password, hashedPasswordFromDB)) {
                            if (userRole.equals("admin")) {
                                dispose();
                                new adminPanel();
                            } else if (userRole.equals("employe")) {
                                dispose();
                                new userPanel(userId);
                            }
                        } else {
                            toaster.error("Incorrect password. Please try again.");
                        }

                    }
                }
                connexion.close();
            }
        } catch (SQLException exception) {
            exception.printStackTrace(System.out);
            toaster.error("Error while retrieving user information.");
        }
    }

    /**
     * Returns the text field for the user ID.
     *
     * @return The text field for the user ID.
     */
    public TextFieldUsername getUsernameField() {
        return usernameField;
    }

    /**
     * Returns the text field for the user's password.
     *
     * @return The text field for the user's password.
     */
    public TextFieldPassword getPasswordField() {
        return passwordField;
    }
}
