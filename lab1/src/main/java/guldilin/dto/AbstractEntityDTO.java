package guldilin.dto;

import guldilin.entity.AbstractEntity;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractEntityDTO implements Serializable {
    private Integer id;
    private Date creationAt;
    private Date updatedAt;

    public AbstractEntityDTO(AbstractEntity entity) {
        this.creationAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }
}
