package com.mftplus.e_shop.model.service;

import com.mftplus.e_shop.model.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO save(ProductDTO productDto);
    ProductDTO update(Long id, ProductDTO dto);
    ProductDTO getById(Long id);
    List<ProductDTO> getByProductName(String productName);
    List<ProductDTO> findByProductQuantity(int quantity);
    List<ProductDTO> findAll();
    public ProductDTO rechargeProduct(Long productId);
    ProductDTO refillProduct(Long productId);
    ProductDTO sell(Long productId, int amount);
    void logicalRemove(Long id);
    ProductDTO reserveProduct(Long productId);
    ProductDTO releaseReservation(Long productId);
}
