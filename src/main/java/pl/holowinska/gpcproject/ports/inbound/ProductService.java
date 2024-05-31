package pl.holowinska.gpcproject.ports.inbound;

import pl.holowinska.gpcproject.domain.model.Product;

import java.util.List;

public interface ProductService {

    Long countAllProducts();

    List<Product> getAllProducts();

    Product getProductByName(String name);
}
