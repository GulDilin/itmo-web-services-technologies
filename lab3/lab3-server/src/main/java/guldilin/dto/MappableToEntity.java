package guldilin.dto;

import guldilin.entity.AbstractEntity;

/**
 * Interface marks that class can be converted to database entity.
 */
public interface MappableToEntity {
    /**
     * Map instance to Entity object.
     *
     * @return Entity object
     */
    AbstractEntity mapToEntity();
}
