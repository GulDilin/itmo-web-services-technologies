package guldilin.exceptions;

/**
 * Exception for entry not found.
 */
public class EntryNotFound extends Exception {
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
    public EntryNotFound(final String m) {
        super(m);
    }
}
