package pl.holowinska.gpcproject.adapters.rest.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.holowinska.gpcproject.adapters.rest.mappers.ProductMapper;
import pl.holowinska.gpcproject.api.ProductApi;
import pl.holowinska.gpcproject.api.model.ProductDTO;
import pl.holowinska.gpcproject.domain.model.Product;
import pl.holowinska.gpcproject.ports.inbound.ProductService;

import java.util.List;

@RestController
@AllArgsConstructor
public class ProductApiImpl implements ProductApi {

    private final ProductService productService;
    private final ProductMapper productMapper;


    @Override
    public ResponseEntity<Long> countAllProducts() {
        return ResponseEntity.ok(productService.countAllProducts());
    }

    @Override
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts()
                .stream()
                .map(productMapper::mapToDTO)
                .toList());
    }

    @Override
    public ResponseEntity<ProductDTO> getProductByName(String name) {
        Product product = productService.getProductByName(name);
        return ResponseEntity.ok(productMapper.mapToDTO(product));
    }
}
