package com.dgorski.product.service;

import com.dgorski.product.entity.Product;
import com.dgorski.product.input.ProductInputDto;
import com.dgorski.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductServiceTest {

    private ProductRepository productRepository = mock(ProductRepository.class);
    private ProductService productService = new ProductService(productRepository);

    public static final String TEST_PRODUCT_NAME = "TestProduct";
    public static final String TEST_PRODUCT_NAME2 = "Second TestProduct";
    public static final BigDecimal TEST_PRICE = new BigDecimal(10);
    public static final BigDecimal TEST_PRICE2 = new BigDecimal(20);

    @Test
    public void findProductByIdTest() {
        // given
        when(productRepository.findById(1L)).thenReturn(Optional.of(buildTestProduct()));

        // when
        Product product = productService.findProductById(1L);

        // then
        assertNotNull(product);
        assertEquals(product.getName(), TEST_PRODUCT_NAME);
        assertEquals(product.getPrice(), TEST_PRICE);
    }

    @Test
    public void getAllProductsTest() {
        // given
        when(productRepository.findByActiveTrue()).thenReturn(Arrays.asList(buildTestProduct(), buildTestProduct()));

        // when
        List<Product> productList = productService.getAllProducts();

        // then
        assertNotNull(productList);
        assertEquals(productList.size(), 2);
    }

    @Test
    public void getProductByIds() {
        // given
        when(productRepository.findByActiveTrueAndIdIn(Set.of(1L, 2L))).thenReturn(Arrays.asList(buildTestProduct(), buildTestProduct()));

        // when
        List<Product> productList = productService.findProductById(Set.of(1L, 2L));

        // then
        assertNotNull(productList);
        assertEquals(productList.size(), 2);
    }

    @Test
    public void createProductTest() {
        // given
        Product baseProduct = new Product(new ProductInputDto(TEST_PRODUCT_NAME, TEST_PRICE));
        when(productRepository.save(baseProduct)).thenReturn(buildTestProduct());

        // when
        Product product = productService.createProduct(new ProductInputDto(TEST_PRODUCT_NAME, TEST_PRICE));

        // then
        assertNotNull(product);
        assertEquals(product.getName(), TEST_PRODUCT_NAME);
        assertEquals(product.getPrice(), TEST_PRICE);
    }

    private Product buildTestProduct() {
       return new Product(new ProductInputDto(TEST_PRODUCT_NAME, TEST_PRICE));
    }

}
