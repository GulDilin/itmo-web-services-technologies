package guldilin.dto;

import guldilin.entity.AbstractEntity;

public interface UpdatableToEntity<T extends AbstractEntity> {

    /**
     * Update entity with current data.
     *
     * @param entity entry to update
     */
    void updateEntity(T entity);
}
