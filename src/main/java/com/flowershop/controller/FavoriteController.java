package com.flowershop.controller;

import com.flowershop.model.Favorite;
import com.flowershop.model.Flower;
import com.flowershop.model.User;
import com.flowershop.repository.FavoriteRepository;
import com.flowershop.repository.FlowerRepository;
import com.flowershop.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/favorites")
public class FavoriteController {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final FlowerRepository flowerRepository;

    public FavoriteController(FavoriteRepository favoriteRepository,
                              UserRepository userRepository,
                              FlowerRepository flowerRepository) {
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
        this.flowerRepository = flowerRepository;
    }

    @GetMapping
    public String viewFavorites(Principal principal, Model model) {
        User user = userRepository.findByUsername(principal.getName());
        List<Favorite> favorites = favoriteRepository.findByUser(user);
        model.addAttribute("favorites", favorites);
        return "favorites";
    }

    @PostMapping("/toggle/{flowerId}")
    public String toggleFavorite(@PathVariable Long flowerId,
                                 @RequestParam(defaultValue = "details") String from,
                                 Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        Flower flower = flowerRepository.findById(flowerId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid flower ID: " + flowerId));

        Optional<Favorite> existing = favoriteRepository.findByUserAndFlower(user, flower);
        if (existing.isPresent()) {
            favoriteRepository.delete(existing.get());
        } else {
            favoriteRepository.save(new Favorite(user, flower));
        }

        if ("favorites".equals(from)) {
            return "redirect:/favorites";
        }
        return "redirect:/shop/" + flowerId;
    }
}