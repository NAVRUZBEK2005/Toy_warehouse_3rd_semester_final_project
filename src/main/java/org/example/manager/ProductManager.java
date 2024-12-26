package org.example.manager;

import org.example.dao.ProductDAO;
import org.example.dto.ProductDTO;
import org.example.entity.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ProductManager {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ProductDAO productDAO = new ProductDAO();

    public void productMenu() {
        while (true) {
            System.out.println("\n=== Product Management ===");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Update Product");
            System.out.println("4. Remove Product");
            System.out.println("5. Back to Main Menu");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    viewProducts();
                    break;
                case 3:
                    updateProduct();
                    break;
                case 4:
                    removeProduct();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Product Methods
    public void addProduct() {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter product description: ");
        String description = scanner.nextLine();
        System.out.print("Enter product price: ");
        long price = scanner.nextLong();
        scanner.nextLine();

        System.out.print("Enter category ID for the product: ");
        int categoryId = scanner.nextInt();
        scanner.nextLine();

        if (price <= 0) {
            System.out.println("Price must be positive.");
            return;
        }

        ProductDTO productDTO = new ProductDTO(null, name, description, price, categoryId);

        try {
            productDAO.addProduct(productDTO);
            System.out.println("Product added successfully!");
        } catch (SQLException e) {
            System.err.println("Error adding product: " + e.getMessage());
        }
    }

    public void viewProducts() {
        try {
            List<Product> products = productDAO.getAllProducts();
            if (products.isEmpty()) {
                System.out.println("No products found.");
            } else {
                products.forEach(product -> System.out.printf(
                        "ID: %d, Name: %s, Description: %s, Price: %d, Category ID: %d%n",
                        product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getCategory().getId()));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching products: " + e.getMessage());
        }
    }

    public void updateProduct() {
        System.out.print("Enter product ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter new product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new product description: ");
        String description = scanner.nextLine();
        System.out.print("Enter new product price: ");
        long price = scanner.nextLong();
        scanner.nextLine();

        System.out.print("Enter new category ID for the product: ");
        int categoryId = scanner.nextInt();
        scanner.nextLine();

        ProductDTO productDTO = new ProductDTO(id, name, description, price, categoryId);

        try {
            productDAO.updateProduct(id, productDTO);
            System.out.println("Product updated successfully!");
        } catch (SQLException e) {
            System.err.println("Error updating product: " + e.getMessage());
        }
    }

    public void removeProduct() {
        System.out.print("Enter product ID to remove: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            productDAO.removeProduct(id);
            System.out.println("Product removed successfully!");
        } catch (SQLException e) {
            System.err.println("Error removing product: " + e.getMessage());
        }
    }
}
