package com.mftplus.e_shop;

import com.mftplus.e_shop.model.dto.ProductDTO;
import com.mftplus.e_shop.model.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class ProductTest implements CommandLineRunner {
    private final ProductService productService;

    public ProductTest(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void run(String... args) {
        ProductDTO laptop = ProductDTO.builder()
                .amount(3)
                .productName("laptop")
                .code("123l")
                .price(23F)
                .expiredReservedDate(LocalDateTime.MAX.isAfter(LocalDateTime.now()) ? LocalDateTime.now().minusDays(1) : LocalDateTime.now())
                .quantity(100)
                .reserved(false)
                .build();
        ProductDTO savedLaptop = productService.save(laptop);
        log.info("saved laptop: {}", savedLaptop);


        ProductDTO mobile = ProductDTO.builder()
                .amount(1)
                .productName("mobile")
                .code("op123")
                .price(12F)
                .expiredReservedDate(LocalDateTime.MAX.isAfter(LocalDateTime.now()) ? LocalDateTime.now().minusDays(1) : LocalDateTime.now())
                .quantity(100)
                .reserved(false)
                .build();
        ProductDTO savedMobile = productService.save(mobile);
        log.info("Saved mobile {} ", savedMobile);

        //Sell
        ProductDTO sellLaptop = productService.sell(1L, 1);
        log.info("Sell product {} ", sellLaptop);

        ProductDTO sellMobile = productService.sell(2L, 1);
        log.info("Sell sellMobile {} ", sellMobile);

        //Charge The Product
        ProductDTO rechargeLaptop = productService.rechargeProduct(1L);
        log.info("Recharge product {} ", rechargeLaptop);

        //reserve product
        ProductDTO reservedLaptop = productService.reserveProduct(1L);
        log.info("Reserved product {} ", reservedLaptop);

        //release the reserved product
        ProductDTO releasedLaptop = productService.releaseReservation(1L);
        log.info("Released product {} ", releasedLaptop);

        //find by quantity 100 (complete)
        List<ProductDTO> findQuantity = productService.findByProductQuantity(100);
        log.info("Find quantity of {} ", findQuantity);

        //update product name
        savedLaptop.setProductName("camera");
        ProductDTO update = productService.update(1L, savedLaptop);
        System.out.println(update);

    }
}

