package org.example.service;

import org.example.manager.CategoryManager;
import org.example.manager.ProductManager;
import org.example.dao.UserDAO;
import org.example.entity.User;
import org.example.dto.UserDTO;
import org.example.RoleEnum;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AdminService {

    private final ProductManager productManager = new ProductManager();
    private final CategoryManager categoryManager = new CategoryManager();
    private final UserDAO userDAO = new UserDAO();
    private final Scanner scanner = new Scanner(System.in);

    public void showAdminMenu() {
        while (true) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. Manage Products");
            System.out.println("2. Manage Categories");
            System.out.println("3. Manage Users");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    showProductMenu();
                    break;
                case 2:
                    showCategoryMenu();
                    break;
                case 3:
                    manageUsers();
                    break;
                case 4:
                    System.out.println("Exiting... Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void showProductMenu() {
        while (true) {
            System.out.println("\n=== Product Management ===");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Back to Admin Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    productManager.addProduct();
                    break;
                case 2:
                    productManager.viewProducts();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void showCategoryMenu() {
        while (true) {
            System.out.println("\n=== Category Management ===");
            System.out.println("1. Add Category");
            System.out.println("2. View Categories");
            System.out.println("3. Back to Admin Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    categoryManager.addCategory();
                    break;
                case 2:
                    categoryManager.viewCategories();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void manageUsers() {
        while (true) {
            System.out.println("\n=== Manage Users ===");
            System.out.println("1. View Users");
            System.out.println("2. Add User");
            System.out.println("3. Update User");
            System.out.println("4. Remove User");
            System.out.println("5. Back to Admin Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewUsers();
                    break;
                case 2:
                    addUser();
                    break;
                case 3:
                    updateUser();
                    break;
                case 4:
                    removeUser();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void viewUsers() {
        try {
            List<UserDTO> users = userDAO.getAllUsers();
            users.forEach(user -> System.out.println(user.getId() + " | " + user.getUsername() + " | " + user.getRole()));
        } catch (SQLException e) {
            System.out.println("Error viewing users: " + e.getMessage());
        }
    }

    private void addUser() {
        System.out.println("Enter user details to add:");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Role (USER/ADMIN): ");
        String role = scanner.nextLine().toUpperCase();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Phone number: ");
        String phoneNumber = scanner.nextLine();

        try {
            User user = new User(0, "First", "Last", username, RoleEnum.valueOf(role), password, phoneNumber);
            userDAO.addUser(user);
            System.out.println("User added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding user: " + e.getMessage());
        }
    }

    private void updateUser() {
        System.out.print("Enter user ID to update: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter new username: ");
        String username = scanner.nextLine();
        System.out.print("Enter new password: ");
        String password = scanner.nextLine();
        System.out.print("Enter new phone number: ");
        String phoneNumber = scanner.nextLine();

        try {
            UserDTO userDTO = userDAO.getUserById(userId);
            if (userDTO != null) {
                User user = new User(userDTO.getId(), userDTO.getFirstName(), userDTO.getLastName(), username, userDTO.getRole(), password, phoneNumber);
                userDAO.updateUser(userId, user);
                System.out.println("User updated successfully!");
            } else {
                System.out.println("User not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating user: " + e.getMessage());
        }
    }

    private void removeUser() {
        System.out.print("Enter user ID to remove: ");
        int userId = scanner.nextInt();
        try {
            userDAO.removeUser(userId);
            System.out.println("User removed successfully!");
        } catch (SQLException e) {
            System.out.println("Error removing user: " + e.getMessage());
        }
    }
}
