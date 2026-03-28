package com.berk.digitalwallet.controller;

import com.berk.digitalwallet.dto.DepositRequest;
import com.berk.digitalwallet.dto.RewardResponse;
import com.berk.digitalwallet.dto.TransactionResponse;
import com.berk.digitalwallet.dto.WithdrawRequest;
import com.berk.digitalwallet.entity.Inventory;
import com.berk.digitalwallet.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/deposit")
    public BigDecimal deposit(
            @Valid @RequestBody DepositRequest request,
            Authentication authentication
            ) {
        String email = authentication.getName();

        return walletService.deposit(email, request.getAmount());
    }

    @GetMapping("/balance")
    public BigDecimal getBalance(Authentication authentication) {

        String email = authentication.getName();

        return walletService.getBalance(email);
    }

    @PostMapping("/withdraw")
    public BigDecimal withdraw(
            @Valid @RequestBody WithdrawRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        return walletService.withdraw(email, request.getAmount(), request.getItemName());
    }

    @GetMapping("/transactions")
    public List<TransactionResponse> getTransactions(Authentication authentication) {

        String email = authentication.getName();

        return walletService.getTransactionHistory(email);
    }

    @PostMapping("/reward")
    public RewardResponse claimReward(Authentication authentication) {

        String email = authentication.getName();

        return walletService.claimReward(email);
    }

    @GetMapping("/inventory")
    public List<Inventory> getInventory(Authentication authentication) {
        String email = authentication.getName();
        return walletService.getInventory(email);
    }

}
