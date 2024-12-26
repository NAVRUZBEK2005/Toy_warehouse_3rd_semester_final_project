package org.example.DBService;

import org.example.manager.AdminUserManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {

    private static final String URL = "jdbc:postgresql://localhost:5432/toy_warehouse";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Big2005boy";

    private static DatabaseUtil instance;

    private DatabaseUtil() {
        ensureAdminUser();
    }

    public static DatabaseUtil getInstance() {
        if (instance == null) {
            instance = new DatabaseUtil();
        }
        return instance;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private void ensureAdminUser() {
        try (Connection connection = getConnection()) {
            AdminUserManager.createAdminIfNotExists(connection);
        } catch (SQLException e) {
            throw new RuntimeException("Error ensuring admin user: " + e.getMessage(), e);
        }
    }
}
