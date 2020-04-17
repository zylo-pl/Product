package com.dgorski.product.entity;

import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String buyerEmail;

    private LocalDateTime creationDate;

    private BigDecimal totalPrice;

    @ManyToMany
    private List<Product> products;

    public OrderProduct() {
    }

    public OrderProduct(String buyerEmail, List<Product> products) {
        this.buyerEmail = buyerEmail;
        this.products = products;
        this.creationDate = LocalDateTime.now();
        this.totalPrice = products.stream().map(product -> product.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public List<Product> getProducts() {
        return products;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderProduct that = (OrderProduct) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
