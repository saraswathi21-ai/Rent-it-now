package com.rentitnow.controller;

import com.rentitnow.model.Product;
import com.rentitnow.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/test-db")
    public String testDb() {
        return "Database connected!";
    }

    // ✅ View All Products
    @GetMapping
    public List<Product> getAll() {
        System.out.println("📦 Fetching all products");
        return service.getAll();
    }

    // ✅ View Products by Category
    @GetMapping("/category/{cat}")
    public List<Product> getByCategory(@PathVariable String cat) {
        System.out.println("📦 Searching products in category: " + cat);
        return service.byCategory(cat);
    }

    // ✅ Search by Product Name
    @GetMapping("/search")
    public List<Product> search(@RequestParam String name) {
        System.out.println("🔍 Searching products with name: " + name);
        return service.search(name);
    }

    // ✅ Add Product (Admin only)
    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestBody Product product) {
        service.addProduct(product);
        System.out.println("✅ Product added: " + product.getName());
        return ResponseEntity.ok("Product added successfully!");
    }

    // ✅ Update Product (Admin only)
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        Product updated = service.updateProduct(id, updatedProduct);
        if (updated == null) {
            return ResponseEntity.badRequest().body("Product not found");
        }
        return ResponseEntity.ok("Product updated successfully!");
    }


    // ✅ Delete Product (Admin only)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        if (service.getById(id) == null) {
            return ResponseEntity.badRequest().body("Product not found");
        }
        service.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully!");
    }

}
