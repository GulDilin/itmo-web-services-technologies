package guldilin.dto;

import guldilin.entity.AbstractEntity;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@XmlSeeAlso({CityDTO.class})
public abstract class AbstractEntityDTO implements Serializable {
    private Integer id;
    private Date creationAt;
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
