package org.kurilov.tasklist.service.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Ivan Kurilov on 20.10.2023
 */

@Component
@Data
@ConfigurationProperties(prefix = "security.jwt")
public class JwtProperties {

    private String secretKey;
    private Long accessExpiration;
    private Long refreshExpiration;
}
