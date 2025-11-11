package com.mftplus.e_shop.model.mapper;


import com.mftplus.e_shop.config.CentralMapperConfig;
import com.mftplus.e_shop.model.dto.ProductDTO;
import com.mftplus.e_shop.model.entity.Product;
import com.mftplus.e_shop.model.repository.ProductRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(config = CentralMapperConfig.class,uses = {ProductRepository.class})
public interface ProductMapper {
    ProductDTO toDto(Product product);
    Product toEntity(ProductDTO productDto);

    List<ProductDTO> toDtoList(List<Product> productList);

    List<Product> toEntityList(List<ProductDTO> productDtoList);

    @Mapping(target = "productId", ignore = true)
    void updateFromDto(ProductDTO productDto, @MappingTarget Product product);

}