package com.flowershop.controller;

import com.flowershop.model.*;
import com.flowershop.repository.CartItemRepository;
import com.flowershop.repository.FlowerRepository;
import com.flowershop.repository.OrderRepository;
import com.flowershop.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final FlowerRepository flowerRepository;
    private final UserRepository userRepository;

    public OrderController(OrderRepository orderRepository,
                           CartItemRepository cartItemRepository,
                           FlowerRepository flowerRepository,
                           UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.cartItemRepository = cartItemRepository;
        this.flowerRepository = flowerRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String viewOrders(Principal principal, Model model) {
        User user = userRepository.findByUsername(principal.getName());
        List<Order> orders = orderRepository.findByUserOrderByOrderDateDesc(user);
        model.addAttribute("orders", orders);
        return "orders";
    }

    @PostMapping("/checkout")
    @Transactional
    public String placeOrder(@RequestParam String deliveryAddress, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        List<CartItem> cartItems = cartItemRepository.findByUser(user);

        if (cartItems.isEmpty()) {
            return "redirect:/cart";
        }

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");
        order.setDeliveryAddress(deliveryAddress);

        double total = 0;
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setFlower(cartItem.getFlower());
            orderItem.setItemName(cartItem.getDisplayName());
            orderItem.setCustomDetails(cartItem.getCustomDetails());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(cartItem.getUnitPrice());
            order.getItems().add(orderItem);

            total += cartItem.getSubtotal();

            if (cartItem.getFlower() != null) {
                Flower flower = cartItem.getFlower();
                int newStock = Math.max(0, flower.getStockQuantity() - cartItem.getQuantity());
                flower.setStockQuantity(newStock);
                flowerRepository.save(flower);
            }
        }

        order.setTotalPrice(total);
        orderRepository.save(order);
        cartItemRepository.deleteByUser(user);

        return "redirect:/orders";
    }
}