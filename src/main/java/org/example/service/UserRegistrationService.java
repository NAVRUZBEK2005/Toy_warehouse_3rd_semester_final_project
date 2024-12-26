package org.example.service;

import org.example.DBService.DatabaseUtil;
import org.example.dto.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserRegistrationService {

    public static boolean saveUser(UserDTO user) throws SQLException {
        String query = "INSERT INTO users (first_name, last_name, username, phone_number, password, role) VALUES (?, ?, ?, ?, ?, 'USER')";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getUsername());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getPhoneNumber());


            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        }
    }
}
