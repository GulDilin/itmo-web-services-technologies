package guldilin.exceptions;

import jakarta.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * Fault info about fields validation errors.
 */
@Data
@Builder
public class FieldValidationFault {
    /**
     * Map field - error.
     */
    private List<FieldValidationFaultEntry> errors;

    /**
     * Default constructor.
     */
    public FieldValidationFault() {
        this.errors = new ArrayList<>();
    }

    /**
     * Constructor with errors map.
     *
     * @param errors map field - error
     */
    public FieldValidationFault(final List<FieldValidationFaultEntry> errors) {
        this.errors = errors;
    }

    /**
     * Constructor from ConstraintViolationException.
     *
     * @param exception original validation exception
     */
    public FieldValidationFault(final ConstraintViolationException exception) {
        this();
        exception
                .getConstraintViolations()
                .forEach(c -> this.addFieldError(c.getPropertyPath().toString(), c.getMessage()));
    }

    /**
     * Add a field error.
     *
     * @param field field name
     * @param errorMessage error message
     * @return fault.
     */
    public FieldValidationFault addFieldError(final String field, final String errorMessage) {
        this.errors.add(FieldValidationFaultEntry.builder()
                .field(field)
                .message(errorMessage)
                .build());
        return this;
    }

    /**
     * Describes if errors list is empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
        return this.errors.isEmpty();
    }
}
