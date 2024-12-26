package org.example;

import org.example.DBService.DatabaseUtil;
import org.example.dto.UserDTO;
import org.example.service.UserService;
import org.example.service.AdminService;
import org.example.dao.UserDAO;
import org.example.manager.AdminUserManager;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static UserService userService = new UserService();
    private static AdminService adminService = new AdminService();

    public static void main(String[] args) {
        try {
            AdminUserManager.createAdminIfNotExists(DatabaseUtil.getConnection());
        } catch (SQLException e) {
            System.out.println("Error creating admin user: " + e.getMessage());
        }

        while (true) {
            System.out.println("\n=== Welcome to the System ===");
            System.out.println("1. Login");
            System.out.println("2. Sign Up");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();

                    if (userService.authenticateUser()) {
                        if (isAdmin(username)) {
                            adminService.showAdminMenu();
                        } else {
                            System.out.println("User authenticated successfully. You have user privileges.");
                        }
                    }
                    break;
                case 2:
                    userService.signUpUser();
                    break;
                case 3:
                    System.out.println("Exiting... Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static boolean isAdmin(String username) {
        UserDAO userDAO = new UserDAO();
        try {
            UserDTO user = userDAO.getUserByUsername(username);
            if (user != null && user.getRole() != null) {
                return user.getRole() == RoleEnum.ADMIN;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
