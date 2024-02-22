package employe;

import utils.connexionBDD;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import principal.LoginFrame;
/**
 * This class represents the window allowing a user to delete his account.
 *
 * <p>The user can choose to delete his account by clicking on the "Delete my account" button.
 * A confirmation dialog box will be displayed before proceeding with the deletion.</p>
 *
 * @author Tommy Brisset
 * @version 1.0
 * @see JFrame
 */
public class deleteUser extends JFrame {


    /**
     * Constructor of the `deleteUser` class.
     *
     * @param id The identifier of the user to be deleted.
     */
    public deleteUser(int id) {
        super();
        setTitle("ISTORE DELETE USER");
        setSize(350, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();


        JButton deleteButton = new JButton("Delete my account");
        deleteButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to delete your account?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                deleteUserAccountwhite(id);
                dispose();
                new LoginFrame();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(deleteButton, gbc);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(cancelButton, gbc);

        add(panel);

        setResizable(false);

        setVisible(true);
    }

    /**
     * This method deletes the user account from the database.
     *
     * <p>
     * It first retrieves the user's email address from the database using the user's identifier.
     * Then, it deletes the user from the whitelist table.
     * Finally, it deletes the user from the user table.
     * </p>
     *
     * @param ID The identifier of the user to be deleted.
     */
    private void deleteUserAccountwhite(int ID) {
        try (Connection connexion = connexionBDD.obtenirConnexion()) {
            try (PreparedStatement statement = connexion.prepareStatement("SELECT email FROM user WHERE id = ?")) {
                statement.setInt(1, ID);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String login = resultSet.getString("email");

                        try (PreparedStatement statement2 = connexion.prepareStatement("DELETE FROM whitelist WHERE email = ?")) {
                            statement2.setString(1, login);
                            statement2.executeUpdate();
                        } catch (SQLException exception) {
                            exception.printStackTrace(System.out);
                            JOptionPane.showMessageDialog(null, "Error while deleting user from whitelist.");
                        }
                    }
                } catch (SQLException exception) {
                    exception.printStackTrace(System.out);
                    JOptionPane.showMessageDialog(null, "Error while retrieving user login.");
                }

                try (PreparedStatement preparedStatement = connexion.prepareStatement("DELETE FROM user WHERE id = ?")) {
                    preparedStatement.setInt(1, ID);
                    preparedStatement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "User deleted.");
                    dispose();
                } catch (SQLException exception) {
                    exception.printStackTrace(System.out);
                    JOptionPane.showMessageDialog(null, "Error while deleting user.");
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
