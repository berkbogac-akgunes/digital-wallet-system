package com.berk.digitalwallet.service;

import com.berk.digitalwallet.entity.Transaction;
import com.berk.digitalwallet.entity.TransactionType;
import com.berk.digitalwallet.entity.User;
import com.berk.digitalwallet.entity.Wallet;
import com.berk.digitalwallet.exception.InvalidCredentialsException;
import com.berk.digitalwallet.exception.RewardCooldownException;
import com.berk.digitalwallet.repository.TransactionRepository;
import com.berk.digitalwallet.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private WalletService walletService;

    private User user;
    private Wallet wallet;

    @BeforeEach
    void setUp() {
        wallet = new Wallet();
        user = new User("test@test.com", "hashedPassword");
        user.assignWallet(wallet);
    }

    @Test
    void deposit_success() {
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));

        BigDecimal result = walletService.deposit("test@test.com", new BigDecimal("100"));

        assertEquals(new BigDecimal("100"), result);
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    void deposit_userNotFound_throwsException() {
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.empty());

        assertThrows(InvalidCredentialsException.class,
                () -> walletService.deposit("test@test.com", new BigDecimal("100")));
    }

    @Test
    void withdraw_success() {
        wallet.deposit(new BigDecimal("200"));
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));

        BigDecimal result = walletService.withdraw("test@test.com", new BigDecimal("50"));

        assertEquals(new BigDecimal("150"), result);
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    void getBalance_success() {
        wallet.deposit(new BigDecimal("500"));
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));

        BigDecimal result = walletService.getBalance("test@test.com");

        assertEquals(new BigDecimal("500"), result);
    }

    @Test
    void getTransactionHistory_success() {
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));
        when(transactionRepository.findByWalletIdOrderByCreatedAtDesc(any()))
                .thenReturn(List.of(
                        new Transaction(new BigDecimal("100"), TransactionType.DEPOSIT, wallet),
                        new Transaction(new BigDecimal("50"), TransactionType.WITHDRAW, wallet)
                ));

        var result = walletService.getTransactionHistory("test@test.com");

        assertEquals(2, result.size());
        assertEquals(TransactionType.DEPOSIT, result.get(0).getType());
    }

    @Test
    void claimReward_success() {
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));

        BigDecimal result = walletService.claimReward("test@test.com");

        assertEquals(BigDecimal.ONE, result);
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    void claimReward_cooldownNotFinished_throwsException() {
        wallet.setLastRewardAt(LocalDateTime.now().minusSeconds(2)); // 5 saniye dolmamış
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));

        assertThrows(RewardCooldownException.class,
                () -> walletService.claimReward("test@test.com"));
    }
}