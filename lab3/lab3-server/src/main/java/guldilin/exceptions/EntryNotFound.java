package guldilin.exceptions;

/**
 * Exception for entry not found.
 */
@WebFault(faultBean=guldilin.exceptions.CityServiceFault)
public class EntryNotFound extends Exception {

    private final CityServiceFault fault;
    /**
     * Default constructor.
     */
    public EntryNotFound() {
        super(ErrorMessages.NOT_FOUND);
    }

    /**
     * Message constructor.
     * @param m message
     */
    public EntryNotFound(final String m, CityServiceFault fault) {
        super(m);
    }
}
