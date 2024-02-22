package admin;

import utils.connexionBDD;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class represents a window for deleting an item from the database. The user can confirm
 * by clicking on the "DELETE ITEM" button. The selected item's information is displayed for * confirmation.
 * confirmation.
 *
 * @author Tommy Brisset
 * @version 1.0
 * @see JFrame
 */
public class deleteItem extends JFrame {

    /** The identifier of the item to be deleted. */
    private final int id;

    /**
     * Constructor of the `deleteItem` class.
     *
     * <p>Initializes the window with the title "DELETE ITEM", configures its parameters and displays information about
     * the selected item. The user can confirm deletion by clicking on the "DELETE ITEM" button.</p>
     *
     * @param id The identifier of the item to be deleted.
     */
    public deleteItem(int id) {
        super();
        this.id = id;

        setTitle("DELETE ITEM");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        displayItemInfo(panel, gbc);

        JButton deleteButton = new JButton("DELETE ITEM");
        deleteButton.addActionListener(e -> {
            int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this item ?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                if (deleteItemFromDatabase(id)) {
                    JOptionPane.showMessageDialog(null, "Item deleted successfully.");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to delete item.");
                }
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 10, 10);

        panel.add(deleteButton, gbc);

        JButton cancelButton = new JButton("CANCEL");
        cancelButton.addActionListener(e -> {
            dispose();
        });

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(cancelButton, gbc);

        add(panel);

        setUndecorated(true);

        setVisible(true);
    }

    /**
     * Displays item information in the panel.
     *
     * @param panel The display panel.
     * @param gbc Grid constraints for component placement.
     */
    private void displayItemInfo(JPanel panel, GridBagConstraints gbc) {
        try {
            Connection connection = connexionBDD.obtenirConnexion();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM item WHERE id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                JLabel itemNameLabel = new JLabel("<html><font size='5'><b>Item Name:</b> " + resultSet.getString("name") + "<br/>" +
                        "<b>Item price:</b> " + resultSet.getString("price") + "<br/>" +
                        "<b>Item quantity:</b> " + resultSet.getString("quantity") + "</font></html>");

                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.insets = new Insets(10, 10, 10, 10);
                panel.add(itemNameLabel, gbc);

            } else {
                JOptionPane.showMessageDialog(null, "Item not found.");
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace(System.out);
        }
    }

    /**
     * Deletes the item from the database using the specified identifier.
     *
     * @param itemId The identifier of the item to be deleted.
     * @return `true` if the item is successfully deleted, otherwise `false`.
     */
    private boolean deleteItemFromDatabase(int itemId) {
        try {
            Connection connection2 = connexionBDD.obtenirConnexion();
            PreparedStatement preparedStatement = connection2.prepareStatement("DELETE FROM item WHERE id = ?");
            preparedStatement.setInt(1, itemId);
            int rowsAffected = preparedStatement.executeUpdate();

            connection2.close();
            return rowsAffected > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace(System.out);
            return false;

        }
    }
}
