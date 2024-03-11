package guldilin.proxy.api.dto;

import java.io.Serializable;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Error DTO.
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorDTO implements Serializable {
    /**
     * The error message.
     */
    private String message;
    /**
     * Additional error details.
     */
    private Map<String, Object> detail;
}
