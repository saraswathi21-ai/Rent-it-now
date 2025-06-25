package com.rentitnow.service;

import com.rentitnow.model.CartItem;
import com.rentitnow.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository repo;

    public CartService(CartRepository repo) {
        this.repo = repo;
    }

    public CartItem add(CartItem item) {
        return repo.save(item);
    }

    public List<CartItem> getUserCart(Long userId) {
        return repo.findByUserId(userId);
    }

    public void remove(Long id) {
        repo.deleteById(id);
    }
}
