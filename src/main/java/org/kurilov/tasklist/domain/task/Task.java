package org.kurilov.tasklist.domain.task;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Ivan Kurilov on 19.10.2023
 */
@Data
public class Task {

    private Long id;
    private String title;
    private String description;
    private Status status;
    private LocalDateTime expirationDate;

}
