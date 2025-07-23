package com.api.product.product;


import com.api.product.entity.Product;
import com.api.product.product.controller.ProductController;
import com.api.product.product.dto.ProductRequest;
import com.api.product.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts() {
        List<Product> mockList = List.of(new Product(1, "Test", "Desc", new BigDecimal("10.0")));
        when(productService.getAll()).thenReturn(mockList);

        ResponseEntity<List<Product>> result = productController.getAll();

        List<Product> products = result.getBody();

        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals("Test", products.get(0).getName());
    }

    @Test
    void testCreateProduct() {
        ProductRequest request = new ProductRequest();
        request.setName("New Product");
        request.setDescription("New Desc");
        request.setPrice(new BigDecimal("99.99"));

        Product mockProduct = new Product(1, "New Product", "New Desc", new BigDecimal("99.99"));

        // Mock security context
        Authentication mockAuth = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(mockAuth);
        SecurityContextHolder.setContext(securityContext);

        // Mock service
        when(productService.create(eq(request), eq(mockAuth))).thenReturn(mockProduct);

        ResponseEntity<Product> response = productController.create(request);

        Product result = response.getBody();

        assertNotNull(result);
        assertEquals("New Product", result.getName());
    }
}