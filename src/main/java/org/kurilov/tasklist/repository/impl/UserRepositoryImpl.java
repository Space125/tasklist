package org.kurilov.tasklist.repository.impl;

import org.kurilov.tasklist.domain.user.Role;
import org.kurilov.tasklist.domain.user.User;
import org.kurilov.tasklist.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Ivan Kurilov on 19.10.2023
 */
@Repository
public class UserRepositoryImpl implements UserRepository {
    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void create(User user) {

    }

    @Override
    public void insertUserRole(Long userId, Role role) {

    }

    @Override
    public boolean isTaskOwner(Long userId, Long taskId) {
        return false;
    }

    @Override
    public void delete(Long id) {

    }
}
