package employe;

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
 * This class represents a window allowing an employee to view information related to one or more stores.
 *
 * <p>The employee can select a store from a drop-down list, then choose to display the store contents or the list
 * of users with access to the store.</p>
 *
 * @author Tommy Brisset
 * @version 1.0
 * @see JFrame
 */
public class listEmployeStore extends JFrame {

    /** JComboBox to select a store. */
    private final JComboBox<String> storeComboBox;

    /** The identifier of the selected store. */
    public int storeId;

    /** The inventory ID of the selected store. */
    public int  inventoryId;

    /** Employee ID. */
    private final int employee_id;

    /** The action to be performed (1 to display store contents, 2 to display users with access). */
    private final int action_to_do;

    /**
     * Constructor of the `listEmployeStore` class.
     *
     * <p>It initializes the window with the title "STORE LIST" and configures its parameters according to the employee
     * and the action to be performed.</p>
     *
     * @param ID_emp The employee's identifier.
     * @param action The action to be performed (1 to display store contents, 2 to display users with access).
     */
    public listEmployeStore(int ID_emp,int action) {
        setTitle("STORE LIST");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        employee_id = ID_emp;
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
     * This method retrieves the names of the stores to which the employee has access.
     *
     * @return An array of strings representing the store names.
     */
    private String[] getStoreNames() {
        List<String> storeNames = new ArrayList<>();
        try {
            Connection connection3 = connexionBDD.obtenirConnexion();
            PreparedStatement preparedStatement = connection3.prepareStatement("SELECT s.name " +
                    "FROM Store s " +
                    "JOIN EmployeeStoreAccess esa ON s.id = esa.store_id " +
                    "JOIN User u ON u.id = esa.employee_id " +
                    "WHERE u.id = ?");
            preparedStatement.setInt(1,employee_id );
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
