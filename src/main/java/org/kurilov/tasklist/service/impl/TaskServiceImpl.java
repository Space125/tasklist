package org.kurilov.tasklist.service.impl;

import org.kurilov.tasklist.domain.task.Task;
import org.kurilov.tasklist.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ivan Kurilov on 19.10.2023
 */
@Service
public class TaskServiceImpl implements TaskService {

    @Override
    public Task getById(Long id) {
        return null;
    }

    @Override
    public List<Task> getAllByUserId(Long userId) {
        return null;
    }

    @Override
    public Task update(Task task) {
        return null;
    }

    @Override
    public Task create(Task task, Long userId) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
