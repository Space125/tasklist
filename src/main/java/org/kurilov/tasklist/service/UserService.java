package org.kurilov.tasklist.service;

import org.kurilov.tasklist.domain.user.User;

/**
 * @author Ivan Kurilov on 19.10.2023
 */

public interface UserService {

    User getById(Long id);

    User getByUsername(String username);

    User update(User user);

    User create(User user);

    boolean isTaskOwner(Long userId, Long taskId);

    void delete(Long id);

}
