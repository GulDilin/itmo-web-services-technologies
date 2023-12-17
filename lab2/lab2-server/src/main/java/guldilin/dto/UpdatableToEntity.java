package guldilin.dto;

import guldilin.entity.AbstractEntity;

/**
 * Interface to mark class that can be used to update and patch realization.
 *
 * @param <T> Database Entity
 */
public interface UpdatableToEntity<T extends AbstractEntity> {

    /**
     * Update entity with current data.
     *
     * @param entity entry to update
     */
    void updateEntity(T entity);

    /**
     * Path entity with current data for non-null fields.
     *
     * @param entity entry to patch
     */
    void patchEntity(T entity);
}
