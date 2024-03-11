package guldilin.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO for providing pagination data to request.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PaginationRequestDTO implements Serializable {
    /**
     * Default part of items limit.
     */
    public static final int DEFAULT_LIMIT = 10;
    /**
     * Default offset of items list.
     */
    public static final int DEFAULT_OFFSET = 0;
    /**
     * Max value for part of values limit.
     */
    public static final int MAX_LIMIT = 100;

    /**
     * Limit for number of items in part.
     */
    @Min(0)
    @Max(PaginationRequestDTO.MAX_LIMIT)
    private Integer limit = DEFAULT_LIMIT;

    /**
     * Offset for first element of items.
     */
    @Min(0)
    private Integer offset = DEFAULT_OFFSET;
}
