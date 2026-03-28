package com.berk.digitalwallet.dto;

import java.math.BigDecimal;

public class RewardResponse {

    private BigDecimal earned;
    private BigDecimal balance;

    public RewardResponse(BigDecimal earned, BigDecimal balance) {
        this.earned = earned;
        this.balance = balance;
    }

    public BigDecimal getEarned() {
        return earned;
    }

    public BigDecimal getBalance() {
        return balance;
    }

}