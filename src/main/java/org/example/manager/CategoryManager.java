package org.example.manager;

import org.example.dao.CategoryDAO;
import org.example.dto.CategoryDTO;
import org.example.entity.Category;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CategoryManager {

    private static final Scanner scanner = new Scanner(System.in);
    private static final CategoryDAO categoryDAO = new CategoryDAO();

    public void categoryMenu() {
        while (true) {
            System.out.println("\n=== Category Management ===");
            System.out.println("1. Add Category");
            System.out.println("2. View Categories");
            System.out.println("3. Update Category");
            System.out.println("4. Remove Category");
            System.out.println("5. Back to Main Menu");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addCategory();
                    break;
                case 2:
                    viewCategories();
                    break;
                case 3:
                    updateCategory();
                    break;
                case 4:
                    removeCategory();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Category Methods
    public void addCategory() {
        System.out.print("Enter category name: ");
        String name = scanner.nextLine();
        System.out.print("Enter category description: ");
        String description = scanner.nextLine();

        if (name.trim().isEmpty() || description.trim().isEmpty()) {
            System.out.println("Category name and description cannot be empty.");
            return;
        }

        CategoryDTO categoryDTO = new CategoryDTO(null, name, description);
        try {
            categoryDAO.addCategory(categoryDTO);
            System.out.println("Category added successfully!");
        } catch (SQLException e) {
            System.err.println("Error adding category: " + e.getMessage());
        }
    }

    public void viewCategories() {
        try {
            List<Category> categories = categoryDAO.getAllCategories();
            if (categories.isEmpty()) {
                System.out.println("No categories found.");
            } else {
                categories.forEach(category -> System.out.println(
                        "ID: " + category.getId() +
                                ", Name: " + category.getName() +
                                ", Description: " + category.getDescription()));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching categories: " + e.getMessage());
        }
    }

    public void updateCategory() {
        System.out.print("Enter category ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter new name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new description: ");
        String description = scanner.nextLine();

        CategoryDTO categoryDTO = new CategoryDTO(id, name, description);

        try {
            categoryDAO.updateCategory(id, categoryDTO);
            System.out.println("Category updated successfully!");
        } catch (SQLException e) {
            System.err.println("Error updating category: " + e.getMessage());
        }
    }

    public void removeCategory() {
        System.out.print("Enter category ID to remove: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            categoryDAO.removeCategory(id);
            System.out.println("Category removed successfully!");
        } catch (SQLException e) {
            System.err.println("Error removing category: " + e.getMessage());
        }
    }
}
