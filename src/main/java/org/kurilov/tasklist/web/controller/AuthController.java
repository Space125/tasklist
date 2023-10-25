package org.kurilov.tasklist.web.controller;

import lombok.RequiredArgsConstructor;
import org.kurilov.tasklist.domain.user.User;
import org.kurilov.tasklist.service.AuthService;
import org.kurilov.tasklist.service.UserService;
import org.kurilov.tasklist.web.dto.auth.JwtRequest;
import org.kurilov.tasklist.web.dto.auth.JwtResponse;
import org.kurilov.tasklist.web.dto.user.UserDto;
import org.kurilov.tasklist.web.dto.validation.OnCreate;
import org.kurilov.tasklist.web.mappers.UserMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ivan Kurilov on 23.10.2023
 */

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    private final UserMapper userMapper;

    @PostMapping("/login")
    public JwtResponse login(@Validated @RequestBody final JwtRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public UserDto register(@Validated(OnCreate.class) @RequestBody final UserDto dto) {
        User user = userMapper.toEntity(dto);
        User createdUser = userService.create(user);
        return userMapper.toDto(createdUser);
    }

    @PostMapping("/refresh")
    public JwtResponse refresh(@RequestBody final String refreshToken) {
        return authService.refresh(refreshToken);
    }

}
