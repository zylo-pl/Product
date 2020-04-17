package com.dgorski.product.service;

import com.dgorski.product.entity.OrderProduct;
import com.dgorski.product.input.OrderProductInputDto;
import com.dgorski.product.repository.OrderProductRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderProductServiceTest {

    private OrderProductRepository orderProductRepository = mock(OrderProductRepository.class);
    private ProductService productService = mock(ProductService.class);
    private OrderProductService orderProductService = new OrderProductService(orderProductRepository, productService);

    public static final String TEST_EMAIL = "test@domain.com";

    @Test
    public void createProductOrderTest() {
        // given
        OrderProduct baseOrderProduct = buildTestOrderProduct();
        OrderProduct baseOrderProductWithId = buildTestOrderProduct();
        baseOrderProductWithId.setId(1L);
        when(orderProductRepository.save(baseOrderProduct)).thenReturn(baseOrderProductWithId);

        // when
        OrderProduct orderProduct = orderProductService.createOrder(new OrderProductInputDto(TEST_EMAIL, Collections.emptySet()));

        // then
        assertNotNull(orderProduct);
        assertEquals(orderProduct.getBuyerEmail(), TEST_EMAIL);
        assertTrue(orderProduct.getProducts() == null || orderProduct.getProducts().size() == 0);
    }

    @Test
    public void findOrdersBetweenTest() {
        // given
        when(orderProductRepository.findAllByCreationDateBetween(any(), any())).thenReturn(Arrays.asList(buildTestOrderProduct()));

        // when
        List<OrderProduct> orderProductList = orderProductService.findOrdersBetween(LocalDateTime.now(), LocalDateTime.now());

        // then
        assertNotNull(orderProductList);
        assertEquals(orderProductList.size(), 1);
    }

    private OrderProduct buildTestOrderProduct() {
        return new OrderProduct(TEST_EMAIL, Collections.emptyList());
    }
}
