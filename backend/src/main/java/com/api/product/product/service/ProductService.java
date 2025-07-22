package com.api.product.product.service;

import com.api.product.entity.Product;
import com.api.product.entity.UserInfo;
import com.api.product.product.dto.ProductRequest;
import com.api.product.repository.ProductRepo;
import com.api.product.repository.UserInfoRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo productRepo;

    private final UserInfoRepo userInfoRepo;

    public Product create(ProductRequest request, Authentication authentication) {

        String username = authentication.getName();
        UserInfo userData = userInfoRepo.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));

        Product data = new Product();
        data.setName(request.getName());
        data.setDescription(request.getDescription());
        data.setPrice(request.getPrice());
        data.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        data.setCreatedBy(userData.getId());

        log.info("Creating product: {}", data.getName());

        return productRepo.save(data);
    }

    public List<Product> getAll() {
        return productRepo.findAll();
    }

    public Product getById(Integer id) {
        log.info("Get product by id: {}", id);
        return productRepo.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product update(Integer id, Product updatedProduct, Authentication authentication) {

        String username = authentication.getName();
        UserInfo userData = userInfoRepo.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));

        Product product = getById(id);

        log.info("Updating product: {}", product.getName());

        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        product.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        product.setUpdatedBy(userData.getId());



        return productRepo.save(product);
    }

    public void delete(Integer id) {
        log.info("Delete product by id: {}", id);
        productRepo.deleteById(id);
    }

    public List<Product> searchByName(String name) {
        return productRepo.findByNameContainingIgnoreCase(name);
    }

    public List<Product> filterByPrice(BigDecimal min, BigDecimal max) {
        return productRepo.findByPriceBetween(min, max);
    }
}
