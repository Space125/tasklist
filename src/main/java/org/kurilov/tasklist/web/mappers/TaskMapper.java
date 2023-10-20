package org.kurilov.tasklist.web.mappers;

import org.kurilov.tasklist.domain.task.Task;
import org.kurilov.tasklist.web.dto.task.TaskDto;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author Ivan Kurilov on 19.10.2023
 */

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskDto toDto(Task task);

    List<TaskDto> toDto(List<Task> tasks);

    Task toEntity(TaskDto dto);

}
