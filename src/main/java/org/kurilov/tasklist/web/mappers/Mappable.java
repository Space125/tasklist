package org.kurilov.tasklist.web.mappers;

import java.util.List;

/**
 * @author Ivan Kurilov on 23.10.2023
 */
public interface Mappable<E, D> {
    D toDto(E entity);

    List<D> toDto(List<E> entities);

    E toEntity(D dto);
}
