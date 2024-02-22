package employe;

import utils.connexionBDD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class represents a window displaying a list of all registered users.
 *
 * <p>It displays a table containing the "EMAIL" and "PSEUDO" columns for each user.</p>
 *
 * <p>A "REFRESH DATA" button is used to update the data displayed in the table by retrieving.
 * the most recent information from the database.</p>
 *
 * <p>The window can be closed by clicking on the "CANCEL" button.</p> *
 *
 * @author Tommy Brisset
 * @version 1.0
 * @see JFrame
 */
public class listAllUsers extends JFrame {

    /** The table model used to store table data. */
    private final DefaultTableModel model;

    /**
     * Constructor of the `listAllUsers` class.
     *
     * <p>It initializes the window with the title "LIST OF ALL USERS" and configures its parameters.
     * The user table is displayed with the "EMAIL" and "PSEUDO" columns.</p>
     */
    public listAllUsers() {
        super("LIST OF ALL USERS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 300);
        setLocationRelativeTo(null);

        model = new DefaultTableModel();
        JTable table = new JTable(model);

        model.addColumn("EMAIL");
        model.addColumn("PSEUDO");

        JButton refreshButton = new JButton("REFRESH DATA");
        refreshButton.addActionListener(e -> refreshData());

        JButton cancelButton = new JButton("CANCEL");
        cancelButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());

        buttonPanel.add(refreshButton, BorderLayout.EAST);
        buttonPanel.add(cancelButton, BorderLayout.WEST);

        getContentPane().add(buttonPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        refreshData();

        setUndecorated(true);

        setVisible(true);
    }

    /**
     * Private method for refreshing table data by retrieving the most recent information
     * from the database.
     */
    private void refreshData() {
        model.setRowCount(0);

        try (Connection connection = connexionBDD.obtenirConnexion()) {
            String sql = "SELECT email, pseudo FROM user";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    model.addRow(new Object[]{resultSet.getString("email"), resultSet.getString("pseudo")});
                }
                connection.close();
            }
        } catch (SQLException exception) {
            exception.printStackTrace(System.out);
            JOptionPane.showMessageDialog(null, "Error retrieving user data from the database.");
        }
    }
}
