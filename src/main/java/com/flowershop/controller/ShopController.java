package com.flowershop.controller;

import com.flowershop.exception.ResourceNotFoundException;
import com.flowershop.model.Flower;
import com.flowershop.model.User;
import com.flowershop.repository.FavoriteRepository;
import com.flowershop.repository.FlowerRepository;
import com.flowershop.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/shop")
public class ShopController {

    private final FlowerRepository flowerRepository;
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;

    public ShopController(FlowerRepository flowerRepository,
                          FavoriteRepository favoriteRepository,
                          UserRepository userRepository) {
        this.flowerRepository = flowerRepository;
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String viewCatalog(@RequestParam(required = false) String category, Model model) {
        List<Flower> flowers;
        if (category != null && !category.isBlank()) {
            flowers = flowerRepository.findByCategory(category);
            model.addAttribute("selectedCategory", category);
        } else {
            flowers = flowerRepository.findAll();
            model.addAttribute("selectedCategory", "All");
        }
        model.addAttribute("flowers", flowers);
        return "shop";
    }

    @GetMapping("/{id}")
    public String viewFlowerDetails(@PathVariable Long id, Principal principal, Model model) {
        Flower flower = flowerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flower with ID " + id + " was not found."));
        boolean isFavorite = false;
        if (principal != null) {
            User user = userRepository.findByUsername(principal.getName());
            if (user != null) {
                isFavorite = favoriteRepository.existsByUserAndFlower(user, flower);
            }
        }

        model.addAttribute("flower", flower);
        model.addAttribute("isFavorite", isFavorite);
        return "flower-details";
    }
}