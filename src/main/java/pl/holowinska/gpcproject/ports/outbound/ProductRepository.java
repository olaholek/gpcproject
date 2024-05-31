package pl.holowinska.gpcproject.ports.outbound;

import pl.holowinska.gpcproject.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Long countAllProducts();

    List<Product> getAllProducts();

    Optional<Product> getProductByName(String name);
}
