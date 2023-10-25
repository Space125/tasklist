package org.kurilov.tasklist.repository.impl;

import lombok.RequiredArgsConstructor;
import org.kurilov.tasklist.domain.exception.ResourceMappingException;
import org.kurilov.tasklist.domain.task.Task;
import org.kurilov.tasklist.repository.DataSourceConfig;
import org.kurilov.tasklist.repository.TaskRepository;
import org.kurilov.tasklist.repository.mappers.TaskRowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Optional;

/**
 * @author Ivan Kurilov on 19.10.2023
 */
@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {

    private static final String FIND_BY_ID = """
            SELECT t.id              as task_id,
                   t.title           as task_title,
                   t.description     as task_description,
                   t.status          as task_status,
                   t.expiration_date as task_expiration_date
            FROM tasks t
            WHERE id = ?;
            """;
    private static final String FIND_ALL_BY_USER_ID = """
            SELECT t.id              as task_id,
                   t.title           as task_title,
                   t.description     as task_description,
                   t.status          as task_status,
                   t.expiration_date as task_expiration_date
            FROM tasks t
            JOIN users_tasks ut on t.id = ut.task_id
            WHERE ut.user_id = ?;
            """;
    private static final String ASSIGN_TO_USER_BY_ID = """
            INSERT INTO users_tasks (user_id, task_id) VALUES (?, ?);
            """;
    private static final String UPDATE = """
            UPDATE tasks SET title = ?,
                             description = ?,
                             status = ?,
                             expiration_date = ?
                         WHERE id = ?;
            """;
    private static final String CREATE = """
            INSERT INTO tasks (title, description, status, expiration_date) VALUES (?, ?, ?, ?);
            """;
    private static final String DELETE = """
            DELETE FROM tasks WHERE id = ?;
            """;
    private final DataSourceConfig dataSourceConfig;

    private static void buildingStatementForTask(final Task task, final PreparedStatement statement) throws SQLException {
        statement.setString(1, task.getTitle());
        if (task.getDescription() == null) {
            statement.setNull(2, Types.VARCHAR);
        } else {
            statement.setString(2, task.getDescription());
        }
        statement.setString(3, task.getStatus().name());
        if (task.getExpirationDate() == null) {
            statement.setNull(4, Types.TIMESTAMP);
        } else {
            statement.setTimestamp(4, Timestamp.valueOf(task.getExpirationDate()));
        }
    }

    @Override
    public Optional<Task> findById(final Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
            statement.setLong(1, id);
            try (ResultSet set = statement.executeQuery()) {
                return Optional.ofNullable(TaskRowMapper.mapRow(set));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException(String.format("Error while finding by task id=%d", id));
        }
    }

    @Override
    public List<Task> findAllByUserId(final Long userId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_USER_ID);
            statement.setLong(1, userId);
            try (ResultSet set = statement.executeQuery()) {
                return TaskRowMapper.mapRows(set);
            }
        } catch (SQLException e) {
            throw new ResourceMappingException(String.format("Error while finding all by user id=%d", userId));
        }
    }

    @Override
    public void assignToUserById(final Long taskId, final Long userId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(ASSIGN_TO_USER_BY_ID);
            statement.setLong(1, userId);
            statement.setLong(2, taskId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException(
                    String.format("Error while assign task=%d to user id=%d", taskId, userId)
            );
        }
    }

    @Override
    public void update(final Task task) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            buildingStatementForTask(task, statement);
            statement.setLong(5, task.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException(
                    String.format("Error while updating task=%d", task.getId())
            );
        }
    }

    @Override
    public void create(final Task task) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS);
            buildingStatementForTask(task, statement);
            statement.executeUpdate();
            try (ResultSet set = statement.getGeneratedKeys()) {
                set.next();
                task.setId(set.getLong(1));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while creating task");
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
            throw new ResourceMappingException(
                    String.format("Error while deleting task=%d", id)
            );
        }
    }
}
