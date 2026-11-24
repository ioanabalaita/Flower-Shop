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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/custom-bouquet.html")
public class CustomBouquetController {

    private final FlowerRepository flowerRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    public CustomBouquetController(FlowerRepository flowerRepository,
                                   CartItemRepository cartItemRepository,
                                   UserRepository userRepository) {
        this.flowerRepository = flowerRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String showBuilder(Model model) {
        List<Flower> singleStems = flowerRepository.findByCategory("Single Stem");
        model.addAttribute("singleStems", singleStems);
        return "custom-bouquet.html";
    }

    @PostMapping("/add")
    public String addCustomBouquetToCart(@RequestParam Map<String, String> params,
                                         @RequestParam(defaultValue = "Kraft Paper") String wrapping,
                                         @RequestParam(defaultValue = "Satin Pink") String ribbon,
                                         @RequestParam(required = false) String cardMessage,
                                         Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        List<Flower> singleStems = flowerRepository.findByCategory("Single Stem");

        double totalPrice = 0;
        List<String> selectedFlowers = new ArrayList<>();

        for (Flower flower : singleStems) {
            String paramKey = "qty_" + flower.getId();
            if (params.containsKey(paramKey) && !params.get(paramKey).isBlank()) {
                int qty = Integer.parseInt(params.get(paramKey));
                if (qty > 0) {
                    totalPrice += flower.getPrice() * qty;
                    selectedFlowers.add(qty + "x " + flower.getName());
                }
            }
        }

        if (selectedFlowers.isEmpty()) {
            return "redirect:/custom-bouquet.html?error=empty";
        }

        double wrappingPrice = switch (wrapping) {
            case "Pastel Pink Paper" -> 10.0;
            case "Luxury Gift Box" -> 25.0;
            default -> 5.0; // Kraft Paper
        };
        totalPrice += wrappingPrice;

        StringBuilder details = new StringBuilder();
        details.append("Flowers: ").append(String.join(", ", selectedFlowers));
        details.append(" | Wrapping: ").append(wrapping);
        details.append(" | Ribbon: ").append(ribbon);
        if (cardMessage != null && !cardMessage.isBlank()) {
            details.append(" | Note: \"").append(cardMessage.trim()).append("\"");
        }

        CartItem customItem = new CartItem();
        customItem.setUser(user);
        customItem.setFlower(null);
        customItem.setCustomName("Custom Bouquet");
        customItem.setCustomDetails(details.toString());
        customItem.setQuantity(1);
        customItem.setUnitPrice(totalPrice);

        cartItemRepository.save(customItem);
        return "redirect:/cart";
    }
}