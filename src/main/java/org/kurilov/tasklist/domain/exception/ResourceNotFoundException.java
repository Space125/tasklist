package org.kurilov.tasklist.domain.exception;

/**
 * @author Ivan Kurilov on 19.10.2023
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
