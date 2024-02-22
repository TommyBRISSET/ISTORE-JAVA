package principal;

import utils.askIDItem;
import utils.connexionBDD;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 * This class represents a window displaying the list of items in a specific store inventory.
 *
 * <p>The window displays a table with the columns "ID", "Name", "Price", and "Quantity" for each item
 * in the specified inventory.</p>
 *
 * @author Tommy Brisset
 * @version 1.0
 * @see JFrame
 */
public class listItemsFromStore extends JFrame {

    /** The ID of the inventory for which the item list is displayed. */
    private final int inventoryId;
    /** Table of articles. */
    private JTable itemsTable;

    /**
     * Constructor of the `ListItemsFromStore` class. Initializes the window with a title, a size, and positions it
     * in the center of the screen. The list of items is retrieved from the database for the specified inventory.
     *
     * @param inventoryId The unique identifier of the inventory.
     * @param storeName The name of the store linked to the inventory.
     */
    public listItemsFromStore(int inventoryId, String storeName) {
        setTitle("ITEMS IN " + storeName);
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        this.inventoryId = inventoryId;

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel titleLabel = new JLabel("ITEMS IN " + storeName);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 20, 10);
        panel.add(titleLabel, gbc);

        createItemsTable();
        JScrollPane scrollPane = new JScrollPane(itemsTable);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 20, 10);
        panel.add(scrollPane, gbc);

        JButton modifyItemButton = new JButton("MODIFY ITEM");
        modifyItemButton.addActionListener(e -> new askIDItem(11));

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(modifyItemButton, gbc);

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
        pack();

        setUndecorated(true);

        setVisible(true);
    }

    /**
     * This method creates the item table by retrieving data from the database for
     * the specified inventory. Table columns include "ID", "Name", "Price", and "Quantity".
     */
    private void createItemsTable() {
        try {
            Connection connection = connexionBDD.obtenirConnexion();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM item WHERE inventory_id = ?");
            preparedStatement.setInt(1, inventoryId);
            ResultSet resultSet = preparedStatement.executeQuery();

            Vector<Vector<Object>> data = new Vector<>();
            Vector<String> columnNames = new Vector<>();
            columnNames.add("ID");
            columnNames.add("Name");
            columnNames.add("Price");
            columnNames.add("Quantity");

            while (resultSet.next()) {
                Vector<Object> row = new Vector<>();
                row.add(resultSet.getString("id"));
                row.add(resultSet.getString("name"));
                row.add(resultSet.getString("price"));
                row.add(resultSet.getString("quantity"));
                data.add(row);
            }

            itemsTable = new JTable(data, columnNames);
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace(System.out);
        }
    }
}
