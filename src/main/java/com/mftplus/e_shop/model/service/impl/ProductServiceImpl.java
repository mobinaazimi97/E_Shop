package com.mftplus.e_shop.model.service.impl;

import com.mftplus.e_shop.model.dto.ProductDTO;
import com.mftplus.e_shop.model.entity.Product;
import com.mftplus.e_shop.model.mapper.ProductMapper;
import com.mftplus.e_shop.model.repository.ProductRepository;
import com.mftplus.e_shop.model.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }


    @Transactional
    @Override
    public ProductDTO save(ProductDTO productDto) {
        Product product = productMapper.toEntity(productDto);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDto(savedProduct);
    }

    @Transactional
    @Override
    public ProductDTO update(Long id, ProductDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found to update"));
        productMapper.updateFromDto(dto, product);
        Product saved = productRepository.save(product);
        return productMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    @Override
    public ProductDTO getById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found by id"));
        return productMapper.toDto(product);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductDTO> getByProductName(String productName) {
        List<Product> products = productRepository.findByProductName(productName);
        return productMapper.toDtoList(products);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductDTO> findByProductQuantity(int quantity) {
        List<Product> products = productRepository.findByProductQuantity(quantity);
        return productMapper.toDtoList(products);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductDTO> findAll() {
        List<Product> products = productRepository.findAll()
                .stream()
                .filter(p -> !p.isDeleted())
                .collect(Collectors.toList());

        return productMapper.toDtoList(products);
    }

    @Transactional
    @Override
    public ProductDTO rechargeProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getQuantity() < 100) {
            product.setQuantity(100);
            productRepository.save(product);
        }

        return productMapper.toDto(product);
    }

    @Transactional
    @Override
    public ProductDTO refillProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getQuantity() < product.getAmount()) {
            product.setQuantity(product.getQuantity() + product.getAmount());
            productRepository.save(product);
        }

        return productMapper.toDto(product);
    }

    @Transactional
    @Override
    public ProductDTO sell(Long productId, int amount) {
        int lost = productRepository.lostQuantity(productId, amount);
        if (lost == 0) {
            log.info("Insufficient quantity or product not found");
        }
        productRepository.refillQuantity(productId);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found by id"));
        return productMapper.toDto(product);
    }

    public void logicalRemove(Long id) {
        Product productId = productRepository.findByProductId(id).orElseThrow(() -> new EntityNotFoundException("Product not found to remove"));
        if (productId != null) {
            productRepository.logicalRemove(productId.getProductId());
        }
    }

    @Transactional
    @Override
    public ProductDTO reserveProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found to reserve"));
        if (product != null) {
            product.setReserved(true);
            product.setQuantity(product.getQuantity() - product.getAmount());
        }

        return productMapper.toDto(product);
    }

    @Transactional
    @Override
    public ProductDTO releaseReservation(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        if (product != null) {
            product.setReserved(false);
            product.setQuantity(product.getQuantity() + product.getAmount());
        }
        return productMapper.toDto(product);
    }

}
