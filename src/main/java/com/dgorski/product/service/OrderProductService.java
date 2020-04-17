package com.dgorski.product.service;

import com.dgorski.product.entity.OrderProduct;
import com.dgorski.product.input.OrderProductInputDto;
import com.dgorski.product.repository.OrderProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class OrderProductService {

    OrderProductRepository orderProductRepository;

    ProductService productService;

    public OrderProductService(OrderProductRepository orderProductRepository, ProductService productService) {
        this.orderProductRepository = orderProductRepository;
        this.productService = productService;
    }

    public OrderProduct createOrder(OrderProductInputDto input) {
        // TODO przetestować
        OrderProduct newOrderProduct = new OrderProduct(input.getBuyerEmail(), productService.findProductById(input.getProductIds()));
        return orderProductRepository.save(newOrderProduct);
    }

    public List<OrderProduct> findOrdersBetween(LocalDateTime fromDate, LocalDateTime toDate) {
        return orderProductRepository.findAllByCreationDateBetween(fromDate, toDate);
    }
}
