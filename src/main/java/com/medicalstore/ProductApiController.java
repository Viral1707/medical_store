package com.medicalstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products/grouped")
public class ProductApiController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public Map<String, List<Product>> getProductsGroupedByCategory() {
        List<Product> products = productService.getAllProducts();
        return products.stream().collect(Collectors.groupingBy(Product::getCategory));
    }

    @GetMapping("/general")
    public Object getGeneralProducts() {
        List<Product> products = productService.getAllProducts();
        if (products == null || products.isEmpty()) {
            return Map.of("message", "No product selected");
        }
        return products;
    }
}