package pl.holowinska.gpcproject.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.holowinska.gpcproject.domain.model.Product;
import pl.holowinska.gpcproject.ports.inbound.ProductService;
import pl.holowinska.gpcproject.ports.outbound.ProductRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Long countAllProducts() {
        return productRepository.countAllProducts();
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    @Override
    public Product getProductByName(String name) {
        Optional<Product> product = productRepository.getProductByName(name);
        if (product.isEmpty()) {
            throw new NoSuchElementException("A product with this name has not been found");
        }
        return product.get();
    }
}
