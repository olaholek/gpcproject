package pl.holowinska.gpcproject.adapters.rest.mappers;

import org.mapstruct.Mapper;
import pl.holowinska.gpcproject.api.model.ProductDTO;
import pl.holowinska.gpcproject.domain.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO mapToDTO(Product product);
}
