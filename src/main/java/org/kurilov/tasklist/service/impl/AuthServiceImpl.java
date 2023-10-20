package org.kurilov.tasklist.service.impl;

import org.kurilov.tasklist.service.AuthService;
import org.kurilov.tasklist.web.dto.auth.JwtRequest;
import org.kurilov.tasklist.web.dto.auth.JwtResponse;
import org.springframework.stereotype.Service;

/**
 * @author Ivan Kurilov on 19.10.2023
 */

@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public JwtResponse login(JwtRequest loginRequest) {
        return null;
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        return null;
    }
}
