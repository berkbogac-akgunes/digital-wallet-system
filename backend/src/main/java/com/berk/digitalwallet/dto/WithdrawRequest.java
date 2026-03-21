package com.berk.digitalwallet.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class WithdrawRequest {

    @NotNull
    @Positive
    private BigDecimal amount;

    public WithdrawRequest(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

}
