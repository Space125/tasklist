package org.kurilov.tasklist.web.controller;

import lombok.RequiredArgsConstructor;
import org.kurilov.tasklist.domain.task.Task;
import org.kurilov.tasklist.service.TaskService;
import org.kurilov.tasklist.web.dto.task.TaskDto;
import org.kurilov.tasklist.web.dto.validation.OnUpdate;
import org.kurilov.tasklist.web.mappers.TaskMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ivan Kurilov on 20.10.2023
 */
@RestController
@RequestMapping("/api/v1/tasks")
@Validated
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @GetMapping("/{id}")
    public TaskDto getById(@PathVariable Long id) {
        Task task = taskService.getById(id);
        return taskMapper.toDto(task);
    }

    @PutMapping
    public TaskDto update(@Validated(OnUpdate.class) @RequestBody TaskDto dto) {
        Task task = taskMapper.toEntity(dto);
        Task updatetdTask = taskService.update(task);
        return taskMapper.toDto(updatetdTask);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        taskService.delete(id);
    }
}
