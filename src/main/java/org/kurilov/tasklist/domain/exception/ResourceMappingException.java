package org.kurilov.tasklist.domain.exception;

/**
 * @author Ivan Kurilov on 19.10.2023
 */
public class ResourceMappingException extends RuntimeException {
    public ResourceMappingException(final String message) {
        super(message);
    }
}
