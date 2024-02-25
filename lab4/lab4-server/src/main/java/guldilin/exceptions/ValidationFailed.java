package guldilin.exceptions;

import jakarta.validation.ConstraintViolationException;
import lombok.Getter;

/**
 * Exception for validation.
 */
@Getter
public class ValidationFailed extends Exception {
    /**
     * Info about fields errors.
     */
    private final FieldValidationFault faultInfo;

    /**
     * Constructor.
     *
     * @param message The error message
     * @param faultInfo additional info
     */
    public ValidationFailed(final String message, final FieldValidationFault faultInfo) {
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
    public ValidationFailed(final String message, final FieldValidationFault faultInfo, final Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * Default constructor.
     */
    public ValidationFailed() {
        super(ErrorMessages.VALIDATION_FAILED);
        this.faultInfo = FieldValidationFault.builder().build();
    }

    /**
     * Constructor from ConstraintViolationException.
     *
     * @param exception original validation exception
     */
    public ValidationFailed(final ConstraintViolationException exception) {
        super(ErrorMessages.VALIDATION_FAILED);
        this.faultInfo = new FieldValidationFault(exception);
    }

    /**
     * Constructor.
     *
     * @param faultInfo additional info
     */
    public ValidationFailed(final FieldValidationFault faultInfo) {
        super(ErrorMessages.VALIDATION_FAILED);
        this.faultInfo = faultInfo;
    }
}
