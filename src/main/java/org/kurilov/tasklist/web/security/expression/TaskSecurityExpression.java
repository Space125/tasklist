package org.kurilov.tasklist.web.security.expression;

import lombok.RequiredArgsConstructor;
import org.kurilov.tasklist.service.UserService;
import org.kurilov.tasklist.web.security.JwtEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author Ivan Kurilov on 31.10.2023
 */

@Component("taskSecurityExpression")
@RequiredArgsConstructor
public class TaskSecurityExpression {

    private final UserService userService;

    public boolean canAccessTask(final Long taskId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtEntity user = (JwtEntity) authentication.getPrincipal();
        Long userId = user.getId();

        return userService.isTaskOwner(userId, taskId);
    }
}
