package guldilin.exceptions;

import jakarta.xml.ws.WebFault;
import lombok.Getter;

/**
 * Exception for entry not found.
 */
@WebFault(faultBean = "guldilin.exceptions.EntityNotFoundFault")
@Getter
public class EntryNotFound extends Exception {

    /**
     * Fault information.
     */
    private final EntryNotFoundFault faultInfo;

    /**
     * Constructor.
     *
     * @param message The error message
     * @param faultInfo additional info
     */
    public EntryNotFound(final String message, final EntryNotFoundFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * Constructor.
     *
     * @param message The error message
     * @param faultInfo additional info
     * @param cause exception cause
     */
    public EntryNotFound(final String message, final EntryNotFoundFault faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * Default constructor
     */
    public EntryNotFound() {
        super(ErrorMessages.NOT_FOUND);
        this.faultInfo = EntryNotFoundFault.builder().build();
    }

    /**
     * Constructor.
     *
     * @param faultInfo additional info
     */
    public EntryNotFound(final EntryNotFoundFault faultInfo) {
        super(ErrorMessages.NOT_FOUND);
        this.faultInfo = faultInfo;
    }

    /**
     * Constructor.
     *
     * @param entity entity name
     */
    public EntryNotFound(final String entity, final Integer id) {
        super(ErrorMessages.NOT_FOUND);
        this.faultInfo = EntryNotFoundFault.builder().entity(entity).id(id).build();
    }
}
