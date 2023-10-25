package org.kurilov.tasklist.service.impl;

import lombok.RequiredArgsConstructor;
import org.kurilov.tasklist.domain.user.User;
import org.kurilov.tasklist.service.AuthService;
import org.kurilov.tasklist.service.UserService;
import org.kurilov.tasklist.web.dto.auth.JwtRequest;
import org.kurilov.tasklist.web.dto.auth.JwtResponse;
import org.kurilov.tasklist.web.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

/**
 * @author Ivan Kurilov on 19.10.2023
 */

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public JwtResponse login(final JwtRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        ));
        User user = userService.getByUsername(loginRequest.getUsername());
        return JwtResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .accessToken(jwtTokenProvider.createAccessToken(user.getId(), user.getUsername(), user.getRoles()))
                .refreshToken(jwtTokenProvider.createRefreshToken(user.getId(), user.getUsername()))
                .build();
    }

    @Override
    public JwtResponse refresh(final String refreshToken) {
        return jwtTokenProvider.refreshUserToken(refreshToken);
    }
}
