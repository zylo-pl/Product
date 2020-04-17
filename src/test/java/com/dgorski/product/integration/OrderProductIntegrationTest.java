package com.dgorski.product.integration;

import com.dgorski.product.contoller.OrderProductContoller;
import com.dgorski.product.contoller.ProductController;
import com.dgorski.product.entity.OrderProduct;
import com.dgorski.product.entity.Product;
import com.dgorski.product.input.OrderProductInputDto;
import com.dgorski.product.input.ProductInputDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.dgorski.product.service.OrderProductServiceTest.TEST_EMAIL;
import static com.dgorski.product.service.ProductServiceTest.TEST_PRODUCT_NAME;
import static com.dgorski.product.service.ProductServiceTest.TEST_PRODUCT_NAME2;
import static com.dgorski.product.service.ProductServiceTest.TEST_PRICE;
import static com.dgorski.product.service.ProductServiceTest.TEST_PRICE2;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class OrderProductIntegrationTest {

    @Autowired
    ProductController productController;

    @Autowired
    OrderProductContoller orderProductController;

    @Test
    public void contextLoadsTest() {
    }

    @Test
    public void createProductAndOrderTest() {
        // given
        Product product = productController.createProduct(new ProductInputDto(TEST_PRODUCT_NAME, TEST_PRICE));

        // when
        OrderProduct orderProduct = orderProductController.createOrderProduct(new OrderProductInputDto(TEST_EMAIL, Set.of(product.getId())));

        // then
        assertNotNull(orderProduct);
        assertEquals(orderProduct.getBuyerEmail(), TEST_EMAIL);
        assertNotNull(orderProduct.getProducts());
        assertEquals(orderProduct.getProducts().size(), 1);
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    public void findOrderProductBetweenTest() {
        // given: products and start time
        LocalDateTime startTestTime = LocalDateTime.now();
        Product product1 = productController.createProduct(new ProductInputDto(TEST_PRODUCT_NAME, TEST_PRICE));
        Product product2 = productController.createProduct(new ProductInputDto(TEST_PRODUCT_NAME2, TEST_PRICE2));
        BigDecimal totalCost = TEST_PRICE.add(TEST_PRICE2);

        // given: first order with 2 products
        OrderProduct orderProduct1 = orderProductController.createOrderProduct(new OrderProductInputDto(TEST_EMAIL, Set.of(product1.getId(), product2.getId())));
        assertTrue(orderProduct1.getTotalPrice().compareTo(totalCost) == 0);

        // when: changes in products
        productController.markAsRemoved(product1.getId());
        productController.updateProduct(product2.getId(), new ProductInputDto(product2.getName(), TEST_PRICE));

        // when: create second order
        OrderProduct orderProduct2 = orderProductController.createOrderProduct(new OrderProductInputDto(TEST_EMAIL, Set.of(product1.getId(), product2.getId())));
        assertTrue(orderProduct2.getTotalPrice().compareTo(TEST_PRICE) == 0);

        // get all orders between test start time and now
        LocalDateTime afterOrderCreationTime = LocalDateTime.now();

        // when: final test
        List<OrderProduct> orderProductList = orderProductController.getOrderProductsBetween(startTestTime, afterOrderCreationTime);

        // then
        assertNotNull(orderProductList);
        assertEquals(orderProductList.size(), 2);
    }

}
