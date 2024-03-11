package guldilin.proxy.api.dto;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for pagination.
 *
 * @param <T> Type that is paginated
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginationDTO<T extends AbstractEntityDTO> implements Serializable {
    /**
     * Number of all items.
     */
    private Long total;
    /**
     * Offset value for next part of items.
     */
    private Long nextOffset;
    /**
     * Part of items.
     */
    private List<T> items;
}
