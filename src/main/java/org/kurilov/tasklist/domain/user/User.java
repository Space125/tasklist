package org.kurilov.tasklist.domain.user;

import lombok.Data;
import org.kurilov.tasklist.domain.task.Task;

import java.util.List;
import java.util.Set;

/**
 * @author Ivan Kurilov on 19.10.2023
 */
@Data
public class User {

    private Long id;
    private String name;
    private String username;
    private String password;
    private String passwordConfirmation;
    private Set<Role> roles;
    private List<Task> tasks;

}
