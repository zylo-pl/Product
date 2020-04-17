package com.dgorski.product.entity;

import com.dgorski.product.input.ProductInputDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private BigDecimal price;

    private LocalDateTime createDate;

    private Boolean active;

    public Product() {
    }

    public Product(ProductInputDto input) {
        this.name = input.getName();
        this.price = input.getPrice();
        this.createDate = LocalDateTime.now();
        this.active = true;
    }

    public void update(ProductInputDto input) {
        this.name = input.getName();
        this.price = input.getPrice();
    }

    public void markAsRemoved() {
        this.active = false;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
