package principal;

import utils.connexionBDD;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 * This class represents a window displaying the list of users with access to a specific
 * specific inventory.
 *
 * <p>The window displays a table with the "Email" and "Nickname" columns for each user who has
 * access to the store identified by the specified ID.</p>
 *
 * @author Tommy Brisset
 * @version 1.0
 * @see JFrame
 */
public class listPersonAcess extends JFrame {

    /** The store ID for which the list of users with access is displayed. */
    private final int storeId;
    /** User table with access. */
    private JTable usersTable;

    /**
     * Constructor of the `ListPersonAccess` class. Initializes window with title, size,
     * and positions it at the center of the screen. The list of users with access is retrieved from
     * database for the specified store.
     *
     * @param storeId The store's unique identifier.
     */
    public listPersonAcess(int storeId) {
        setTitle("USERS WITH ACCESS TO INVENTORY");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        this.storeId = storeId;

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel titleLabel = new JLabel("USERS WITH ACCESS TO INVENTORY");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 20, 10);
        panel.add(titleLabel, gbc);

        createUsersTable();
        JScrollPane scrollPane = new JScrollPane(usersTable);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 20, 10);
        panel.add(scrollPane, gbc);

        JButton cancelButton = new JButton("CANCEL");
        cancelButton.addActionListener(e -> dispose());

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(cancelButton, gbc);

        add(panel);
        pack();
        setVisible(true);
    }

    /**
     * This method creates a table of users with access by retrieving data from the
     * database for the specified store. Table columns include "Email" and "Nickname".
     */
    void createUsersTable() {
        try {
            Connection connection = connexionBDD.obtenirConnexion();
            String sql = "SELECT u.email, u.pseudo " +
                    "FROM user u " +
                    "JOIN EmployeeStoreAccess esa ON u.id = esa.employee_id " +
                    "JOIN store st ON esa.store_id = st.id " +
                    "WHERE st.id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, storeId);
            ResultSet resultSet = preparedStatement.executeQuery();

            Vector<Vector<Object>> data = new Vector<>();
            Vector<String> columnNames = new Vector<>();
            columnNames.add("Email");
            columnNames.add("Pseudo");

            while (resultSet.next()) {
                Vector<Object> row = new Vector<>();
                row.add(resultSet.getString("email"));
                row.add(resultSet.getString("pseudo"));
                data.add(row);
            }

            usersTable = new JTable(data, columnNames);
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace(System.out);
        }
    }

    /**
     * This method returns the table of users with access.
     *
     * @return The table of users with access.
     */
    public JTable getUsersTable() {
        return usersTable;
    }
}
