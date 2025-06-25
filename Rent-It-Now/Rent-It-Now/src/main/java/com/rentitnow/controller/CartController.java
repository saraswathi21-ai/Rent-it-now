package com.rentitnow.controller;

import com.rentitnow.model.CartItem;
import com.rentitnow.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // ✅ Add item to cart
    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody CartItem cartItem) {
        cartService.add(cartItem);
        return ResponseEntity.ok("Item added to cart!");
    }

    // ✅ View all cart items for a user
    @GetMapping("/user/{userId}")
    public List<CartItem> viewCart(@PathVariable Long userId) {
        return cartService.getUserCart(userId);
    }

    // ✅ Remove item from cart by item ID
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<String> removeFromCart(@PathVariable Long id) {
        cartService.remove(id);
        return ResponseEntity.ok("Item removed from cart!");
    }
}
