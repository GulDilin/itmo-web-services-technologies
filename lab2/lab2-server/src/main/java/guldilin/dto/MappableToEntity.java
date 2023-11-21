package guldilin.dto;

import guldilin.entity.AbstractEntity;

public interface MappableToEntity {
    /**
     * Map instance to Entity object.
     *
     * @return Entity object
     */
    AbstractEntity mapToEntity();
}
