package org.kurilov.tasklist.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kurilov.tasklist.domain.exception.ResourceNotFoundException;
import org.kurilov.tasklist.domain.user.Role;
import org.kurilov.tasklist.domain.user.User;
import org.kurilov.tasklist.web.dto.auth.JwtRequest;
import org.kurilov.tasklist.web.dto.auth.JwtResponse;
import org.kurilov.tasklist.web.security.JwtTokenProvider;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Set;

/**
 * @author Ivan Kurilov on 08.11.2023
 */
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    AuthenticationManager authenticationManager = Mockito.mock(AuthenticationManager.class);

    UserServiceImpl userService = Mockito.mock(UserServiceImpl.class);

    JwtTokenProvider jwtTokenProvider = Mockito.mock(JwtTokenProvider.class);

    AuthServiceImpl authService = new AuthServiceImpl(authenticationManager, userService, jwtTokenProvider);

    @Test
    void login() {
        Long userId = 1L;
        String username = "username";
        String password = "password";
        Set<Role> roles = Collections.emptySet();
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";
        JwtRequest request = new JwtRequest();
        request.setUsername(username);
        request.setPassword(password);
        User user = new User();
        user.setId(userId);
        user.setUsername(username);
        user.setRoles(roles);
        Mockito.when(userService.getByUsername(username)).thenReturn(user);
        Mockito.when(jwtTokenProvider.createAccessToken(userId, username, roles)).thenReturn(accessToken);
        Mockito.when(jwtTokenProvider.createRefreshToken(userId, username)).thenReturn(refreshToken);
        JwtResponse response = authService.login(request);
        Assertions.assertEquals(username, response.getUsername());
        Assertions.assertEquals(userId, response.getId());
        Assertions.assertNotNull(response.getAccessToken());
        Assertions.assertNotNull(response.getRefreshToken());
        Mockito.verify(authenticationManager).authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
    }

    @Test
    void loginWithIncorrectUsername() {
        String username = "username";
        String password = "password";
        JwtRequest request = new JwtRequest();
        request.setUsername(username);
        request.setPassword(password);
        User user = new User();
        user.setUsername(username);
        Mockito.when(userService.getByUsername(username)).thenThrow(ResourceNotFoundException.class);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> authService.login(request));
        Mockito.verifyNoInteractions(jwtTokenProvider);
    }

    @Test
    void refresh() {
        String refreshToken = "refreshToken";
        String accessToken = "accessToken";
        String newRefreshToken = "newRefreshToken";
        JwtResponse expectedResponse = JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(newRefreshToken)
                .build();
        Mockito.when(jwtTokenProvider.refreshUserToken(refreshToken)).thenReturn(expectedResponse);
        JwtResponse actualResponse = authService.refresh(refreshToken);
        Assertions.assertEquals(expectedResponse, actualResponse);
        Mockito.verify(jwtTokenProvider).refreshUserToken(refreshToken);
    }
}
