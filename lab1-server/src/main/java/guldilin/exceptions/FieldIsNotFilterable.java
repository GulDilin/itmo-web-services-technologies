package guldilin.exceptions;

/**
 * Exception for non-filterable fields.
 */
public class FieldIsNotFilterable extends Exception {
    /**
     * FieldIsNotFilterable constructor.
     *
     * @param message error message
     */
    public FieldIsNotFilterable(final String message) {
        super(message);
    }
}
