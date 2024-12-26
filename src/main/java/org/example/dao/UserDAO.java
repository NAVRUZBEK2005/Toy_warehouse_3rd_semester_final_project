package org.example.dao;

import org.example.DBService.DatabaseUtil;
import org.example.dto.UserDTO;
import org.example.entity.User;
import org.example.RoleEnum;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private Connection getConnection() throws SQLException {
        return DatabaseUtil.getConnection();
    }

    // Get user by username, returning a UserDTO
    public UserDTO getUserByUsername(String username) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new UserDTO(
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        RoleEnum.valueOf(resultSet.getString("role")),
                        resultSet.getString("password"),
                        resultSet.getString("phone_number")
                );
            }
        }
        return null;
    }

    // Add a new user to the database
    public void addUser(User user) throws SQLException {
        String query = "INSERT INTO users (first_name, last_name, username, role, password, phone_number) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getUsername());
            statement.setString(4, user.getRole().name()); // Enum role
            statement.setString(5, user.getPassword());
            statement.setString(6, user.getPhoneNumber());
            statement.executeUpdate();
        }
    }

    // Update an existing user's information
    public void updateUser(int id, User user) throws SQLException {
        String query = "UPDATE users SET first_name = ?, last_name = ?, username = ?, role = ?, password = ?, phone_number = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getUsername());
            statement.setString(4, user.getRole().name());
            statement.setString(5, user.getPassword());
            statement.setString(6, user.getPhoneNumber());
            statement.setInt(7, id);
            statement.executeUpdate();
        }
    }

    // Remove a user from the database by ID
    public void removeUser(int id) throws SQLException {
        String query = "DELETE FROM users WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    // Get all users, returning a list of UserDTO objects
    public List<UserDTO> getAllUsers() throws SQLException {
        List<UserDTO> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                UserDTO userDTO = new UserDTO(
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        RoleEnum.valueOf(resultSet.getString("role")),
                        resultSet.getString("password"),
                        resultSet.getString("phone_number")
                );
                users.add(userDTO);
            }
        }
        return users;
    }

    // Get a user by ID, returning a UserDTO object
    public UserDTO getUserById(int userId) throws SQLException {
        String query = "SELECT * FROM users WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new UserDTO(
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        RoleEnum.valueOf(resultSet.getString("role")),
                        resultSet.getString("password"),
                        resultSet.getString("phone_number")
                );
            }
        }
        return null;
    }
}
