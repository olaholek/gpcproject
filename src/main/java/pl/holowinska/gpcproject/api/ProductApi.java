package pl.holowinska.gpcproject.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.holowinska.gpcproject.api.model.ApiError;
import pl.holowinska.gpcproject.api.model.ProductDTO;

import java.util.List;

@RequestMapping("/products")
@Tag(name = "Products")
public interface ProductApi {

    @Operation(description = "Get number of products", responses = {
            @ApiResponse(
                    responseCode = "200",
                    useReturnTypeSchema = true
            )
    })
    @GetMapping("/count")
    ResponseEntity<Long> countAllProducts();

    @Operation(description = "Get all products", responses = {
            @ApiResponse(
                    responseCode = "200",
                    useReturnTypeSchema = true
            )
    })
    @GetMapping
    ResponseEntity<List<ProductDTO>> getAllProducts();

    @Operation(description = "Get product by name", responses = {
            @ApiResponse(
                    responseCode = "200",
                    useReturnTypeSchema = true
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ApiError.class))
            )
    })
    @GetMapping("/{name}")
    ResponseEntity<ProductDTO> getProductByName(
            @PathVariable(name = "name") String name
    );
}
