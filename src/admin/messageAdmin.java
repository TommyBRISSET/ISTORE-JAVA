package admin;

import utils.connexionBDD;
import utils.graphisme.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the message administration window in the admin center.
 * It displays a list of messages with titles on the left and their contents on the right.
 * The administrator can select a message to view its content and mark it as read.
 *
 * @author Tommy Brisset
 * @version 1.0
 */
public class messageAdmin extends JFrame {

    /**
     * The list of message titles.
     */
    private JList<String> messageList;

    /**
     * The area to display the message content.
     */
    private JTextArea messageContent;

    /**
     * The button to close the window.
     */
    private JButton cancelButton;

    /**
     * The list of message titles.
     */
    private List<String> titles;

    /**
     * The list of message contents.
     */
    private List<String> contents;

    /**
     * Constructs a new MessageAdmin window.
     */
    public messageAdmin() {
        fetchMessagesFromDatabase();

        messageList = new JList<>(titles.toArray(new String[0]));
        messageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        messageList.setCellRenderer(new CustomListCellRenderer());

        messageContent = new JTextArea();
        messageContent.setEditable(false);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());

        messageList.addListSelectionListener(e -> {
            int selectedIndex = messageList.getSelectedIndex();
            if (selectedIndex >= 0) {
                String selectedContent = contents.get(selectedIndex);
                messageContent.setText(selectedContent);
            }
        });

        messageList.addListSelectionListener(e -> {
            int selectedIndex = messageList.getSelectedIndex();
            if (selectedIndex >= 0) {
                int selectedId = getIdFromDatabase(selectedIndex);
                String selectedContent = contents.get(selectedIndex);
                messageContent.setText(selectedContent);

                updateIsReadStatus(selectedId);
            }
        });


        JScrollPane listScrollPane = new JScrollPane(messageList);
        listScrollPane.setPreferredSize(new Dimension(200, 0));


        JScrollPane contentScrollPane = new JScrollPane(messageContent);

        setLayout(new BorderLayout(10, 10));
        add(listScrollPane, BorderLayout.WEST);
        add(contentScrollPane, BorderLayout.CENTER);
        add(cancelButton, BorderLayout.SOUTH);

        UIUtils.styleJList(messageList);
        UIUtils.styleJTextArea(messageContent);
        UIUtils.styleJButton(cancelButton);


        setTitle("ADMIN CENTER - MESSAGES");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
        setSize(800, 400);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);

        refreshData();
    }

    /**
     * Refreshes the displayed data by clearing and fetching new messages from the database.
     */
    private void refreshData() {
        clearData();
        fetchMessagesFromDatabase();
        updateUI();
    }

    /**
     * Updates the user interface with the latest data.
     */
    private void updateUI() {
        messageList.setListData(titles.toArray(new String[0]));
    }

    /**
     * Clears the existing data lists.
     */
    private void clearData() {
        titles = new ArrayList<>();
        contents = new ArrayList<>();
    }

    /**
     * Fetches messages from the database and populates the titles and contents lists.
     */
    private void fetchMessagesFromDatabase() {
        titles = new ArrayList<>();
        contents = new ArrayList<>();

        try (Connection connexion = connexionBDD.obtenirConnexion()) {
            String sql = "SELECT title, content, is_read FROM message ORDER BY id DESC";
            try (PreparedStatement statement = connexion.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    String title = resultSet.getString("title");
                    String content = resultSet.getString("content");
                    boolean isRead = resultSet.getBoolean("is_read");

                    if (!isRead) {
                        title = "<html><b>" + title + "</b></html>";
                    }
                    titles.add(title);
                    contents.add(content);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    /**
     * Updates the is_read status of the selected message in the database.
     *
     * @param selectedId The index of the selected message.
     */
    private void updateIsReadStatus(int selectedId) {
        try (Connection connexion = connexionBDD.obtenirConnexion()) {
            String sql = "UPDATE message SET is_read = ? WHERE id = ?";
            try (PreparedStatement statement = connexion.prepareStatement(sql)) {
                statement.setBoolean(1, true);
                statement.setInt(2, selectedId);

                int rowsUpdated = statement.executeUpdate();

                if (rowsUpdated > 0) {
                    refreshData();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    /**
     * The custom list cell renderer for the message list.
     * It adds a separator border between list items.
     */private int getIdFromDatabase(int selectedIndex) {
        int messageId = -1;
        try (Connection connexion = connexionBDD.obtenirConnexion()) {
            String sql = "SELECT id FROM message WHERE title = ?";
            try (PreparedStatement statement = connexion.prepareStatement(sql)) {
                String cleanTitle = titles.get(selectedIndex).replaceAll("\\<.*?\\>", "");
                statement.setString(1, cleanTitle);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        messageId = resultSet.getInt("id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return messageId;
    }

    /**
     * The custom list cell renderer for the message list.
     * It adds a separator border between list items.
     */
    private class CustomListCellRenderer extends DefaultListCellRenderer {

        private final Color separatorColor = new Color(200, 200, 200);


        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel renderer = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (index > 0) {
                renderer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, separatorColor));
            }
            return renderer;
        }
    }
}
