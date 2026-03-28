package com.berk.digitalwallet.dto;

public class InventoryResponse {
    private String itemName;
    private int quantity;

    public InventoryResponse(String itemName, int quantity) {
        this.itemName = itemName;
        this.quantity = quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }
}
