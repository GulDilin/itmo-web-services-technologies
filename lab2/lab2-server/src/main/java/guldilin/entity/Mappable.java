package guldilin.entity;

import guldilin.dto.AbstractEntityDTO;

public interface Mappable {
    /**
     * Map to DTO object.
     *
     * @return DTO object
     */
    AbstractEntityDTO mapToDTO();
}
