package com.flowershop.controller;

import com.flowershop.model.Order;
import com.flowershop.repository.OrderRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController {

    private final OrderRepository orderRepository;

    public AdminOrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public String viewAllOrders(Model model) {
        List<Order> orders = orderRepository.findAllByOrderByOrderDateDesc();
        model.addAttribute("orders", orders);
        return "admin-orders";
    }

    @PostMapping("/{id}/status")
    public String updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order ID: " + id));
        order.setStatus(status);
        orderRepository.save(order);
        return "redirect:/admin/orders";
    }
}