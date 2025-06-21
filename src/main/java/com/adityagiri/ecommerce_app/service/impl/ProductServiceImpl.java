package com.adityagiri.ecommerce_app.service.impl;

import com.adityagiri.ecommerce_app.dto.product.ProductCreateRequestDTO;
import com.adityagiri.ecommerce_app.dto.product.ProductResponseDTO;
import com.adityagiri.ecommerce_app.entity.Product;
import com.adityagiri.ecommerce_app.entity.User;
import com.adityagiri.ecommerce_app.repository.ProductRepository;
import com.adityagiri.ecommerce_app.repository.UserRepository;
import com.adityagiri.ecommerce_app.service.ProductService;

import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository){
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public ProductResponseDTO createProduct(ProductCreateRequestDTO productCreateRequestDTO){
        if(productCreateRequestDTO.getName()==null || productCreateRequestDTO.getDescription()==null || productCreateRequestDTO.getPrice()==null || productCreateRequestDTO.getStocks()==null || productCreateRequestDTO.getAvailable()==null || productCreateRequestDTO.getSellerId()==null){
            throw new RuntimeException("All fields are mandatory!");
        }

        User seller = userRepository.findById(productCreateRequestDTO.getSellerId())
                .orElseThrow(()-> new RuntimeException("Seller not found!"));

        Product product = new Product();
        product.setName(productCreateRequestDTO.getName());
        product.setDescription(productCreateRequestDTO.getName());
        product.setPrice(productCreateRequestDTO.getPrice());
        product.setStocks(productCreateRequestDTO.getStocks());
        product.setAvailable(productCreateRequestDTO.getAvailable());
        product.setSeller(seller);

        Product savedProduct = productRepository.save(product);
        return convertToProductResponseDTO(savedProduct);
    }

    public ProductResponseDTO getProductById(Long id){
        Product product = productRepository.findById(id).orElseThrow(()-> new RuntimeException("No product found with given id: " + id));

        return convertToProductResponseDTO(product);
    }

    public List<ProductResponseDTO> getAllProductsBySellerId(Long id){
        User seller = userRepository.findById(id).orElseThrow(()-> new RuntimeException("Seller not found!"));

        List<Product> products = productRepository.findBySellerId(id);
        List<ProductResponseDTO> finalProductsList = new ArrayList<>();

        for(Product p: products){
            finalProductsList.add(convertToProductResponseDTO(p));
        }

        return finalProductsList;
    }

    public List<ProductResponseDTO> getAllProducts(){
        List<Product> products = productRepository.findAll();
        List<ProductResponseDTO> finalProductsList = new ArrayList<>();

        for(Product p: products){
            finalProductsList.add(convertToProductResponseDTO(p));
        }

        return finalProductsList;
    }

    public List<ProductResponseDTO> searchProductsByName(String name){
        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
        List<ProductResponseDTO> finalProductsList = new ArrayList<>();

        for(Product p: products){
            finalProductsList.add(convertToProductResponseDTO(p));
        }

        return finalProductsList;
    }

    private ProductResponseDTO convertToProductResponseDTO(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStocks(),
                product.getAvailable(),
                product.getSeller().getId()
        );
    }
}
