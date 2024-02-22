package admin;

import utils.connexionBDD;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class represents a window for creating a new item. The user can enter the name,
 * price, quantity and inventory ID of the item to be created, and click on the "CREATE ITEM" button,
 * the item is added to the database.
 *
 * @author Tommy Brisset
 * @version 1.0
 * @see JFrame
 */
public class createItem extends JFrame {

    /** Text field for entering item name. */
    private final JTextField itemNameField;

    /** Text field for entering item price. */
    private final JTextField itemPriceField;

    /** Text field for entering item quantity. */
    private final JTextField itemQuantityField;

    /**
     * Constructor of the `createItem` class.
     *
     * <p>Initializes the window with the title "CREATE AN ITEM", configures its parameters and adds the components
     * text fields for the item's name, price, quantity and inventory ID,
     * as well as the "CREATE ITEM" and "CANCEL" buttons.</p>
     *
     * @param ID The inventory ID to which the item will be associated.
     */
    public createItem(int ID) {
        setTitle("CREATE AN ITEM");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel titleLabel = new JLabel("CREATE YOUR ITEM");
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

        JLabel idLabel = new JLabel("ID de l'inventaire:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 10, 10, 10);
        panel.add(idLabel, gbc);

        JTextField idField = new JTextField(String.valueOf(ID));
        idField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 10, 10, 10);
        panel.add(idField, gbc);

        JButton createItemButton = new JButton("CREATE ITEM");
        createItemButton.addActionListener(e -> {

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
                if (price < 0 && quantity < 0)  {
                    JOptionPane.showMessageDialog(null, "Please enter a valid positive price and quantity.");
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid numeric price or quantity.");
                return;
            }
            try {
                Connection connection1 = connexionBDD.obtenirConnexion();
                PreparedStatement preparedStatement = (connection1).prepareStatement("SELECT * FROM item WHERE name = ? AND inventory_id = ?");
                preparedStatement.setString(1, itemName);
                preparedStatement.setString(2, inventoryId);
                if (preparedStatement.executeQuery().next()) {
                    JOptionPane.showMessageDialog(null, "This item already exists in this inventory.");
                    connection1.close();
                    return;
                }
                connection1.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace(System.out);
            }

            try {
                Connection connection = connexionBDD.obtenirConnexion();
                PreparedStatement preparedStatement = (connection).prepareStatement("INSERT INTO item (name, price, quantity, inventory_id) VALUES (?, ?, ?, ?)");
                preparedStatement.setString(1, itemName);
                preparedStatement.setString(2, itemPrice);
                preparedStatement.setString(3, itemQuantity);
                preparedStatement.setString(4, inventoryId);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Item created successfully.");
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace(System.out);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        panel.add(createItemButton, gbc);

        JButton cancelButton = new JButton("CANCEL");
        cancelButton.addActionListener(e -> dispose());

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
