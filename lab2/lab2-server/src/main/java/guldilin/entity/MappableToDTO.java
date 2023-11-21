package guldilin.entity;

import guldilin.dto.AbstractEntityDTO;

public interface MappableToDTO {
    /**
     * Map to DTO object.
     *
     * @return DTO object
     */
    AbstractEntityDTO mapToDTO();
}
