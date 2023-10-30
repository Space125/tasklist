package org.kurilov.tasklist.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.kurilov.tasklist.domain.user.Role;
import org.kurilov.tasklist.domain.user.User;

import java.util.Optional;

/**
 * @author Ivan Kurilov on 19.10.2023
 */
@Mapper
public interface UserRepository {
    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    void update(User user);

    void create(User user);

    void insertUserRole(@Param("userId") Long userId, @Param("role") Role role);

    boolean isTaskOwner(@Param("userId") Long userId, @Param("taskId") Long taskId);

    void delete(Long id);
}
