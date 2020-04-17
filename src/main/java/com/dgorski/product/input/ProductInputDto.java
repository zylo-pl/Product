package com.dgorski.product.input;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ProductInputDto {

    @NotNull
    @NotEmpty
    private final String name;

    @NotNull
    @DecimalMin(value = "0.0")
    private final BigDecimal price;

    public ProductInputDto(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
