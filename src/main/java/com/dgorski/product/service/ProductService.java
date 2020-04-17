package com.dgorski.product.service;

import com.dgorski.product.entity.Product;
import com.dgorski.product.exception.ProductNotFoundException;
import com.dgorski.product.input.ProductInputDto;
import com.dgorski.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProductService {

    private ProductRepository productRepository;

    private static final String PRODUCT_NOT_FOUND = "Product not found: ";

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(ProductInputDto input) {
        return productRepository.save(new Product(input));
    }

    public Product findProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND + id));
    }

    public List<Product> findProductById(Set<Long> ids) {
        return productRepository.findByActiveTrueAndIdIn(ids);
    }

    public List<Product> getAllProducts() {
        return productRepository.findByActiveTrue();
    }

    public Product updateProduct(Long id, ProductInputDto input) {
        Product product = findProductById(id);
        product.update(input);
        return productRepository.save(product);
    }

    public void markAsRemoved(Long id) {
        Product product = findProductById(id);
        product.markAsRemoved();
        productRepository.save(product);
    }
}
