package com.mftplus.e_shop.model.repository;

import com.mftplus.e_shop.model.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select p from productEntity p where p.productName like :productName")
    List<Product> findByProductName(@Param("productName") String productName);

    @Query("select p from productEntity p where p.quantity=:quantity")
    List<Product> findByProductQuantity(int quantity);

    @Query("select p from productEntity p where p.productId=:productId")
    Optional<Product> findByProductId(@Param("productId") Long productId);

    @Modifying
    @Transactional
    @Query("update productEntity p set p.quantity = p.quantity + p.amount " +
            "where p.productId = :productId and p.quantity < p.amount")
    int refillQuantity(@Param("productId") Long productId);

    @Modifying
    @Transactional
    @Query("update productEntity p set p.quantity = p.quantity - :amount " +
            "where p.productId = :productId and p.quantity >= :amount")
    int lostQuantity(@Param("productId") Long productId, @Param("amount") int amount);

    @Modifying
    @Query("update productEntity p set p.isDeleted=true where p.productId= :productId")
    @Transactional
    void logicalRemove(@Param("productId") Long productId);
}
