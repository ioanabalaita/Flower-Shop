package com.flowershop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem extends BaseEntity{



    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "flower_id")
    private Flower flower;

    @Column(nullable = false)
    private String itemName;

    @Column(length = 1000)
    private String customDetails;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private double unitPrice;

    public OrderItem() {
    }

    public double getSubtotal() {
        return unitPrice * quantity;
    }


    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public Flower getFlower() { return flower; }
    public void setFlower(Flower flower) { this.flower = flower; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public String getCustomDetails() { return customDetails; }
    public void setCustomDetails(String customDetails) { this.customDetails = customDetails; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
}