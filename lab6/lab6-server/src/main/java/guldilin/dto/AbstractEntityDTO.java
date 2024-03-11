package guldilin.dto;

import guldilin.entity.AbstractEntity;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Abstract Entity DTO class with default fields.
 * {@link AbstractEntity}
 */
@Data
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractEntityDTO implements Serializable {
    /**
     * DTO field shading an entity field with same name.
     */
    private Integer id;
    /**
     * DTO field shading an entity field with same name.
     */
    private Date creationAt;
    /**
     * DTO field shading an entity field with same name.
     */
    private Date updatedAt;

    /**
     * Constructor for DTO object from Entity.
     *
     * @param entity JPA entity.
     */
    public AbstractEntityDTO(final AbstractEntity entity) {
        this.id = entity.getId();
        this.creationAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }
}
