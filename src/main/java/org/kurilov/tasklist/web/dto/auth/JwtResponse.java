package org.kurilov.tasklist.web.dto.auth;

import lombok.Data;

/**
 * @author Ivan Kurilov on 19.10.2023
 */
@Data
public class JwtResponse {

    private Long id;
    private String username;
    private String accessToken;
    private String refreshToken;

}
