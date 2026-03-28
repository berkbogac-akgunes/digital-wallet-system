package com.berk.digitalwallet.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String itemName;

    @Column(nullable = false)
    private int quantity;

    public Inventory() {}

    public Inventory(User user, String itemName, int quantity) {
        this.user = user;
        this.itemName = itemName;
        this.quantity = quantity;
    }

    public User getUser() {
        return user;
    }

    public Long getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
