package guldilin.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginationRequestDTO implements Serializable {
    public static final Integer DEFAULT_LIMIT = 10;
    public static final Integer MAX_LIMIT = 100;

    @Min(0)
    @Max(PaginationRequestDTO.MAX_LIMIT)
    private Integer limit = DEFAULT_LIMIT;

    @Min(0)
    private Integer offset = 0;
}
