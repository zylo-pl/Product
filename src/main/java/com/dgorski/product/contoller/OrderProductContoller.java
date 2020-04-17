package com.dgorski.product.contoller;

import com.dgorski.product.entity.OrderProduct;
import com.dgorski.product.input.OrderProductInputDto;
import com.dgorski.product.service.OrderProductService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController()
@RequestMapping("order")
@Api
public class OrderProductContoller {

    @Autowired
    OrderProductService orderProductService;

    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderProduct createOrderProduct(@RequestBody @Validated OrderProductInputDto input) {
        return orderProductService.createOrder(input);
    }

    @GetMapping(value = "", produces = "application/json")
    public List<OrderProduct> getOrderProductsBetween(@RequestParam(value = "from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                         @RequestParam(value = "to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return orderProductService.findOrdersBetween(from, to);
    }

}
