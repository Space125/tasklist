package org.kurilov.tasklist.web.mappers;


import org.kurilov.tasklist.domain.user.User;
import org.kurilov.tasklist.web.dto.user.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Ivan Kurilov on 19.10.2023
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    User toEntity(UserDto dto);
}
