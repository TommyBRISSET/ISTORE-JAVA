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

import static utils.LastID.obtenirDernierID;

/**
 * This class represents the registration window. Users can register by providing
 * a username (email), nickname and password.
 *
 * <p>If no administrator is registered, the first registered user will be considered an
 * an administrator. Otherwise, new users will be registered as employees.</p>
 *
 * <p>The registration window includes fields for username, nickname, and password.
 * The user can also return to the login page by clicking on a link.</p>
 *
 * <p>The class uses the BCrypt library to hash passwords before saving them in
 * the database.</p>
 *
 * @author Tommy Brisset
 * @version 1.0
 * @see JFrame
 */
public class registerFrame extends JFrame {

    /** Displays notifications to the user. */
    private final Toaster toaster;

    /** Text field for user name (email). */
    private TextFieldUsername usernameField;

    /** Text field for password. */
    private TextFieldPassword passwordField;

    /** Text field for nickname. */
    private TextFieldPseudo pseudoField;

    /** Indicates whether at least one administrator is registered in the database. */
    private boolean adminExists;

    /** Information label displayed in the window. */
    private JLabel infoLabel;

    /** Information label displayed in the window. */
    private JLabel infoLabelPassword;

    /** Password progress bar. */
    private JProgressBar passwordStrengthBar;

    /** Indicates whether the admin is active. */
    private boolean adminActifMenu;

    /**
     * Constructor of the `RegisterFrame` class. Initializes and configures the registration window.
     * @param adminActif Indicates whether the admin is active.
     */
    public registerFrame(boolean adminActif) {
        JPanel mainJPanel = getMainJPanel();

        addLogo(mainJPanel);

        addSeparator(mainJPanel);

        addUsernameTextField(mainJPanel);

        addPasswordTextField(mainJPanel);

        addPseudoTextField(mainJPanel);

        adminActifMenu = adminActif;

        if (!adminActif){
            addRegisterButton(mainJPanel);
        }
        addLoginButton(mainJPanel);

        cancelButton(mainJPanel);


        this.adminExists = false;
        checkAdminUser();
        if (adminExists) {
            infoLabel.setText("TYPE ACCOUNT : EMPLOYEE");
        } else {
            infoLabel.setForeground(Color.RED);
            infoLabel.setText("TYPE ACCOUNT : ADMINISTRATOR");
        }
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
        infoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel1.add(infoLabel);
        infoLabel.setBounds(40, 20, 550, 30);

        infoLabelPassword = new JLabel();
        infoLabelPassword.setForeground(Color.WHITE);
        infoLabelPassword.setText("<html><b>Password</b> must contain at least 8 characters,<br> one lowercase letter and one special character.</html>");
        infoLabelPassword.setFont(new Font("Arial", Font.PLAIN, 11));
        panel1.add(infoLabelPassword);
        infoLabelPassword.setBounds(15, 320, 260, 60);


        return panel1;
    }

    /**
     * Adds a vertical dividing line to the main panel.
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
     * Adds the nickname text field to the main panel.
     *
     * @param panel1 The main JPanel.
     */
    private void addPseudoTextField(JPanel panel1) {
        pseudoField = new TextFieldPseudo();
        pseudoField.setText(UIUtils.PLACEHOLDER_TEXT_PSEUDO);
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

        setLayout(new BorderLayout());

        passwordStrengthBar = new JProgressBar(0, 100);
        passwordStrengthBar.setStringPainted(true);
        passwordStrengthBar.setString(" ");
        passwordStrengthBar.setForeground(Color.BLACK);
        passwordStrengthBar.setFont(new Font("Arial", Font.BOLD, 12));
        passwordStrengthBar.setBorder(null);
        passwordStrengthBar.setBounds(423, 220, 260, 15);
        add(passwordStrengthBar);

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
                // Met à jour la force du mot de passe
                updatePasswordStrength(passwordField);
            }
        });
        panel1.add(passwordField);
    }

    /**
     *Sets password strength and updates progress bar.
     *
     * @param strength Password strength (0-100).
     */
    public void setPasswordStrength(int strength) {
        passwordStrengthBar.setValue(strength);

        if (strength >= 95) {
            passwordStrengthBar.setForeground(Color.GREEN);
            passwordStrengthBar.setString("Very strong password");
        } else if (strength >= 90) {
            passwordStrengthBar.setForeground(Color.YELLOW);
            passwordStrengthBar.setString("Strong password");
        } else if (strength >= 60) {
            passwordStrengthBar.setForeground(Color.ORANGE);
            passwordStrengthBar.setString("Medium password");
        } else {
            passwordStrengthBar.setString("Weak password");
            passwordStrengthBar.setForeground(Color.RED);
        }
    }

    /**
     * Updates password strength according to specified criteria.
     *
     * @param passwordField The password field.
     */
    private void updatePasswordStrength(TextFieldPassword passwordField) {
        char[] password = passwordField.getPassword();
        int length = password.length;

        int strength = 0;

        if (length >= 8) {
            strength += 30;
        }
        if (containsUpperCase(password)) {
            strength += 30;
        }
        if (containsSpecialCharacter(password)) {
            strength += 30;
        }
        if (containsDigit(password)) {
            strength += 30;
        }
        setPasswordStrength(strength);
    }

    /**
     * Checks whether the character array contains at least one uppercase character.
     *
     * @param password The password character array.
     * @return true if the password contains at least one uppercase letter, otherwise false.
     */
    private boolean containsUpperCase(char[] password) {
        for (char c : password) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the character array contains at least one digit.
     *
     * @param password The password's character array.
     * @return true if the password contains at least one digit, otherwise false.
     */
    private boolean containsDigit(char[] password) {
        for (char c : password) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether the character array contains at least one special character.
     *
     * @param password The password's character array.
     * @return true if the password contains at least one special character, otherwise false.
     */
    private boolean containsSpecialCharacter(char[] password) {
        String specialCharacters = "!@#$%^&*()-_=+[]{}|;:'\",.<>?";
        for (char c : password) {
            if (specialCharacters.contains(String.valueOf(c))) {
                return true;
            }
        }
        return false;
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
        if (adminActifMenu ) {
            panel1.add(new HyperlinkText(UIUtils.BUTTON_TEXT_BACK, 423, 300, this::dispose));
        } else {
            panel1.add(new HyperlinkText(UIUtils.BUTTON_TEXT_CANCEL, 423, 300, this::dispose));
        }
    }


    /**
     * Adds the link to connect to the main panel.
     *
     * @param panel1 The main JPanel.
     */
    private void addRegisterButton(JPanel panel1) {
        panel1.add(new HyperlinkText(UIUtils.BUTTON_TEXT_LOGIN, 631, 300, () -> {
            dispose();
            new LoginFrame();
        }));
    }

    /**
     * Checks whether at least one user with the admin role is registered in the database.
     * If no admin is found, the `adminExists` variable is set to false.
     */
    private void checkAdminUser() {
        try (Connection connection3 = connexionBDD.obtenirConnexion()) {
            // Vérifier s'il y a au moins un utilisateur avec le rôle admin
            String checkAdminQuery = "SELECT COUNT(*) FROM user WHERE role = 'admin'";
            try (PreparedStatement checkAdminStatement = connection3.prepareStatement(checkAdminQuery)) {
                ResultSet resultSet = checkAdminStatement.executeQuery();
                resultSet.next();
                int adminCount = resultSet.getInt(1);

                adminExists = (adminCount > 0);
                connection3.close();
            }
        } catch (SQLException exception) {
            exception.printStackTrace(System.out);
        }
    }

    /**
     * Inserts a new administrator user in the database with the "admin" role.
     */
    private void insertAdminUser() {

        String login = usernameField.getText();
        if (!verifEmail.isValidEmail(login)) {
            toaster.warn("Please enter a valid email.");
            return;
        }
        String password = String.valueOf(passwordField.getPassword());
        if (!verifPassword.isValidPassword(password)) {
            toaster.warn("Please enter a valid password.");
            toaster.warn("Password must contain at least 8 characters, one uppercase letter, one lowercase letter, one digit, and one special character.");
            return;
        }
        try (Connection connexion4 = connexionBDD.obtenirConnexion()) {

            login = usernameField.getText();
            String pseudo = pseudoField.getText();
            password = String.valueOf(passwordField.getPassword());

            // Hacher le mot de passe avant de l'enregistrer dans la base de données
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            String role = "admin";

            String sql = "INSERT INTO user (email, pseudo, password_hash, role) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connexion4.prepareStatement(sql)) {
                preparedStatement.setString(1, login);
                preparedStatement.setString(2, pseudo);
                preparedStatement.setString(3, hashedPassword);
                preparedStatement.setString(4, role);

                preparedStatement.executeUpdate();
                connexion4.close();
            }
        } catch (SQLException exception) {
            exception.printStackTrace(System.out);
            toaster.error("Error while inserting admin user.");
        }
        dispose();
        new LoginFrame();
    }

    /**
     * Inserts a new user in the database with the "employe" role.
     */
    private void insertUser(){
        String login = usernameField.getText();
        if (!verifEmail.isValidEmail(login)) {
            toaster.warn("Please enter a valid email.");
            return;
        }
        String password = String.valueOf(passwordField.getPassword());
        if (!verifPassword.isValidPassword(password)) {
            toaster.warn("Please enter a valid password.");
            toaster.warn("Password must contain at least 8 characters, one uppercase letter, one lowercase letter, one digit, and one special character.");
            return;
        }
        // on verifie si le compte avec l'email existe deja dans la table
        try (Connection connexion5 = connexionBDD.obtenirConnexion()) {

            String sql = "SELECT * FROM user WHERE email = ?";
            try (PreparedStatement preparedStatement = connexion5.prepareStatement(sql)) {
                preparedStatement.setString(1, login);
                if (preparedStatement.executeQuery().next()) {
                    toaster.warn("Email already exists.");
                    toaster.warn("Please enter a different email.");
                    passwordField.setText("");
                    return;
                }
                connexion5.close();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (Connection connexion = connexionBDD.obtenirConnexion()) {
            String sql = "SELECT * FROM whitelist WHERE email = ?";
            try (PreparedStatement preparedStatement = connexion.prepareStatement(sql)) {
                preparedStatement.setString(1, login);
                if (preparedStatement.executeQuery().next()) {

                    // Hacher le mot de passe avant de l'enregistrer dans la base de données
                    String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

                    String pseudo = pseudoField.getText();

                    try {
                        Connection connexion2 = connexionBDD.obtenirConnexion();
                        int ID = obtenirDernierID(connexion, "user");
                        String role = "employe";

                        String sql2 = "INSERT INTO user (id, email, pseudo,password_hash,role) VALUES (?, ?, ?,?,?)";
                        try (PreparedStatement preparedStatement2 = connexion2.prepareStatement(sql2)) {
                            preparedStatement2.setInt(1, ID);
                            preparedStatement2.setString(2, login);
                            preparedStatement2.setString(3, pseudo);
                            preparedStatement2.setString(4, hashedPassword);
                            preparedStatement2.setString(5, role);

                            preparedStatement2.executeUpdate();
                            toaster.success("User registered successfully!");
                            toaster.success("YOU can now login.");
                        }
                        connexion2.close();
                    } catch (SQLException exception) {
                        exception.printStackTrace(System.out);
                        toaster.error("Error while registering user.");
                    }
                } else {
                    toaster.warn("Email not in whitelist.");
                    toaster.warn("You are not allowed to register. Please contact the administrator.");
                    return;
                }
                connexion.close();
            }
        } catch (SQLException exception) {
            exception.printStackTrace(System.out);
            toaster.error("Error while registering user.");
        }
        dispose();
        if (!adminActifMenu ){
            new LoginFrame();
        }
    }

    /**
     * Checks if all fields are filled in.
     *
     * @return true if all fields are filled, otherwise false.
     */
    private boolean allFieldsFilled() {
        return !usernameField.getText().isEmpty() && !pseudoField.getText().isEmpty() && passwordField.getPassword().length > 0;
    }

    /**
     * Manages the registration button click event.
     */
    void loginEventHandler() {
        if (adminExists) {
            if (allFieldsFilled()) {
                insertUser();
            } else {
                toaster.warn("Please fill in all fields.");
            }
        } else {
            if (allFieldsFilled()) {
                insertAdminUser();
            } else {
                toaster.warn("Please fill in all fields.");
            }
        }
    }

    /**
     * Returns the username field.
     *
     * @return The username field.
     */
    public TextFieldUsername getUsernameField() {
        return usernameField;
    }

    /**
     * Returns the password field.
     *
     * @return The password field.
     */
    public TextFieldPassword getPasswordField() {
        return passwordField;
    }

    /**
     * Returns the nickname field.
     *
     * @return The nickname field.
     */
    public TextFieldPseudo getPseudoField() {
        return pseudoField;
    }

    /**
     * Returns the information label.
     *
     * @param adminExists The information label.
     */
    public void setAdminExists(boolean adminExists) {
        this.adminExists = adminExists;
    }
}
