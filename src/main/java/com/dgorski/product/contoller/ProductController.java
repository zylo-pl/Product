package com.dgorski.product.contoller;

import com.dgorski.product.entity.Product;
import com.dgorski.product.input.ProductInputDto;
import com.dgorski.product.service.ProductService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("product")
@Api
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping(value = "{id}", produces = "application/json")
    public Product getProduct(@PathVariable(value = "id") Long id) {
        return productService.findProductById(id);
    }

    @GetMapping(produces = "application/json")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody @Validated ProductInputDto input) {
        return productService.createProduct(input);
    }

    @PutMapping(value = "{id}", consumes = "application/json", produces = "application/json")
    public Product updateProduct(@PathVariable(value = "id") Long id, @RequestBody @Validated ProductInputDto input) {
        return productService.updateProduct(id, input);
    }

    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void markAsRemoved(@PathVariable(value = "id") Long id) {
        productService.markAsRemoved(id);
    }
}
