package com.dgorski.product.input;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

public class OrderProductInputDto {

    @NotNull
    @NotEmpty
    private String buyerEmail;

    @NotNull
    private Set<Long> productIds;

    public OrderProductInputDto(String buyerEmail, Set<Long> productIds) {
        this.buyerEmail = buyerEmail;
        this.productIds = productIds;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public Set<Long> getProductIds() {
        return productIds;
    }
}
