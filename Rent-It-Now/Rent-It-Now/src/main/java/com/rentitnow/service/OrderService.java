package com.rentitnow.service;

import com.rentitnow.model.CartItem;
import com.rentitnow.model.Order;
import com.rentitnow.model.Product;
import com.rentitnow.repository.CartRepository;
import com.rentitnow.repository.OrderRepository;
import com.rentitnow.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepo;
    private final CartRepository cartRepo;
    private final ProductRepository productRepo;

    public OrderService(OrderRepository orderRepo, CartRepository cartRepo, ProductRepository productRepo) {
        this.orderRepo = orderRepo;
        this.cartRepo = cartRepo;
        this.productRepo = productRepo;
    }

    public Order placeOrder(Long userId) {
        List<CartItem> cartItems = cartRepo.findByUserId(userId);
        if (cartItems.isEmpty()) return null;

        double total = 0;
        List<Long> productIds = new ArrayList<>();
        int rentalDays = 0;

        for (CartItem c : cartItems) {
            Product p = productRepo.findById(c.getProductId()).orElse(null);
            if (p != null && p.getAvailableQuantity() >= 1) {
                total += p.getPricePerDay() * c.getRentalDays();
                p.setAvailableQuantity(p.getAvailableQuantity() - 1);
                productRepo.save(p);

                productIds.add(p.getId());
                rentalDays = c.getRentalDays(); // Assuming same rentalDays for all items
            }
        }

        Order order = new Order();
        order.setUserId(userId);
        order.setProductIds(productIds);
        order.setRentalDays(rentalDays);
        order.setTotalAmount(total);
        order.setStartDate(LocalDate.now());
        order.setReturnDate(LocalDate.now().plusDays(rentalDays));
        order.setReturned(false);

        Order savedOrder = orderRepo.save(order);
        cartRepo.deleteAll(cartItems);

        return savedOrder;
    }

    public List<Order> getUserOrders(Long userId) {
        return orderRepo.findByUserId(userId);
    }

    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    public Order returnOrder(Long orderId) {
        Order order = orderRepo.findById(orderId).orElse(null);
        if (order != null && !order.isReturned()) {
            for (Long pid : order.getProductIds()) {
                Product p = productRepo.findById(pid).orElse(null);
                if (p != null) {
                    p.setAvailableQuantity(p.getAvailableQuantity() + 1);
                    productRepo.save(p);
                }
            }
            order.setReturned(true);
            return orderRepo.save(order);
        }
        return null;
    }
}
