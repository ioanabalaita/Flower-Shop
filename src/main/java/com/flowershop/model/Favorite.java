package com.flowershop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "favorites")
public class Favorite extends BaseEntity{


    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "flower_id")
    private Flower flower;

    public Favorite() {
    }

    public Favorite(User user, Flower flower) {
        this.user = user;
        this.flower = flower;
    }


    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Flower getFlower() { return flower; }
    public void setFlower(Flower flower) { this.flower = flower; }
}