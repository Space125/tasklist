package org.kurilov.tasklist.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kurilov.tasklist.domain.exception.ResourceNotFoundException;
import org.kurilov.tasklist.domain.user.Role;
import org.kurilov.tasklist.domain.user.User;
import org.kurilov.tasklist.repository.UserRepository;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.Set;

/**
 * @author Ivan Kurilov on 07.11.2023
 */
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    UserRepository userRepository = Mockito.mock(UserRepository.class);

    BCryptPasswordEncoder passwordEncoder = Mockito.mock(BCryptPasswordEncoder.class);

    UserServiceImpl userService = new UserServiceImpl(userRepository, passwordEncoder);

    @Test
    void getById() {
        Long id = 1L;
        User expectedUser = new User();
        expectedUser.setId(id);
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(expectedUser));
        User actualUser = userService.getById(id);
        Assertions.assertEquals(expectedUser, actualUser);
        Mockito.verify(userRepository).findById(id);
    }

    @Test
    void getByIdWithNotExistingId() {
        Long id = 1L;
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> userService.getById(id));
        Mockito.verify(userRepository).findById(id);
    }

    @Test
    void getByUsername() {
        String username = "username@gmail.com";
        User expectedUser = new User();
        expectedUser.setUsername(username);
        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(expectedUser));
        User actualUser = userService.getByUsername(username);
        Assertions.assertEquals(expectedUser, actualUser);
        Mockito.verify(userRepository).findByUsername(username);
    }

    @Test
    void getByUsernameNotExistingUsername() {
        String username = "username@gmail.com";
        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> userService.getByUsername(username));
        Mockito.verify(userRepository).findByUsername(username);
    }

    @Test
    void update() {
        Long id = 1L;
        String password = "password";
        User expectedUser = new User();
        expectedUser.setId(id);
        expectedUser.setPassword(password);
        Mockito.when(passwordEncoder.encode(password)).thenReturn("encodedPassword");
        Mockito.when(userRepository.findById(expectedUser.getId())).thenReturn(Optional.of(expectedUser));
        User actualUser = userService.update(expectedUser);
        Assertions.assertEquals(expectedUser.getName(), actualUser.getName());
        Assertions.assertEquals(expectedUser.getUsername(), actualUser.getUsername());
        Mockito.verify(passwordEncoder).encode(password);
        Mockito.verify(userRepository).update(expectedUser);
    }

    @Test
    void create() {
        String username = "uasername@gmail.com";
        String password = "password";
        User expectedUser = new User();
        expectedUser.setUsername(username);
        expectedUser.setPassword(password);
        expectedUser.setPasswordConfirmation(password);
        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        Mockito.when(passwordEncoder.encode(password)).thenReturn("encodedPassword");
        User actualUser = userService.create(expectedUser);
        Assertions.assertEquals(Set.of(Role.ROLE_USER), actualUser.getRoles());
        Assertions.assertEquals("encodedPassword", actualUser.getPassword());
        Mockito.verify(userRepository).create(expectedUser);
        Mockito.verify(userRepository).insertUserRole(actualUser.getId(), Role.ROLE_USER);
    }

    @Test
    void createExistingUser() {
        String username = "uasername@gmail.com";
        String password = "password";
        User expectedUser = new User();
        expectedUser.setUsername(username);
        expectedUser.setPassword(password);
        expectedUser.setPasswordConfirmation(password);
        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(expectedUser));
        Assertions.assertThrows(IllegalStateException.class, () -> userService.create(expectedUser));
        Mockito.verify(userRepository).findByUsername(username);
        Mockito.verify(userRepository, Mockito.never()).create(expectedUser);
    }

    @Test
    void createUserWithCheckPasswordConfirmation() {
        String username = "uasername@gmail.com";
        String password = "password";
        String passwordConfirmation = "password1";
        User expectedUser = new User();
        expectedUser.setUsername(username);
        expectedUser.setPassword(password);
        expectedUser.setPasswordConfirmation(passwordConfirmation);
        Assertions.assertThrows(IllegalStateException.class, () ->
                userService.create(expectedUser)
        );
        Mockito.verify(userRepository, Mockito.never()).create(expectedUser);
    }

    @Test
    void isTaskOwner() {
        Long userId = 1L;
        Long taskId = 1L;
        Mockito.when(userRepository.isTaskOwner(userId, taskId)).thenReturn(true);
        boolean isTaskOwner = userService.isTaskOwner(userId, taskId);
        Assertions.assertTrue(isTaskOwner);
        Mockito.verify(userRepository).isTaskOwner(userId, taskId);
    }

    @Test
    void isTaskOwnerFail() {
        Long userId = 1L;
        Long taskId = 1L;
        Mockito.when(userRepository.isTaskOwner(userId, taskId)).thenReturn(false);
        boolean isTaskOwner = userService.isTaskOwner(userId, taskId);
        Assertions.assertFalse(isTaskOwner);
        Mockito.verify(userRepository).isTaskOwner(userId, taskId);
    }

    @Test
    void delete() {
        Long id = 1L;
        userService.delete(id);
        Mockito.verify(userRepository).delete(id);
    }
}
