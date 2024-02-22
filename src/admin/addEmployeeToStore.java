package admin;

import utils.connexionBDD;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class represents a window for adding an employee to a specified store. The user can view
 * store and user details, then click on the "ADD EMPLOYEE TO STORE" button to add
 * the employee to the store in the database.
 *
 * @author Tommy Brisset
 * @version 1.0
 * @see JFrame
 */
public class addEmployeeToStore extends JFrame {

    /** Text field to display store name. */
    private final JTextField storeNameField;

    /** Text field to display user name. */
    private final JTextField userNameField;

    /** Text field for store ID. */
    private final int storeId;

    /** Text field for user ID. */
    private final int userId;

    /**
     * Constructor of the `addEmployeeToStore` class.
     *
     * <p>Initializes the window with the title "ADD EMPLOYEE TO STORE", configures its parameters and adds the components
     * text fields for store name, user name, store ID and user ID.
     * user ID, as well as the "ADD EMPLOYEE TO STORE" and "CANCEL" buttons.</p>
     *
     * @param storeId The store ID to which the employee will be added.
     * @param userId The ID of the user who will be added to the store.
     */
    public addEmployeeToStore(int storeId, int userId) {
        super();

        this.storeId = storeId;
        this.userId = userId;

        setTitle("ADD EMPLOYEE TO STORE");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel storeID = new JLabel("STORE ID :");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 0, 10);
        panel.add(storeID, gbc);

        JTextField storeIdField = new JTextField();
        storeIdField.setEditable(false);
        storeIdField.setText(String.valueOf(storeId));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 0, 10);
        panel.add(storeIdField, gbc);

        JLabel nameOfStore = new JLabel("STORE NAME :");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 0, 10);
        panel.add(nameOfStore, gbc);

        storeNameField = new JTextField();
        storeNameField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 0, 10);
        panel.add(storeNameField, gbc);

        JLabel userIdLabel = new JLabel("USER ID :");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 10, 0, 10);
        panel.add(userIdLabel, gbc);

        JTextField userIdField = new JTextField();
        userIdField.setEditable(false);
        userIdField.setText(String.valueOf(userId));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 10, 0, 10);
        panel.add(userIdField, gbc);

        JLabel userName = new JLabel("USER NAME :");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(10, 10, 0, 10);
        panel.add(userName, gbc);

        userNameField = new JTextField();
        userNameField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(10, 10, 0, 10);
        panel.add(userNameField, gbc);

        JButton addButton = new JButton("ADD EMPLOYEE TO STORE");
        addButton.addActionListener(e -> addUserToStore());

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(addButton, gbc);

        JButton cancelButton = new JButton("CANCEL");
        cancelButton.addActionListener(e -> {
            dispose();
        });

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(cancelButton, gbc);

        add(panel);

        fetchStoreAndUserDetails();

        setUndecorated(true);

        setVisible(true);
    }

    /**
     * Retrieves store and user details from the database and displays them in the corresponding text fields.
     */
    private void fetchStoreAndUserDetails() {
        try {
            Connection connection = connexionBDD.obtenirConnexion();
            PreparedStatement statement = connection.prepareStatement("SELECT name FROM store WHERE id = ?");
            statement.setInt(1, storeId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                storeNameField.setText(resultSet.getString("name"));
            }
            connection.close();

            Connection connection2 = connexionBDD.obtenirConnexion();
            statement = connection2.prepareStatement("SELECT email FROM user WHERE id = ?");
            statement.setInt(1, userId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                userNameField.setText(resultSet.getString("email"));
            }
            connection2.close();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    /**
     * Adds the user to the store in the database, checking first that he doesn't already have access to the store.
     */
    private void addUserToStore() {
        try {
            Connection connection3 = connexionBDD.obtenirConnexion();
            PreparedStatement preparedStatement = connection3.prepareStatement("SELECT * FROM employeestoreaccess WHERE store_id = ? AND employee_id = ?");
            preparedStatement.setInt(1, storeId);
            preparedStatement.setInt(2, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                JOptionPane.showMessageDialog(null, "User already has access to this store.");
                return;
            }
            connection3.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace(System.out);
        }
        try {
            Connection connection4 = connexionBDD.obtenirConnexion();
            PreparedStatement statement = connection4.prepareStatement("INSERT INTO employeestoreaccess (store_id, employee_id) VALUES (?, ?)");
            statement.setInt(1, storeId);
            statement.setInt(2, userId);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(this, "User added to store successfully!");
            connection4.close();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }
}
