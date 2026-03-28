package com.berk.digitalwallet.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class WithdrawRequest {

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotNull
    private String itemName;

    public WithdrawRequest(BigDecimal amount, String itemName) {
        this.amount = amount;
        this.itemName = itemName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getItemName() {
        return itemName;
    }

}
