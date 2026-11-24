package com.flowershop.repository;

import com.flowershop.model.Favorite;
import com.flowershop.model.Flower;
import com.flowershop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUser(User user);
    Optional<Favorite> findByUserAndFlower(User user, Flower flower);
    boolean existsByUserAndFlower(User user, Flower flower);
}