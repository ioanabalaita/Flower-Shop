package com.flowershop.repository;

import com.flowershop.model.Order;
import com.flowershop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserOrderByOrderDateDesc(User user);
    List<Order> findAllByOrderByOrderDateDesc();
}