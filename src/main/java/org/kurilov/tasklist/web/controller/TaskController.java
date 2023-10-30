package org.kurilov.tasklist.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Task Controller", description = " Task API")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @GetMapping("/{id}")
    @Operation(summary = "Get Task by Id")
    public TaskDto getById(@PathVariable final Long id) {
        Task task = taskService.getById(id);
        return taskMapper.toDto(task);
    }

    @PutMapping
    @Operation(summary = "Update Task")
    public TaskDto update(@Validated(OnUpdate.class) @RequestBody final TaskDto dto) {
        Task task = taskMapper.toEntity(dto);
        Task updatetdTask = taskService.update(task);
        return taskMapper.toDto(updatetdTask);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Task")
    public void deleteById(@PathVariable final Long id) {
        taskService.delete(id);
    }
}
