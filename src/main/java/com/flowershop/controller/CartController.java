package com.flowershop.controller;

import com.flowershop.model.CartItem;
import com.flowershop.model.Flower;
import com.flowershop.model.User;
import com.flowershop.repository.CartItemRepository;
import com.flowershop.repository.FlowerRepository;
import com.flowershop.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartItemRepository cartItemRepository;
    private final FlowerRepository flowerRepository;
    private final UserRepository userRepository;

    public CartController(CartItemRepository cartItemRepository,
                          FlowerRepository flowerRepository,
                          UserRepository userRepository) {
        this.cartItemRepository = cartItemRepository;
        this.flowerRepository = flowerRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String viewCart(Principal principal, Model model) {
        User user = userRepository.findByUsername(principal.getName());
        List<CartItem> items = cartItemRepository.findByUser(user);
        double total = items.stream().mapToDouble(CartItem::getSubtotal).sum();

        model.addAttribute("cartItems", items);
        model.addAttribute("totalPrice", total);
        return "cart";
    }

    @PostMapping("/add/{flowerId}")
    public String addToCart(@PathVariable Long flowerId,
                            @RequestParam(defaultValue = "1") int quantity,
                            Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        Flower flower = flowerRepository.findById(flowerId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid flower ID: " + flowerId));

        if (quantity < 1) {
            quantity = 1;
        }

        Optional<CartItem> existing = cartItemRepository.findByUserAndFlower(user, flower);
        if (existing.isPresent()) {
            CartItem item = existing.get();
            item.setQuantity(item.getQuantity() + quantity);
            cartItemRepository.save(item);
        } else {
            cartItemRepository.save(new CartItem(user, flower, quantity));
        }

        return "redirect:/cart";
    }

    @PostMapping("/update/{itemId}")
    public String updateQuantity(@PathVariable Long itemId, @RequestParam int quantity) {
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid cart item ID: " + itemId));

        if (quantity <= 0) {
            cartItemRepository.delete(item);
        } else {
            item.setQuantity(quantity);
            cartItemRepository.save(item);
        }
        return "redirect:/cart";
    }

    @PostMapping("/remove/{itemId}")
    public String removeItem(@PathVariable Long itemId) {
        cartItemRepository.deleteById(itemId);
        return "redirect:/cart";
    }
}