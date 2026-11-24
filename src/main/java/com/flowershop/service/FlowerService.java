package com.flowershop.service;

import com.flowershop.model.Flower;
import com.flowershop.repository.FlowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlowerService {

    private final FlowerRepository flowerRepository;

    @Autowired
    public FlowerService(FlowerRepository flowerRepository) {
        this.flowerRepository = flowerRepository;
    }

    public List<Flower> getAllFlowers() {
        return flowerRepository.findAll();
    }

    public void saveFlower(Flower flower) {
        flowerRepository.save(flower);
    }



    public void deleteFlower(Long id) {
        flowerRepository.deleteById(id);
    }


    public Flower getFlowerById(Long id) {
        return flowerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID-ul florii este invalid: " + id));
    }

}