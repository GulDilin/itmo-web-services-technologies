package guldilin.entity;

import guldilin.dto.AbstractEntityDTO;

/**
 * Maks class that can be converted to DTO.
 */
public interface MappableToDTO {
    /**
     * Map to DTO object.
     *
     * @return DTO object
     */
    AbstractEntityDTO mapToDTO();
}
