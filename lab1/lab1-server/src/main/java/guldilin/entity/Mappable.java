package guldilin.entity;

import guldilin.dto.AbstractEntityDTO;

/**
 * Annotation marks class as Mappable to DTO.
 */
public interface Mappable {
    /**
     * Map to DTO object.
     *
     * @return DTO object
     */
    AbstractEntityDTO mapToDTO();
}
