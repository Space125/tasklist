package org.kurilov.tasklist.repository.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kurilov.tasklist.domain.exception.ResourceMappingException;
import org.kurilov.tasklist.domain.user.Role;
import org.kurilov.tasklist.domain.user.User;
import org.kurilov.tasklist.repository.DataSourceConfig;
import org.kurilov.tasklist.repository.UserRepository;
import org.kurilov.tasklist.repository.mappers.UserRowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * @author Ivan Kurilov on 19.10.2023
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository {

    private static final String FIND_BY_ID = """
            SELECT u.id       as user_id,
                   u.name     as user_name,
                   u.username as user_username,
                   u.password as user_password,
                   ur.role as user_role_role,
                   t.id as task_id,
                   t.title as task_title,
                   t.description as task_description,
                   t.status as task_status,
                   t.expiration_date as task_expiration_date
            FROM users u
                   LEFT JOIN users_roles ur on u.id = ur.user_id
                   LEFT JOIN users_tasks ut on u.id = ut.user_id
                   LEFT JOIN tasks t on t.id = ut.task_id
            WHERE u.id = ?;
            """;
    private static final String FIND_BY_USERNAME = """
            SELECT u.id       as user_id,
                   u.name     as user_name,
                   u.username as user_username,
                   u.password as user_password,
                   ur.role as user_role_role,
                   t.id as task_id,
                   t.title as task_title,
                   t.description as task_description,
                   t.status as task_status,
                   t.expiration_date as task_expiration_date
            FROM users u
                   LEFT JOIN users_roles ur on u.id = ur.user_id
                   LEFT JOIN users_tasks ut on u.id = ut.user_id
                   LEFT JOIN tasks t on t.id = ut.task_id
            WHERE u.username = ?;""";
    private static final String UPDATE = """
            UPDATE users SET name = ?,
                             username = ?,
                             password = ?
                         WHERE id = ?;""";
    private static final String CREATE = """
            INSERT INTO users (name, username, password)
            VALUES (?, ?, ?)""";
    private static final String INSERT_USER_ROLE = """
            INSERT INTO users_roles (user_id, role)
            VALUES (?, ?)""";
    private static final String IS_TASK_OWNER = """
            SELECT exists(
                SELECT 1
                FROM users_tasks
                WHERE user_id = ?
                AND task_id = ?
            );""";
    private static final String DELETE = """
            delete from users where id = ?;""";
    private final DataSourceConfig dataSourceConfig;

    @Override
    public Optional<User> findById(final Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setLong(1, id);
            try (ResultSet set = statement.executeQuery()) {
                return Optional.ofNullable(UserRowMapper.mapRow(set));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new ResourceMappingException(
                    String.format("Error while finding user by id=%d", id)
            );
        }
    }

    @Override
    public Optional<User> findByUsername(final String username) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_USERNAME,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setString(1, username);
            try (ResultSet set = statement.executeQuery()) {
                return Optional.ofNullable(UserRowMapper.mapRow(set));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new ResourceMappingException(
                    String.format("Error while finding user by username=%S", username)
            );
        }
    }

    @Override
    public void update(final User user) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, user.getName());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setLong(4, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new ResourceMappingException(
                    String.format("Error while updating user id=%d", user.getId())
            );
        }
    }

    @Override
    public void create(final User user) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();
            try (ResultSet set = statement.getGeneratedKeys()) {
                set.next();
                user.setId(set.getLong(1));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new ResourceMappingException(
                    String.format("Error while creating user name=%s", user.getName())
            );
        }
    }

    @Override
    public void insertUserRole(final Long userId, final Role role) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_USER_ROLE);
            statement.setLong(1, userId);
            statement.setString(2, role.name());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new ResourceMappingException(
                    String.format("Error while inserting user id=%d role=%s", userId, role.name())
            );
        }

    }

    @Override
    public boolean isTaskOwner(final Long userId, final Long taskId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(IS_TASK_OWNER);
            statement.setLong(1, userId);
            statement.setLong(2, taskId);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                return rs.getBoolean(1);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new ResourceMappingException(
                    String.format("Error while checking if user id=%d owner is task id=%d", userId, taskId)
            );
        }
    }

    @Override
    public void delete(final Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new ResourceMappingException(
                    String.format("Error while deleting user=%d", id)
            );
        }
    }
}
