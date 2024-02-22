package employe;

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

import Toaster.Toaster;
import org.mindrot.jbcrypt.BCrypt;

import utils.graphisme.*;
import utils.verifEmail;
import utils.verifPassword;
import utils.connexionBDD;

/**
 * This class represents a window allowing an employee to update his information.
 *
 * <p>The employee can modify his login (email), password and nickname. The window displays the current information
 * information and allows the employee to enter the new values.</p>
 *
 * @author Tommy Brisset
 * @version 1.0
 * @see JFrame
 */
public class updateUser extends JFrame {

    /** Displays notifications to the user. */
    private final Toaster toaster;

    /** Text field for user name (email). */
    private TextFieldUsername usernameField;

    /** Text field for password. */
    private TextFieldPassword passwordField;

    /** Text field for nickname. */
    private TextFieldPseudo pseudoField;

    /** Information label displayed in the window. */
    private JLabel infoLabel;

    /** id to be updated */
    private int id;

    /**
     * Constructor of the `updateUser` class. Initializes and configures the registration window.
     *
     * @param id The user ID to be updated.
     */
    public updateUser(int id) {

        JPanel mainJPanel = getMainJPanel();

        addLogo(mainJPanel);

        addSeparator(mainJPanel);

        addUsernameTextField(mainJPanel);

        addPasswordTextField(mainJPanel);

        addPseudoTextField(mainJPanel);

        addLoginButton(mainJPanel);

        cancelButton(mainJPanel);

        prefillFields(id);

        this.add(mainJPanel);
        this.pack();
        this.setVisible(true);
        this.toFront();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width / 2 - getWidth() / 2, screenSize.height / 2 - getHeight() / 2);

        toaster = new Toaster(mainJPanel);
    }

    /**
     * Creates and returns the main panel of the registration window.
     *
     * @return The main JPanel of the registration window.
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
        infoLabel.setText("YOU'RE ARE UPDATING THIS ACCOUNT");
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel1.add(infoLabel);
        infoLabel.setBounds(40, 20, 550, 30);

        return panel1;
    }

    /**
     * Adds a vertical separator line to the main panel.
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
     * Adds the application logo to the main panel.
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
     * Adds a text field for the user name (email) to the main panel.
     *
     * @param panel1 The main JPanel.
     */
    private void addUsernameTextField(JPanel panel1) {
        usernameField = new TextFieldUsername();

        usernameField.setBounds(423, 50, 260, 44);
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
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    dispose();
                }
            }
        });

        panel1.add(usernameField);
    }

    /**
     * Adds the text field for the nickname to the main panel.
     *
     * @param panel1 The main JPanel.
     */
    private void addPseudoTextField(JPanel panel1) {
        pseudoField = new TextFieldPseudo();

        pseudoField.setBounds(423, 109, 260, 44);
        pseudoField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (pseudoField.getText().equals(UIUtils.PLACEHOLDER_TEXT_PSEUDO)) {
                    pseudoField.setText("");
                }
                pseudoField.setForeground(Color.white);
                pseudoField.setBorderColor(UIUtils.COLOR_INTERACTIVE);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (pseudoField.getText().isEmpty()) {
                    pseudoField.setText(UIUtils.PLACEHOLDER_TEXT_PSEUDO);
                }
                pseudoField.setForeground(UIUtils.COLOR_OUTLINE);
                pseudoField.setBorderColor(UIUtils.COLOR_OUTLINE);
            }
        });
        pseudoField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER)
                    loginEventHandler();
            }
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    dispose();
                }
            }
        });
        panel1.add(pseudoField);
    }


    /**
     * Adds the password text field to the main panel.
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
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    dispose();
                }
            }
        });
        panel1.add(passwordField);
    }

    /**
     * Pre-fills fields with current database information.
     *
     * @param id The user ID for which to pre-fill.
     */
    private void prefillFields(int id) {
        try (Connection connection = connexionBDD.obtenirConnexion()) {
            String sql = "SELECT email, pseudo FROM user WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String loginDB = resultSet.getString("email");
                        String pseudoDB = resultSet.getString("pseudo");

                        usernameField.setText(loginDB);
                        pseudoField.setText(pseudoDB);
                    } else {
                        toaster.error("User not found.");
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace(System.out);
        }
    }


    /**
     * Adds the registration button to the main panel.
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
                int x2 = (getWidth() - metrics.stringWidth(UIUtils.BUTTON_TEXT_REGISTER)) / 2;
                int y2 = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
                g2.setFont(UIUtils.FONT_GENERAL_UI);
                g2.setColor(loginButtonColors[1]);
                g2.drawString(UIUtils.BUTTON_TEXT_REGISTER, x2, y2);
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
     * Adds the password recovery link to the main panel.
     *
     * @param panel1 The main JPanel.
     */
    private void cancelButton(JPanel panel1) {
        panel1.add(new HyperlinkText(UIUtils.BUTTON_TEXT_BACK, 423, 300, this::dispose));
    }

    /**
     * Manages the registration button click event.
     */
    private void loginEventHandler() {
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

        try (Connection connexion2 = connexionBDD.obtenirConnexion()) {
            try (PreparedStatement statement = connexion2.prepareStatement("SELECT email FROM user WHERE id = ?")) {
                statement.setInt(1, id);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String login2 = resultSet.getString("email");

                        Connection connexion3 = connexionBDD.obtenirConnexion();
                        try (PreparedStatement statement2 = connexion3.prepareStatement("DELETE FROM whitelist WHERE email = ?")) {
                            statement2.setString(1, login2);
                            statement2.executeUpdate();
                        }
                        connexion3.close();
                    }
                    connexion2.close();
                }
            } catch (SQLException exception) {
                exception.printStackTrace(System.out);
                toaster.error("Error while deleting user from whitelist.");
            }

            Connection connexion4 = connexionBDD.obtenirConnexion();
            try (PreparedStatement statement = connexion4.prepareStatement("INSERT INTO whitelist (email) VALUES (?)")) {
                statement.setString(1, login);
                statement.executeUpdate();
            } catch (SQLException exception) {
                exception.printStackTrace(System.out);
                toaster.error("Error while adding user to whitelist.");
            }
            connexion4.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        try (Connection connexion5 = connexionBDD.obtenirConnexion()) {

            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            String pseudo = pseudoField.getText();

            try {

                String sql2 = "UPDATE user SET email = ?, pseudo = ?, password_hash = ? WHERE id = ?";
                try (PreparedStatement preparedStatement2 = connexion5.prepareStatement(sql2)) {
                    preparedStatement2.setString(1, login);
                    preparedStatement2.setString(2, pseudo);
                    preparedStatement2.setString(3, hashedPassword);
                    preparedStatement2.setInt(4, id);

                    preparedStatement2.executeUpdate();
                    toaster.success("User updated successfully !");
                }
                connexion5.close();
            } catch (SQLException exception) {
                exception.printStackTrace(System.out);
                toaster.error("Error while updating user.");
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
