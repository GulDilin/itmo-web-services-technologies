package guldilin.exceptions;

/**
 * Utility class with Error messages constants.
 */
public final class ErrorMessages {

    /**
     * Disable public constructor for Utility class.
     */
    private ErrorMessages() {
        // empty
    }

    /**
     * Not blank message.
     */
    public static final String NOT_BLANK = "Cannot be blank";
    /**
     * Not null message.
     */
    public static final String NOT_NULL = "Cannot be null";
    /**
     * Min value 0 message.
     */
    public static final String MIN_0 = "Min value is 0";
    /**
     * Max value 1000 message.
     */
    public static final String MAX_1000 = "Max value is 1000";
    /**
     * Field is not filterable message.
     */
    public static final String FIELD_NOT_FILTERABLE = "Field is not Filterable or does not exist";
    /**
     * Item does not found message.
     */
    public static final String NOT_FOUND = "Entry not found";
    /**
     * Missing required env variable message.
     */
    public static final String MISSING_REQUIRED_ENV = "Missing required env variable \"%s\"";
}
