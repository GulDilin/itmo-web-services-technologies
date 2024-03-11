package guldilin.exceptions;

import lombok.Builder;
import lombok.Data;

/**
 * Fault info for FieldIsNotFilterable exception.
 */
@Data
@Builder
public class FieldIsNotFilterableFault {
    /**
     * Name of field that is not filterable.
     */
    private String field;
}
