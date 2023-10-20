package org.kurilov.tasklist.service;

import org.kurilov.tasklist.domain.task.Task;

import java.util.List;

/**
 * @author Ivan Kurilov on 19.10.2023
 */
public interface TaskService {

    Task getById(Long id);

    List<Task> getAllByUserId(Long userId);

    Task update(Task task);

    Task create(Task task, Long userId);

    void delete(Long id);
}
