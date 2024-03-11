package guldilin.proxy.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Fault info for EntryNotFound exception.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntryNotFoundFault {
    /**
     * Entity name.
     */
    private String entity;
    /**
     * Identifier of entity that did not found.
     */
    private Integer id;
}
