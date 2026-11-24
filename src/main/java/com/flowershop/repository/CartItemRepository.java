package com.flowershop.repository;

import com.flowershop.model.CartItem;
import com.flowershop.model.Flower;
import com.flowershop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(User user);
    Optional<CartItem> findByUserAndFlower(User user, Flower flower);
    void deleteByUser(User user);
}