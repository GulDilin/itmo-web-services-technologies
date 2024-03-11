package guldilin.proxy.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Fault info about fields validation errors.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldValidationFaultEntry {
    /**
     * Field name.
     */
    private String field;

    /**
     * Error message.
     */
    private String message;
}
