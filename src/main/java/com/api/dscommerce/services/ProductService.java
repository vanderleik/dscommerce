package com.api.dscommerce.services;

import com.api.dscommerce.dto.ProductDTO;
import com.api.dscommerce.entities.Product;
import com.api.dscommerce.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private static ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Product result = productRepository.findById(id).orElseThrow();

        return new ProductDTO(result);
    }
}
