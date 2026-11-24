package com.flowershop;

import com.flowershop.exception.ResourceNotFoundException;
import com.flowershop.model.CartItem;
import com.flowershop.model.Flower;
import com.flowershop.model.Order;
import com.flowershop.model.OrderItem;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlowerShopUnitTests {

    @Test
    void testFlowerInheritanceAndProperties() {
        Flower flower = new Flower();
        flower.setId(1L);
        flower.setName("Pink Peony");
        flower.setPrice(25.5);
        flower.setCategory("Single Stem");
        flower.setStockQuantity(10);

        assertEquals(1L, flower.getId());
        assertEquals("Pink Peony", flower.getName());
        assertEquals(25.5, flower.getPrice());
        assertEquals("Single Stem", flower.getCategory());
        assertEquals(10, flower.getStockQuantity());
    }

    @Test
    void testCustomBouquetCartItem() {
        CartItem item = new CartItem();
        item.setCustomName("Custom Bouquet");
        item.setCustomDetails("Flowers: 3x Rose | Wrapping: Kraft Paper");
        item.setQuantity(1);
        item.setUnitPrice(65.0);

        assertNull(item.getFlower());
        assertEquals("Custom Bouquet", item.getCustomName());
        assertEquals(65.0, item.getUnitPrice());
    }

    @Test
    void testOrderStatusAndDeliveryAddress() {
        Order order = new Order();
        order.setId(10L);
        order.setDeliveryAddress("Brasov, Str. Republicii 12");
        order.setStatus("PENDING");
        order.setTotalPrice(120.0);

        assertEquals(10L, order.getId());
        assertEquals("PENDING", order.getStatus());
        assertEquals("Brasov, Str. Republicii 12", order.getDeliveryAddress());
        assertEquals(120.0, order.getTotalPrice());
    }

    @Test
    void testOrderContainsItems() {
        Order order = new Order();
        OrderItem item1 = new OrderItem();
        item1.setItemName("Red Rose");
        item1.setQuantity(2);

        OrderItem item2 = new OrderItem();
        item2.setItemName("White Tulip");
        item2.setQuantity(3);

        order.setItems(List.of(item1, item2));

        assertEquals(2, order.getItems().size());
        assertEquals("Red Rose", order.getItems().get(0).getItemName());
    }

    @Test
    void testResourceNotFoundExceptionMessage() {
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> {
                    throw new ResourceNotFoundException("Flower with ID 999 was not found.");
                }
        );

        assertEquals("Flower with ID 999 was not found.", exception.getMessage());
    }
}