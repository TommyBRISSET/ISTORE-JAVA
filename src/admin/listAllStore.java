package admin;

import principal.listItemsFromStore;
import principal.listPersonAcess;
import utils.connexionBDD;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a window displaying a list of all stores. It allows the user to perform
 * various actions, such as viewing the contents of the store or seeing which users have access to the store.
 *
 * @author Tommy Brisset
 * @version 1.0
 * @see JFrame
 */
public class listAllStore extends JFrame {

    /** The JComboBox containing the store names. */
    private final JComboBox<String> storeComboBox;

    /** The identifier of the selected store. */
    public int storeId;

    /** The inventory ID of the selected store. */
    public int  inventoryId;

    /** The action to be performed (1 to view content, 2 to view users with access). */
    private final int action_to_do;

    /**
     * Default constructor.
     *
     * @param action The action to be performed (1 to view content, 2 to view users with access).
     */
    public listAllStore(int action) {
        setTitle("STORE LIST");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        action_to_do = action;

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel titleLabel = new JLabel("LIST OF ALL STORES");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 20, 10);
        panel.add(titleLabel, gbc);

        storeComboBox = new JComboBox<>(getStoreNames());
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 20, 10);
        panel.add(storeComboBox, gbc);

        JButton viewContentButton = null;
        if (action_to_do == 1) {
            viewContentButton = new JButton("VIEW THE CONTENT OF THE STORE");
        }
        if (action_to_do == 2) {
            viewContentButton = new JButton("VIEW THE USERS WITH ACCESS TO THE STORE");
        }
        assert viewContentButton != null;
        viewContentButton.addActionListener(e -> {
            String selectedStoreName = (String) storeComboBox.getSelectedItem();
            if (selectedStoreName != null) {

                try {
                    Connection connection = connexionBDD.obtenirConnexion();
                    PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM store WHERE name = ?");
                    preparedStatement.setString(1, selectedStoreName);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        storeId = resultSet.getInt("id");
                    }
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace(System.out);
                }
                try {
                    Connection connection2 = connexionBDD.obtenirConnexion();
                    PreparedStatement preparedStatement = connection2.prepareStatement("SELECT id FROM inventory WHERE store_id = ?");
                    preparedStatement.setString(1, String.valueOf(storeId));
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        inventoryId = resultSet.getInt("id");
                    }
                    connection2.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace(System.out);
                }
                if (action_to_do == 1) {
                    new listItemsFromStore(inventoryId, selectedStoreName);
                }
                if (action_to_do == 2) {
                    new listPersonAcess(storeId);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select a store.");
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(viewContentButton, gbc);

        JButton cancelButton = new JButton("CANCEL");
        cancelButton.addActionListener(e -> {
            dispose();
        });

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(cancelButton, gbc);

        add(panel);

        setUndecorated(true);

        setVisible(true);
    }

    /**
     * This method retrieves the names of all the stores from the database.
     *
     * @return An array of strings containing the names of all the stores.
     */
    private String[] getStoreNames() {
        List<String> storeNames = new ArrayList<>();
        try {
            Connection connection3 = connexionBDD.obtenirConnexion();
            PreparedStatement preparedStatement = connection3.prepareStatement("SELECT name FROM store");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                storeNames.add(resultSet.getString("name"));
            }
            connection3.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace(System.out);
        }
        return storeNames.toArray(new String[0]);
    }
}
