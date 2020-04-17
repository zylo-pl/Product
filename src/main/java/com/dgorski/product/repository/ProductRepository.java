package com.dgorski.product.repository;

import com.dgorski.product.entity.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findByActiveTrue();
    List<Product> findByActiveTrueAndIdIn(Set<Long> ids);
}
