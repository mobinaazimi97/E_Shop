package com.mftplus.e_shop.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString
public class ProductDTO {
    private Long productId;
    private Float price;
    private String code;
    private int quantity = 100;
    private String productName;
    private int amount;
    private boolean reserved;
    private LocalDateTime expiredReservedDate;

}
