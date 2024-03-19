package guldilin.exceptions;

/**
 * Enumeration for error codes.
 */
public enum ErrorCode {
    /**
     * Resource not found.
     */
    RESOURCE_NOT_FOUND,
    /**
     * Method not allowed.
     */
    METHOD_NOT_ALLOWED,
    /**
     * Entity not found.
     */
    ENTITY_NOT_FOUND,
    /**
     * Unexpected server error.
     */
    INTERNAL_SERVER_ERROR,
    /**
     * Request is not valid.
     */
    REQUEST_INVALID,
    /**
     * Entity field is not filterable.
     */
    FIELD_IS_NOT_FILTERABLE,
    /**
     * Error during validation process.
     */
    VALIDATION_FAILED,
    /**
     * Error for failed auth.
     */
    NOT_AUTHORIZED,
}
