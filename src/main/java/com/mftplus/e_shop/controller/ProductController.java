package com.mftplus.e_shop.controller;

import com.mftplus.e_shop.controller.controllerExceptions.ProductNotFoundException;
import com.mftplus.e_shop.model.dto.ProductDTO;
import com.mftplus.e_shop.model.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/save")
    public ResponseEntity<ProductDTO> saveProduct(@RequestBody ProductDTO productDto) {
        ProductDTO savedProduct = productService.save(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long productId, @RequestBody ProductDTO productDto) {
        ProductDTO updated = productService.update(productId, productDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long productId) {
        productService.logicalRemove(productId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> productDtoList = productService.findAll();
        return new ResponseEntity<>(productDtoList, HttpStatus.OK);
    }

    @GetMapping("/id/{productId}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.getById(productId));
    }

    @GetMapping("/name/{productName}")
    public ResponseEntity<List<ProductDTO>> getProductName(@PathVariable String productName) {
        return ResponseEntity.ok(productService.getByProductName(productName));
    }

    @GetMapping("/quantity/{productQuantity}")
    public ResponseEntity<List<ProductDTO>> findByProductQuantity(@PathVariable int productQuantity) {
        return ResponseEntity.ok(productService.findByProductQuantity(productQuantity));
    }

    @PutMapping("/recharge/{productId}")
    public ResponseEntity<ProductDTO> rechargeProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.rechargeProduct(productId));
    }

    @PutMapping("/refill/{productId}")
    public ResponseEntity<ProductDTO> refillProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.refillProduct(productId));
    }

    @PutMapping("/sell/{productId}/{amount}")
    public ResponseEntity<ProductDTO> sell(@PathVariable Long productId, @PathVariable int amount) {
        return ResponseEntity.ok(productService.sell(productId, amount));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public String handleUsernameNotFoundException(ProductNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception ex) {
        return ex.getMessage();
    }
}
