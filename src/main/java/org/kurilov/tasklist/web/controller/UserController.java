package org.kurilov.tasklist.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.kurilov.tasklist.domain.task.Task;
import org.kurilov.tasklist.domain.user.User;
import org.kurilov.tasklist.service.TaskService;
import org.kurilov.tasklist.service.UserService;
import org.kurilov.tasklist.web.dto.task.TaskDto;
import org.kurilov.tasklist.web.dto.user.UserDto;
import org.kurilov.tasklist.web.dto.validation.OnCreate;
import org.kurilov.tasklist.web.dto.validation.OnUpdate;
import org.kurilov.tasklist.web.mappers.TaskMapper;
import org.kurilov.tasklist.web.mappers.UserMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ivan Kurilov on 20.10.2023
 */
@RestController
@RequestMapping("/api/v1/users")
@Validated
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "User API")
public class UserController {

    private final UserService userService;
    private final TaskService taskService;

    private final UserMapper userMapper;
    private final TaskMapper taskMapper;

    @PutMapping
    @Operation(summary = "Update User")
    @PreAuthorize("@userSecurityExpression.canAccessUser(#dto.id)")
    public UserDto update(@Validated(OnUpdate.class) @RequestBody final UserDto dto) {
        User user = userMapper.toEntity(dto);
        User updatedUser = userService.update(user);
        return userMapper.toDto(updatedUser);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get UserDto by Id")
    @PreAuthorize("@userSecurityExpression.canAccessUser(#id)")
    public UserDto getById(@PathVariable final Long id) {
        User user = userService.getById(id);
        return userMapper.toDto(user);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete User")
    @PreAuthorize("@userSecurityExpression.canAccessUser(#id)")
    public void delete(@PathVariable final Long id) {
        userService.delete(id);
    }

    @GetMapping("/{id}/tasks")
    @Operation(summary = "Get all User Tasks")
    @PreAuthorize("@userSecurityExpression.canAccessUser(#id)")
    public List<TaskDto> getTaskByUserId(@PathVariable final Long id) {
        List<Task> tasks = taskService.getAllByUserId(id);
        return taskMapper.toDto(tasks);
    }

    @PostMapping("/{id}/tasks")
    @Operation(summary = "Add task to User")
    @PreAuthorize("@userSecurityExpression.canAccessUser(#id)")
    public TaskDto createTask(@Validated(OnCreate.class)
                              @PathVariable final Long id,
                              @RequestBody final TaskDto dto) {
        Task task = taskMapper.toEntity(dto);
        Task createdTask = taskService.create(task, id);
        return taskMapper.toDto(createdTask);
    }
}
