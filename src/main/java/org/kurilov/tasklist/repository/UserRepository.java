package org.kurilov.tasklist.repository;

import org.kurilov.tasklist.domain.user.Role;
import org.kurilov.tasklist.domain.user.User;

import java.util.Optional;

/**
 * @author Ivan Kurilov on 19.10.2023
 */
public interface UserRepository {
    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    void update(User user);

    void create(User user);

    void insertUserRole(Long userId, Role role);

    boolean isTaskOwner(Long userId, Long taskId);

    void delete(Long id);
}
