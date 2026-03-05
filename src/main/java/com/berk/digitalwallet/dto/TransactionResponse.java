package com.berk.digitalwallet.dto;

import com.berk.digitalwallet.entity.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponse {

    private BigDecimal amount;
    private TransactionType type;
    private LocalDateTime createdAt;

    public TransactionResponse(BigDecimal amount, TransactionType type, LocalDateTime createdAt) {
        this.amount = amount;
        this.type = type;
        this.createdAt = createdAt;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
