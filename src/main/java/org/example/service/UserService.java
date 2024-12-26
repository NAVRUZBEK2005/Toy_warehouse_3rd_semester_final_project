package org.example.service;

import org.example.manager.CategoryManager;
import org.example.manager.ProductManager;
import org.example.dto.UserDTO;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Logger;

public class UserService {

    private static final Logger logger = Logger.getLogger(UserService.class.getName());
    private static final Scanner scanner = new Scanner(System.in);
    private final UserAuthenticationService authService = new UserAuthenticationService();
    private final UserRegistrationService registrationService = new UserRegistrationService();
    private final ProductManager productManager = new ProductManager();
    private final CategoryManager categoryManager = new CategoryManager();

    public boolean authenticateUser() {
        System.out.println("=== User Login ===");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try {
            if (authService.authenticateUser(username, password)) {
                logger.info("Login successful for user: " + username);
                showMainMenu();
                return true;
            }
        } catch (SQLException e) {
            logger.severe("Error during authentication: " + e.getMessage());
        }

        System.out.println("Invalid username or password.");
        return false;
    }

    public void signUpUser() {
        System.out.println("=== User Sign Up ===");
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        UserDTO newUser = new UserDTO(null, firstName, lastName, username, phoneNumber, password, "USER");

        try {
            boolean isSaved = registrationService.saveUser(newUser);
            if (isSaved) {
                System.out.println("User registered successfully!");
                logger.info("New user registered: " + username);

                showMainMenu();
            } else {
                System.out.println("Registration failed. Please try again.");
            }
        } catch (SQLException e) {
            logger.severe("Error during sign-up: " + e.getMessage());
        }
    }

    private void showMainMenu() {
        while (true) {
            System.out.println("\n=== Main Menu ===");
            System.out.println("1. Product Management");
            System.out.println("2. Category Management");
            System.out.println("3. Exit");
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
                    System.out.println("Exiting... Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void showProductMenu() {
        while (true) {
            System.out.println("\n=== Product Menu ===");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Update Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Back to Main Menu");
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
                    productManager.updateProduct();
                    break;
                case 4:
                    productManager.removeProduct();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void showCategoryMenu() {
        while (true) {
            System.out.println("\n=== Category Menu ===");
            System.out.println("1. Add Category");
            System.out.println("2. View Categories");
            System.out.println("3. Update Category");
            System.out.println("4. Delete Category");
            System.out.println("5. Back to Main Menu");
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
                    categoryManager.updateCategory();
                    break;
                case 4:
                    categoryManager.removeCategory();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
