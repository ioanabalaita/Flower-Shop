package com.flowershop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cart_items")
public class CartItem extends BaseEntity{


    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "flower_id")
    private Flower flower;

    private String customName;

    @Column(length = 1000)
    private String customDetails;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private double unitPrice;

    public CartItem() {
    }

    public CartItem(User user, Flower flower, int quantity) {
        this.user = user;
        this.flower = flower;
        this.quantity = quantity;
        this.unitPrice = flower.getPrice();
    }

    public String getDisplayName() {
        return flower != null ? flower.getName() : customName;
    }

    public double getSubtotal() {
        return unitPrice * quantity;
    }



    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Flower getFlower() { return flower; }
    public void setFlower(Flower flower) { this.flower = flower; }

    public String getCustomName() { return customName; }
    public void setCustomName(String customName) { this.customName = customName; }

    public String getCustomDetails() { return customDetails; }
    public void setCustomDetails(String customDetails) { this.customDetails = customDetails; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
}