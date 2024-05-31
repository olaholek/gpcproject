package pl.holowinska.gpcproject.adapters.rest;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.holowinska.gpcproject.GpcprojectApplication;
import pl.holowinska.gpcproject.domain.model.Product;
import pl.holowinska.gpcproject.ports.inbound.ProductService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = GpcprojectApplication.class)
@AutoConfigureMockMvc
class ProductApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;


    @Test
    public void shouldReturnProductCount() throws Exception {
        //when
        when(productService.countAllProducts()).thenReturn(3L);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/products/count"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("3"));
    }

    @Test
    public void shouldReturnAllProductsWhenProductsExist() throws Exception {
        //given
        List<Product> mockProducts = Arrays.asList(new Product(), new Product());

        //when
        when(productService.getAllProducts()).thenReturn(mockProducts);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", equalTo(mockProducts.size())));
    }


    @Test
    public void shouldReturnEmptyListWhenNoProductsExist() throws Exception {
        //when
        when(productService.getAllProducts()).thenReturn(Collections.emptyList());

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", equalTo(0)));
    }

    @Test
    public void shouldReturnProductWhenProductExists() throws Exception {
        //given
        Product mockProduct = new Product();
        mockProduct.setName("exampleName");

        //when
        when(productService.getProductByName("exampleName")).thenReturn(mockProduct);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/products/{name}", "exampleName"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", equalTo("exampleName")));
    }

    @Test
    public void shouldReturnNotFoundWhenProductDoesNotExist() throws Exception {
        //when
        when(productService.getProductByName("nonexistent")).thenThrow(NoSuchElementException.class);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/products/{name}", "nonexistent"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}