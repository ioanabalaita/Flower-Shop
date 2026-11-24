package com.flowershop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "flowers")
public class Flower extends BaseEntity{


    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double price;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private int stockQuantity = 10;

    private String imageUrl;

    public Flower() {
    }

    public Flower(String name, double price, String description, String category, int stockQuantity, String imageUrl) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.stockQuantity = stockQuantity;
        this.imageUrl = imageUrl;
    }


    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}