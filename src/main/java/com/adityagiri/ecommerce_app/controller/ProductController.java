package com.adityagiri.ecommerce_app.controller;

import com.adityagiri.ecommerce_app.dto.product.ProductCreateRequestDTO;
import com.adityagiri.ecommerce_app.dto.product.ProductResponseDTO;
import com.adityagiri.ecommerce_app.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping("/create")
    public ProductResponseDTO createProduct(@RequestBody ProductCreateRequestDTO productCreateRequestDTO){
        return productService.createProduct(productCreateRequestDTO);
    }

    @GetMapping("/get/{id}")
    public ProductResponseDTO getProductById(@PathVariable Long id){
        return productService.getProductById(id);
    }

    @GetMapping("/get/seller/{id}")
    public List<ProductResponseDTO> getAllProductsBySellerId(@PathVariable Long id){
        return productService.getAllProductsBySellerId(id);
    }

    @GetMapping("/get/all")
    public List<ProductResponseDTO> getAllProducts(){
        return productService.getAllProducts();
    }

//    GET /search?name=iphone
    @GetMapping("/search")
    public List<ProductResponseDTO> searchProductsByName(@RequestParam String name){
        return productService.searchProductsByName(name);
    }
}
