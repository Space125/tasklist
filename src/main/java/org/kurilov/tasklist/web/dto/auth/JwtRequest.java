package org.kurilov.tasklist.web.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author Ivan Kurilov on 19.10.2023
 */
@Data
public class JwtRequest {

    @NotNull(message = "Username must be not null.")
    private String username;

    @NotNull(message = "Password must be not null.")
    private String password;

}
