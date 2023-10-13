package guldilin.dto;

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
    @Min(0)
    private Long limit;

    @Min(0)
    private Long offset;
}
