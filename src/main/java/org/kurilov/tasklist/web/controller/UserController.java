package org.kurilov.tasklist.web.controller;

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
public class UserController {

    private final UserService userService;
    private final TaskService taskService;

    private final UserMapper userMapper;
    private final TaskMapper taskMapper;

    @PutMapping
    public UserDto update(@Validated(OnUpdate.class) @RequestBody UserDto dto) {
        User user = userMapper.toEntity(dto);
        User updatedUser = userService.update(user);
        return userMapper.toDto(updatedUser);
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return userMapper.toDto(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
        ;
    }

    @GetMapping("/{id}/tasks")
    public List<TaskDto> getTaskByUserId(@PathVariable Long id) {
        List<Task> tasks = taskService.getAllByUserId(id);
        return taskMapper.toDto(tasks);
    }

    @PostMapping("/{id}/tasks")
    public TaskDto createTask(@Validated(OnCreate.class) @PathVariable Long id, @RequestBody TaskDto dto) {
        Task task = taskMapper.toEntity(dto);
        Task createdTask = taskService.create(task, id);
        return taskMapper.toDto(createdTask);
    }

}
