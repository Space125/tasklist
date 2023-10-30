package org.kurilov.tasklist.web.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author Ivan Kurilov on 19.10.2023
 */
@Data
@Schema(description = "JWT request")
public class JwtRequest {

    @Schema(description = "User email", example = "jd@gmail.com")
    @NotNull(message = "Username must be not null.")
    private String username;

    @Schema(description = "User password", example = "Test54123")
    @NotNull(message = "Password must be not null.")
    private String password;

}
