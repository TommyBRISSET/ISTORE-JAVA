package utils;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The updateItem class represents a Swing window for updating an item's information.
 *
 * <p>
 * This window is used in the context of updating an item's details, such as its name, price, and quantity.
 * It pre-fills the form fields with the existing item information retrieved from the database.
 * </p>
 *
 * <p>
 * The class utilizes Swing for the graphical user interface.
 * </p>
 *
 * <p>
 * To use this class, create an instance by providing the item ID and inventory ID.
 * </p>
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * new updateItem(1, 2); // Update item with ID 1 in the inventory with ID 2
 * </pre>
 *
 * @author Tommy Brisset
 * @version 1.0
 */
public class updateItem extends JFrame {

    /**
     * The JTextField for the item name.
     */
    private final JTextField itemNameField;

    /**
     * The JTextField for the item price.
     */
    private final JTextField itemPriceField;

    /**
     * The JTextField for the item quantity.
     */
    private final JTextField itemQuantityField;

    /**
     * Constructs an instance of the updateItem class.
     *
     * @param ID_item The ID of the item to be updated.
     * @param ID_inv The ID of the inventory to which the item belongs.
     */
    public updateItem(int ID_item, int ID_inv) {
        setTitle("UPDATE AN ITEM");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel titleLabel = new JLabel("UPDATE YOUR ITEM");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 20, 10);
        panel.add(titleLabel, gbc);

        JLabel nameLabel = new JLabel("Name:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 10, 10, 10);
        panel.add(nameLabel, gbc);

        itemNameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 10, 10);
        panel.add(itemNameField, gbc);

        JLabel priceLabel = new JLabel("Price:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 10, 10, 10);
        panel.add(priceLabel, gbc);

        itemPriceField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 10, 10, 10);
        panel.add(itemPriceField, gbc);

        JLabel quantityLabel = new JLabel("Quantity:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 10, 10, 10);
        panel.add(quantityLabel, gbc);

        itemQuantityField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 10, 10, 10);
        panel.add(itemQuantityField, gbc);

        JLabel idLabel = new JLabel("Inventory ID:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 10, 10, 10);
        panel.add(idLabel, gbc);

        JTextField idField = new JTextField(String.valueOf(ID_inv));
        idField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 10, 10, 10);
        panel.add(idField, gbc);

        try {
            Connection connection = connexionBDD.obtenirConnexion();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM item WHERE id = ?");
            preparedStatement.setInt(1, ID_item);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String itemName = resultSet.getString("name");
                String itemPrice = resultSet.getString("price");
                String itemQuantity = resultSet.getString("quantity");

                itemNameField.setText(itemName);
                itemPriceField.setText(itemPrice);
                itemQuantityField.setText(itemQuantity);
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace(System.out);
        }

        JButton updateItemButton = new JButton("UPDATE ITEM");
        updateItemButton.addActionListener(e -> {
            String itemName = itemNameField.getText();
            String itemPrice = itemPriceField.getText();
            String itemQuantity = itemQuantityField.getText();
            String inventoryId = idField.getText();

            if (itemName.isEmpty() || itemPrice.isEmpty() || itemQuantity.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all the fields.");
                return;
            }

            try {
                double price = Double.parseDouble(itemPrice);
                double quantity = Double.parseDouble(itemQuantity);
                if (price < 0 || quantity < 0)  {
                    JOptionPane.showMessageDialog(null, "Please enter a valid positive price and quantity.");
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter valid numeric price or quantity.");
                return;
            }

            try {
                Connection connection1 = connexionBDD.obtenirConnexion();
                PreparedStatement preparedStatement = connection1.prepareStatement("UPDATE item SET name = ?, price = ?, quantity = ? WHERE id = ? AND inventory_id = ?");
                preparedStatement.setString(1, itemName);
                preparedStatement.setString(2, itemPrice);
                preparedStatement.setString(3, itemQuantity);
                preparedStatement.setString(4, String.valueOf(ID_item));
                preparedStatement.setString(5, inventoryId);

                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Item updated successfully.");
                connection1.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace(System.out);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        panel.add(updateItemButton, gbc);

        JButton cancelButton = new JButton("CANCEL");
        cancelButton.addActionListener(e -> {
            dispose();
        });

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(cancelButton, gbc);

        add(panel);

        setUndecorated(true);

        setVisible(true);
    }
}
