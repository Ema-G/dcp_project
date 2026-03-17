package com.example.product;

import com.example.product.model.Product;
import com.example.product.repository.ProductRepository;
import com.example.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void getAllProducts_returnsAllProducts() {
        Product p = new Product("Laptop", "A laptop", new BigDecimal("999.99"), 10);
        when(productRepository.findAll()).thenReturn(List.of(p));

        List<Product> result = productService.getAllProducts();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Laptop");
    }

    @Test
    void getProductById_exists_returnsProduct() {
        Product p = new Product("Phone", "A phone", new BigDecimal("499.99"), 5);
        when(productRepository.findById(1L)).thenReturn(Optional.of(p));

        Optional<Product> result = productService.getProductById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Phone");
    }

    @Test
    void getProductById_notFound_returnsEmpty() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Product> result = productService.getProductById(99L);

        assertThat(result).isEmpty();
    }

    @Test
    void createProduct_savesAndReturnsProduct() {
        Product p = new Product("Tablet", "A tablet", new BigDecimal("299.99"), 20);
        when(productRepository.save(p)).thenReturn(p);

        Product result = productService.createProduct(p);

        assertThat(result.getName()).isEqualTo("Tablet");
        verify(productRepository, times(1)).save(p);
    }

    @Test
    void deleteProduct_exists_returnsTrue() {
        Product p = new Product("Watch", "A watch", new BigDecimal("199.99"), 3);
        when(productRepository.findById(1L)).thenReturn(Optional.of(p));

        boolean result = productService.deleteProduct(1L);

        assertThat(result).isTrue();
        verify(productRepository).delete(p);
    }

    @Test
    void deleteProduct_notFound_returnsFalse() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        boolean result = productService.deleteProduct(99L);

        assertThat(result).isFalse();
    }
}
