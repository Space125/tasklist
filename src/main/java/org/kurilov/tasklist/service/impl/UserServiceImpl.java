package org.kurilov.tasklist.service.impl;

import org.kurilov.tasklist.domain.user.User;
import org.kurilov.tasklist.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author Ivan Kurilov on 19.10.2023
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public User getById(Long id) {
        return null;
    }

    @Override
    public User getByUsername(String username) {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public User create(User user) {
        return null;
    }

    @Override
    public boolean isTaskOwner(Long userId, Long taskId) {
        return false;
    }

    @Override
    public void delete(Long id) {

    }
}
