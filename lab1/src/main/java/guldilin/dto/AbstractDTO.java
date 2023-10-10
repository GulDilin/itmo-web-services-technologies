package guldilin.dto;

import guldilin.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public abstract class AbstractDTO {
    private Integer id;
    private Date creationAt;
    private Date updatedAt;

    public AbstractDTO(AbstractEntity entity) {
        this.creationAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }
}
