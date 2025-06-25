package com.rentitnow.controller;

import com.rentitnow.model.Product;
import com.rentitnow.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/products")
public class AdminController {

    private final ProductService service;

    public AdminController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public Product add(@RequestBody Product p) {
        return service.addProduct(p);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product p) {
        return service.updateProduct(id, p);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteProduct(id);
    }

    @GetMapping
    public List<Product> viewAll() {
        return service.getAll();
    }
}
