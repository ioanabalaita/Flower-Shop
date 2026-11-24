package com.flowershop.controller;

import com.flowershop.model.Flower;
import com.flowershop.service.FlowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class FlowerController {

    private final FlowerService flowerService;

    @Autowired
    public FlowerController(FlowerService flowerService) {
        this.flowerService = flowerService;
    }

    @GetMapping("/flowers")
    public String viewFlowersPage(Model model) {
        model.addAttribute("flowersList", flowerService.getAllFlowers());
        model.addAttribute("newFlower", new Flower());
        return "flowers";
    }

    @PostMapping("/flowers/add")
    public String addFlower(@ModelAttribute("newFlower") Flower flower) {
        flowerService.saveFlower(flower);
        return "redirect:/flowers";
    }


    @GetMapping("/flowers/delete/{id}")
    public String deleteFlower(@PathVariable("id") Long id) {

        flowerService.deleteFlower(id);

        return "redirect:/flowers";
    }

    @GetMapping("/flowers/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Flower flower = flowerService.getFlowerById(id);

        model.addAttribute("flowerToEdit", flower);
        return "edit-flower";
    }

    @PostMapping("/flowers/edit/{id}")
    public String updateFlower(@PathVariable("id") Long id, @ModelAttribute("flowerToEdit") Flower flower) {

        flower.setId(id);

        flowerService.saveFlower(flower);

        return "redirect:/flowers";
    }

}