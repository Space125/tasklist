package org.kurilov.tasklist.service;

import org.kurilov.tasklist.web.dto.auth.JwtRequest;
import org.kurilov.tasklist.web.dto.auth.JwtResponse;

/**
 * @author Ivan Kurilov on 19.10.2023
 */
public interface AuthService {
    JwtResponse login(JwtRequest loginRequest);

    JwtResponse refresh(String refreshToken);

}
