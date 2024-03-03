package guldilin.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for filtering argument.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilterArgumentDTO implements Serializable {
    /**
     * Name of filtered field.
     */
    private String field;
    /**
     * Target filtering field value.
     */
    private String value;
}
