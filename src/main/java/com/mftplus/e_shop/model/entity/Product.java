package com.mftplus.e_shop.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Entity(name = "productEntity")
@Table(name = "products")
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@SQLRestriction("is_deleted=false")
public class Product extends Base {
    @Id
    @SequenceGenerator(name = "productSeq", sequenceName = "product_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productSeq")
    private Long productId;

    @Column(name = "price")
    @Min(value = 0, message = "Price Cant Be Negative!")
    private Float price;

    @Column(name = "serialId", length = 30)
    private String code;

    @Column(name = "quantity")
    private int quantity = 100;

    @Column(name = "product_name", length = 40)
    private String productName;

    @Column(name = "product_amount", length = 5)
    @Min(value = 1, message = "amount cant be less than 1 !!!")
    private int amount;

    @Column(name = "reserved")
    private boolean reserved;

    @Column(name = "expiredReservedDate")
    private LocalDateTime expiredReservedDate;
}
