package employe;

import Toaster.Toaster;
import utils.connexionBDD;
import utils.graphisme.HyperlinkText;
import utils.graphisme.TextFieldTitle;
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
 * The `sendMessage` class represents a graphical user interface for sending messages.
 * Users can input a title and a message content, and upon clicking the send button,
 * the message is sent to the specified receiver (in this case, an admin).
 *
 * The class extends the JFrame class to create a window for user interaction.
 *
 * @author Tommy Brisset
 * @version 1.0
 */
public class sendMessage extends JFrame {

    /** Toaster component to display information messages. */
    private final Toaster toaster;

    /** The text field for the user ID. */
    private JTextArea messageField;

    /** The text field for the user's password. */
    private TextFieldTitle titleField;

    /** Information label displayed in the window. */
    private JLabel infoLabel;

    /** The user's ID. */
    private int id_user;

    /** The admin ID. */
    private int id_admin;

    /**
     * Constructor of the `sendMessage` class. Initializes the connection window with
     * components such as text fields, buttons, etc.
     *
     * @param id_user The ID of the user sending the message.
     */
    public sendMessage(int id_user) {

        this.id_user = id_user;
        JPanel mainJPanel = getMainJPanel();

        addLogo(mainJPanel);

        addSeparator(mainJPanel);

        addFieldTitle(mainJPanel);

        addMessageField(mainJPanel);

        addLoginButton(mainJPanel);

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
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setText("You're about to send a message to the admin");
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
            e.printStackTrace(System.out);
        }        panel1.add(label1);
        label1.setBounds(50, 100, 200, 200);
    }


    /**
     * Adds the title text field to the connection window.
     *
     * @param panel1 The main JPanel.
     */
    private void addFieldTitle(JPanel panel1) {
        titleField = new TextFieldTitle();

        titleField.setBounds(435, 75, 260, 44);
        titleField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (titleField.getText().equals(UIUtils.PLACEHOLDER_TEXT_TITLE)) {
                    titleField.setText("");
                }
                titleField.setForeground(Color.white);
                titleField.setBorderColor(UIUtils.COLOR_INTERACTIVE);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (titleField.getText().isEmpty()) {
                    titleField.setText(UIUtils.PLACEHOLDER_TEXT_TITLE);
                }
                titleField.setForeground(UIUtils.COLOR_OUTLINE);
                titleField.setBorderColor(UIUtils.COLOR_OUTLINE);
            }
        });

        titleField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER)
                    buttonEventHandler();
            }
        });
        panel1.add(titleField);
    }


    /**
     * Adds the message text area to the connection window.
     *
     * @param panel1 The main JPanel.
     */
    private void addMessageField(JPanel panel1) {
        messageField = new JTextArea();

        messageField.setLineWrap(true);
        messageField.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(messageField);
        scrollPane.setBounds(410, 130, 310, 140);

        messageField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (messageField.getText().equals(UIUtils.PLACEHOLDER_TEXT_MESS)) {
                    messageField.setText("");
                }
                messageField.setForeground(Color.BLACK);
                messageField.setBorder(BorderFactory.createLineBorder(UIUtils.COLOR_INTERACTIVE));
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (messageField.getText().isEmpty()) {
                    messageField.setText(UIUtils.PLACEHOLDER_TEXT_MESS);
                }
                messageField.setForeground(Color.BLACK);
                messageField.setBorder(BorderFactory.createLineBorder(UIUtils.COLOR_OUTLINE));
            }
        });

        panel1.add(scrollPane);
    }


    /**
     * Adds the send button to the connection window.
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
                int x2 = (getWidth() - metrics.stringWidth(UIUtils.BUTTON_SEND_MESS)) / 2;
                int y2 = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
                g2.setFont(UIUtils.FONT_GENERAL_UI);
                g2.setColor(loginButtonColors[1]);
                g2.drawString(UIUtils.BUTTON_SEND_MESS, x2, y2);
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
        loginButton.setBounds(423, 290, 250, 44);
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel1.add(loginButton);
    }


    /**
     * Adds a "Cancel" button to the connection window.
     *
     * @param panel1 The main JPanel.
     */
    private void cancelButton(JPanel panel1) {
        panel1.add(new HyperlinkText(UIUtils.BUTTON_TEXT_BACK, 423, 340, this::dispose));
    }


    /**
     * Handles the send event when a user presses the send button
     * or presses the "Enter" key in the message field.
     */
    private void buttonEventHandler() {
        // on recupere l'id d'un administrator
        try {
            Connection connection1 = connexionBDD.obtenirConnexion();
            PreparedStatement preparedStatement = connection1.prepareStatement("SELECT id FROM user WHERE role = 'admin'");
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.getResultSet();
            resultSet.next();
            id_admin = resultSet.getInt("id");
            preparedStatement.close();
            connection1.close();
        } catch (SQLException e) {
            toaster.error("An error occurred while sending the message");
            e.printStackTrace(System.out);
        }


        try {
            String title = titleField.getText();
            String message = messageField.getText();

            if (title.equals(UIUtils.PLACEHOLDER_TEXT_TITLE) || title.isEmpty() || message.equals(UIUtils.PLACEHOLDER_TEXT_MESS) || message.isEmpty()) {
                toaster.warn("Please fill in all the fields");
                return;
            }

            Connection connection = connexionBDD.obtenirConnexion();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO message (sender_id, receiver_id, title, content) VALUES (?, ?, ?, ?)");
            preparedStatement.setInt(1, id_user);
            preparedStatement.setInt(2, id_admin);
            preparedStatement.setString(3, title);
            preparedStatement.setString(4, message);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();

            toaster.success("Message sent successfully");
            titleField.setText(UIUtils.PLACEHOLDER_TEXT_TITLE);
            messageField.setText(UIUtils.PLACEHOLDER_TEXT_MESS);
        } catch (SQLException e) {
            toaster.error("An error occurred while sending the message");
            e.printStackTrace(System.out);
        }
        
    }
}
