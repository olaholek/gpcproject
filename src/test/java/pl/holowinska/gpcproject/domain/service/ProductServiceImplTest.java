package pl.holowinska.gpcproject.domain.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.holowinska.gpcproject.domain.model.Product;
import pl.holowinska.gpcproject.ports.inbound.ProductService;
import pl.holowinska.gpcproject.ports.outbound.ProductRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ProductServiceImplTest {

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Test
    public void shouldReturnProductCount() {
        //when
        when(productRepository.countAllProducts()).thenReturn(4L);

        //then
        assertEquals(4L, productService.countAllProducts());
    }

    @Test
    public void shouldReturnAllProductsWhenProductsExist() {
        //given
        List<Product> mockProducts = Arrays.asList(new Product(), new Product());

        //when
        when(productRepository.getAllProducts()).thenReturn(mockProducts);

        //then
        assertEquals(mockProducts, productService.getAllProducts());
    }

    @Test
    public void shouldReturnEmptyListWhenNoProductsExist() {
        //when
        when(productRepository.getAllProducts()).thenReturn(Collections.emptyList());

        //then
        assertEquals(0, productService.getAllProducts().size());
    }

    @Test
    public void shouldReturnProductByNameWhenProductExists() {
        //given
        Product mockProduct = new Product();
        mockProduct.setName("exampleName");

        //when
        when(productRepository.getProductByName("exampleName")).thenReturn(Optional.of(mockProduct));

        //then
        assertEquals(mockProduct, productService.getProductByName("exampleName"));
    }

    @Test
    public void shouldThrowExceptionWhenProductDoesNotExists() {
        //when
        when(productRepository.getProductByName("nonexistent")).thenReturn(Optional.empty());

        //then
        assertThrows(NoSuchElementException.class, () -> {
            productService.getProductByName("nonexistent");
        });
    }
}