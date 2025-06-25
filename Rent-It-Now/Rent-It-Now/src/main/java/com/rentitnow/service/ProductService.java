package com.rentitnow.service;

import com.rentitnow.model.Product;
import com.rentitnow.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    // ✅ Add Product
    public Product addProduct(Product p) {
        System.out.println("🆕 Adding product: " + p.getName());
        return repo.save(p);
    }

    // ✅ Get All Products
    public List<Product> getAll() {
        return repo.findAll();
    }

    // ✅ Update Product
    public Product updateProduct(Long id, Product updatedProduct) {
        Optional<Product> optional = repo.findById(id);
        if (optional.isPresent()) {
            Product existing = optional.get();
            existing.setName(updatedProduct.getName());
            existing.setDescription(updatedProduct.getDescription());
            existing.setPricePerDay(updatedProduct.getPricePerDay());
            existing.setAvailableQuantity(updatedProduct.getAvailableQuantity());
            existing.setCategory(updatedProduct.getCategory());

            System.out.println("✏️ Updating product ID " + id);
            return repo.save(existing);
        } else {
            System.out.println("⚠️ Product not found with ID: " + id);
            return null;
        }
    }

    // ✅ Delete Product
    public void deleteProduct(Long id) {
        if (repo.existsById(id)) {
            System.out.println("🗑️ Deleting product ID: " + id);
            repo.deleteById(id);
        } else {
            System.out.println("⚠️ Cannot delete, product not found with ID: " + id);
        }
    }

    // ✅ Get Products by Category
    public List<Product> byCategory(String cat) {
        return repo.findByCategory(cat);
    }

    // ✅ Search Products by Name
    public List<Product> search(String name) {
        return repo.findByNameContainingIgnoreCase(name);
    }

    // ✅ Get Product by ID
    public Product getById(Long id) {
        return repo.findById(id).orElse(null);
    }
}
