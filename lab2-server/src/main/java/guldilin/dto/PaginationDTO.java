package guldilin.dto;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginationDTO<T extends AbstractEntityDTO> implements Serializable {
    private Long total;
    private Long nextOffset;
    private List<T> items;
}
