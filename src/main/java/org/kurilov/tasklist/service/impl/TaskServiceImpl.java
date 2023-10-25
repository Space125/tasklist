package org.kurilov.tasklist.service.impl;


import lombok.RequiredArgsConstructor;
import org.kurilov.tasklist.domain.exception.ResourceNotFoundException;
import org.kurilov.tasklist.domain.task.Status;
import org.kurilov.tasklist.domain.task.Task;
import org.kurilov.tasklist.repository.TaskRepository;
import org.kurilov.tasklist.service.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Ivan Kurilov on 19.10.2023
 */
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    @Transactional(readOnly = true)
    public Task getById(final Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Task with id: %d not found", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> getAllByUserId(final Long userId) {
        return taskRepository.findAllByUserId(userId);
    }

    @Override
    @Transactional
    public Task update(final Task task) {
        taskRepository.update(task);
        return task;
    }

    @Override
    @Transactional
    public Task create(final Task task, final Long userId) {
        if (task.getStatus() == null) {
            task.setStatus(Status.TODO);
        }
        taskRepository.create(task);
        taskRepository.assignToUserById(task.getId(), userId);
        return task;
    }

    @Override
    @Transactional
    public void delete(final Long id) {
        taskRepository.delete(id);
    }
}
