package com.berk.digitalwallet.repository;

import com.berk.digitalwallet.entity.Inventory;
import com.berk.digitalwallet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByUserAndItemName(User user, String itemName);
    List<Inventory> findByUser(User user);

}
