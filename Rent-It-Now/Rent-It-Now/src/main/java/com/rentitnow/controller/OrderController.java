package com.rentitnow.controller;

import com.rentitnow.model.Order;
import com.rentitnow.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // ✅ Place order
    @PostMapping("/place/{userId}")
    public ResponseEntity<String> placeOrder(@PathVariable Long userId) {
        Order order = orderService.placeOrder(userId);
        return order != null ?
                ResponseEntity.ok("✅ Order placed successfully!") :
                ResponseEntity.badRequest().body("❌ Failed to place order");
    }

    // ✅ View user's orders
    @GetMapping("/user/{userId}")
    public List<Order> getUserOrders(@PathVariable Long userId) {
        return orderService.getUserOrders(userId);
    }

    // ✅ View all orders (admin)
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // ✅ Return order
    @PutMapping("/return/{orderId}")
    public ResponseEntity<String> returnOrder(@PathVariable Long orderId) {
        Order returned = orderService.returnOrder(orderId);
        return returned != null ?
                ResponseEntity.ok("✅ Item returned and quantity updated!") :
                ResponseEntity.badRequest().body("❌ Return failed or already returned");
    }
}
