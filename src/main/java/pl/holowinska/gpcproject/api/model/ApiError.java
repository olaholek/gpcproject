package pl.holowinska.gpcproject.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "ApiError")
public class ApiError {
    int status;
    String message;
    String description;
}
