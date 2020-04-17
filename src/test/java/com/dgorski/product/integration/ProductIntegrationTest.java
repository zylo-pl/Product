package com.dgorski.product.integration;

import com.dgorski.product.contoller.ProductController;
import com.dgorski.product.entity.Product;
import com.dgorski.product.input.ProductInputDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.dgorski.product.service.ProductServiceTest.TEST_PRODUCT_NAME;
import static com.dgorski.product.service.ProductServiceTest.TEST_PRICE;
import static com.dgorski.product.service.ProductServiceTest.TEST_PRICE2;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductIntegrationTest {

    @Autowired
    ProductController productController;

    @Test
    public void contextLoadsTest() {
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    public void createTwoProductAndGetAll() {
    	// given
        Product createdProduct = productController.createProduct(new ProductInputDto(TEST_PRODUCT_NAME, TEST_PRICE));
        Product createdProduct2 = productController.createProduct(new ProductInputDto(TEST_PRODUCT_NAME, TEST_PRICE));

        // when
        List<Product> listProduct = productController.getAllProducts();

        // then
        assertNotNull(listProduct);
        assertEquals(listProduct.size(), 2);
        assertTrue(listProduct.stream().map(product -> product.getId()).collect(Collectors.toSet()).containsAll(Arrays.asList(createdProduct.getId(), createdProduct2.getId())));
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    public void createProductMarkAsRemovedAndGetAll() {
    	// given
        Product createdProduct = productController.createProduct(new ProductInputDto(TEST_PRODUCT_NAME, TEST_PRICE));

        // when
        productController.markAsRemoved(createdProduct.getId());

        // then
        assertEquals(productController.getAllProducts().size(), 0);
    }

    @Test
    public void createProductAndGetById() {
    	// given
        Product createdProduct = productController.createProduct(new ProductInputDto(TEST_PRODUCT_NAME, TEST_PRICE));

        //when
        Product getByIdProduct = productController.getProduct(createdProduct.getId());

        // then
        assertEquals(getByIdProduct.getName(), TEST_PRODUCT_NAME);
        assertTrue(getByIdProduct.getPrice().compareTo(TEST_PRICE) == 0);
    }

    @Test
    public void createAndUpdateProductTest() {
        // given
        Product createdProduct = productController.createProduct(new ProductInputDto(TEST_PRODUCT_NAME, TEST_PRICE));
        ProductInputDto productEditionInputDto = new ProductInputDto(createdProduct.getName(), TEST_PRICE2);

        // when
        productController.updateProduct(createdProduct.getId(), productEditionInputDto);

        //then
        Product editedProduct = productController.getProduct(createdProduct.getId());
        assertNotNull(editedProduct);
        assertEquals(editedProduct.getName(), TEST_PRODUCT_NAME);
        assertTrue(editedProduct.getPrice().compareTo(TEST_PRICE2) == 0);
    }
}
