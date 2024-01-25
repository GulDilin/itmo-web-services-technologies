package guldilin.proxy.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Fault info for FieldIsNotFilterable exception.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldIsNotFilterableFault {
    /**
     * Name of field that is not filterable.
     */
    private String field;
}
