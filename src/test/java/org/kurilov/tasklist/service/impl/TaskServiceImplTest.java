package org.kurilov.tasklist.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kurilov.tasklist.domain.exception.ResourceNotFoundException;
import org.kurilov.tasklist.domain.task.Status;
import org.kurilov.tasklist.domain.task.Task;
import org.kurilov.tasklist.repository.TaskRepository;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Ivan Kurilov on 03.11.2023
 */

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    TaskRepository taskRepository = Mockito.mock(TaskRepository.class);

    TaskServiceImpl taskService = new TaskServiceImpl(taskRepository);

    @Test
    void getById() {
        Long id = 1L;
        Task expectedTask = new Task();
        expectedTask.setId(id);
        Mockito.when(taskRepository.findById(id))
                .thenReturn(Optional.of(expectedTask));
        Task actualTask = taskService.getById(id);
        Assertions.assertEquals(expectedTask, actualTask);
        Mockito.verify(taskRepository).findById(id);
    }

    @Test
    void getByIdWithNotExistingId() {
        Long id = 1L;
        Mockito.when(taskRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> taskService.getById(id));
        Mockito.verify(taskRepository).findById(id);
    }

    @Test
    void getAllByUserId() {
        Long userId = 1L;
        List<Task> expectedTasks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            expectedTasks.add(new Task());
        }
        Mockito.when(taskRepository.findAllByUserId(userId))
                .thenReturn(expectedTasks);
        List<Task> actualTasks = taskService.getAllByUserId(userId);
        Assertions.assertEquals(expectedTasks, actualTasks);
        Mockito.verify(taskRepository).findAllByUserId(userId);
    }

    @Test
    void update() {
        Task expectedTask = new Task();
        expectedTask.setId(1L);
        expectedTask.setTitle("title");
        expectedTask.setDescription("description");
        expectedTask.setStatus(Status.IN_PROGRESS);
        Mockito.when(taskRepository.findById(expectedTask.getId())).thenReturn(Optional.of(expectedTask));
        Task actualTask = taskService.update(expectedTask);
        Assertions.assertEquals(expectedTask, actualTask);
        Mockito.verify(taskRepository).update(expectedTask);
    }

    @Test
    void create() {
        Long userId = 1L;
        Long taskId = 1L;
        Task task = new Task();
        Mockito.doAnswer(invocation -> {
            Task savedTask = invocation.getArgument(0);
            savedTask.setId(taskId);
            return savedTask;
        }).when(taskRepository).create(task);
        Task actualTask = taskService.create(task, userId);
        Mockito.verify(taskRepository).create(task);
        Assertions.assertNotNull(actualTask);
        Assertions.assertEquals(Status.TODO, actualTask.getStatus());
        Mockito.verify(taskRepository).assignToUserById(actualTask.getId(), userId);
    }

    @Test
    void delete() {
        Long id = 1L;
        Assertions.assertThrows(ResourceNotFoundException.class, () -> taskService.delete(id));
        Mockito.verify(taskRepository).delete(id);
    }
}
