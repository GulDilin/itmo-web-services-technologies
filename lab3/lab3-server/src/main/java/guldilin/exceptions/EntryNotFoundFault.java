package guldilin.exceptions;

import lombok.Builder;
import lombok.Data;

/**
 * Fault info for EntryNotFound exception.
 */
@Data
@Builder
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
