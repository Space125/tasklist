package org.kurilov.tasklist.repository;

import org.kurilov.tasklist.domain.task.Task;

import java.util.List;
import java.util.Optional;

/**
 * @author Ivan Kurilov on 19.10.2023
 */
public interface TaskRepository {
    Optional<Task> findById(Long id);

    List<Task> findAllByUserId(Long userId);

    void assignToUserById(Long taskId, Long userId);

    void update(Task task);

    void create(Task task);

    void delete(Long id);
}
