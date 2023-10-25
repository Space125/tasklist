package org.kurilov.tasklist.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 * @author Ivan Kurilov on 24.10.2023
 */
@Data
@AllArgsConstructor
public class ExceptionBody {

    private String message;
    private Map<String, String> errors;

    public ExceptionBody(final String message) {
        this.message = message;
    }
}
