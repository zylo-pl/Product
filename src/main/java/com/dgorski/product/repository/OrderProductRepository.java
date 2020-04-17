package com.dgorski.product.repository;

import com.dgorski.product.entity.OrderProduct;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderProductRepository extends CrudRepository<OrderProduct, Long> {
    List<OrderProduct> findAllByCreationDateBetween(LocalDateTime fromDate, LocalDateTime toDate);
}
