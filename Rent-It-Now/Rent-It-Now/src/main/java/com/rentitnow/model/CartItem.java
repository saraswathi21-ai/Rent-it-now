package com.rentitnow.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cart_items")  // optional but good practice to specify table name
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long productId;

    private int rentalDays;

    // Constructors
    public CartItem() {
    }

    public CartItem(Long userId, Long productId, int rentalDays) {
        this.userId = userId;
        this.productId = productId;
        this.rentalDays = rentalDays;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(int rentalDays) {
        this.rentalDays = rentalDays;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", userId=" + userId +
                ", productId=" + productId +
                ", rentalDays=" + rentalDays +
                '}';
    }
}
