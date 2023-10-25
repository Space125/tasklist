package org.kurilov.tasklist.repository.mappers;

import lombok.SneakyThrows;
import org.kurilov.tasklist.domain.task.Status;
import org.kurilov.tasklist.domain.task.Task;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Kurilov on 23.10.2023
 */
public class TaskRowMapper {

    @SneakyThrows
    public static Task mapRow(final ResultSet resultSet) {
        if (resultSet.next()) {
            Task task = new Task();
            task.setId(resultSet.getLong("task_id"));
            constructorTask(resultSet, task);
            return task;
        }
        return null;
    }

    @SneakyThrows
    public static List<Task> mapRows(final ResultSet resultSet) {
        List<Task> tasks = new ArrayList<>();
        while (resultSet.next()) {
            Task task = new Task();
            task.setId(resultSet.getLong("task_id"));
            if (!resultSet.wasNull()) {
                constructorTask(resultSet, task);
                tasks.add(task);
            }
        }
        return tasks;
    }

    private static void constructorTask(final ResultSet resultSet, final Task task) throws SQLException {
        task.setTitle(resultSet.getString("task_title"));
        task.setDescription(resultSet.getString("task_description"));
        task.setStatus(Status.valueOf(resultSet.getString("task_status")));
        Timestamp timestamp = resultSet.getTimestamp("task_expiration_date");
        if (timestamp != null) {
            task.setExpirationDate(timestamp.toLocalDateTime());
        }
    }
}
