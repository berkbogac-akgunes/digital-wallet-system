package com.berk.digitalwallet.service;

import com.berk.digitalwallet.dto.TransactionResponse;
import com.berk.digitalwallet.dto.RewardResponse;
import com.berk.digitalwallet.entity.*;
import com.berk.digitalwallet.exception.RewardCooldownException;
import com.berk.digitalwallet.repository.InventoryRepository;
import org.springframework.transaction.annotation.Transactional;
import com.berk.digitalwallet.exception.InvalidCredentialsException;
import com.berk.digitalwallet.repository.TransactionRepository;
import com.berk.digitalwallet.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WalletService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final InventoryRepository inventoryRepository;

    public WalletService(UserRepository userRepository, TransactionRepository transactionRepository, InventoryRepository inventoryRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional
    public BigDecimal deposit(String email, BigDecimal amount) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException("User not found"));

        Wallet wallet = user.getWallet();

        wallet.deposit(amount);

        Transaction transaction = new Transaction(
                amount,
                TransactionType.DEPOSIT,
                wallet
        );

        transactionRepository.save(transaction);

        return wallet.getBalance();

    }

    @Transactional(readOnly = true)
    public BigDecimal getBalance(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException("User not found"));

        return user.getWallet().getBalance();

    }

    @Transactional
    public BigDecimal withdraw(String email, BigDecimal amount, String itemName) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Wallet wallet = user.getWallet();

        wallet.withdraw(amount);

        Transaction transaction = new Transaction(
                amount,
                TransactionType.WITHDRAW,
                wallet
        );

        transactionRepository.save(transaction);

        Optional<Inventory> existing = inventoryRepository.findByUserAndItemName(user, itemName);

        if(existing.isPresent()){
            existing.get().setQuantity(existing.get().getQuantity() + 1);
            inventoryRepository.save(existing.get());
        } else {
            inventoryRepository.save(new Inventory(user, itemName, 1));
        }

        return wallet.getBalance();

    }

    @Transactional(readOnly = true)
    public List<TransactionResponse> getTransactionHistory(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException("User not found"));

        Wallet wallet = user.getWallet();

        List<Transaction> transactions =
                transactionRepository.findByWalletIdOrderByCreatedAtDesc(wallet.getId());

        return transactions.stream()
                .map(t -> new TransactionResponse(
                        t.getAmount(),
                        t.getType(),
                        t.getCreatedAt()
                ))
                .toList();
    }

    @Transactional
    public RewardResponse claimReward(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException("User not found"));

        Wallet wallet = user.getWallet();

        LocalDateTime now = LocalDateTime.now();

        if (wallet.getLastRewardAt() != null &&
                wallet.getLastRewardAt().plusSeconds(5).isAfter(now)) {

            throw new RewardCooldownException("Reward cooldown not finished");
        }

        int randomAmount = 1 + (int)(Math.random() * 5);
        BigDecimal reward = new BigDecimal(randomAmount);

        wallet.deposit(reward);

        wallet.setLastRewardAt(now);

        Transaction transaction = new Transaction(
                reward,
                TransactionType.DEPOSIT,
                wallet
        );

        transactionRepository.save(transaction);

        return new RewardResponse(reward, wallet.getBalance());
    }

    @Transactional(readOnly = true)
    public List<Inventory> getInventory(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException("User not found"));
        return inventoryRepository.findByUser(user);
    }

}
