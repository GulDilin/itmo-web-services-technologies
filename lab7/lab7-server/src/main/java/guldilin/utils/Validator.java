package guldilin.utils;

import guldilin.exceptions.ErrorMessages;
import guldilin.exceptions.FieldValidationFault;
import guldilin.exceptions.ValidationFailed;
import jakarta.el.ExpressionFactory;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import java.util.Optional;

/**
 * Validator wrapper for jakarta validation.
 */
public final class Validator {
    /**
     * Empty constructor for util class.
     */
    private Validator() {
        // empty
    }
    /**
     * Validate jakarta constraints.
     *
     * @param o any object to validate (with jakarta validation annotations)
     * @throws ValidationFailed if not valid
     */
    public static void validate(final Object o) throws ValidationFailed {
        if (o == null) return;
        ExpressionFactory.newInstance();
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            var validator = factory.getValidator();
            var constraintViolations = validator.validate(o);
            var fault = new FieldValidationFault();
            constraintViolations.forEach(
                    c -> fault.addFieldError(c.getPropertyPath().toString(), c.getMessage()));
            if (!fault.isEmpty()) throw new ValidationFailed(fault);
        }
    }

    /**
     * Validate that specified field is not null.
     *
     * @param o value
     * @param fieldName name of field for ValidationFailed exception
     * @throws ValidationFailed if value is null
     */
    public static void validateNotNull(final Object o, final String fieldName) throws ValidationFailed {
        Optional.ofNullable(o)
                .orElseThrow(() -> new ValidationFailed(
                        new FieldValidationFault().addFieldError(fieldName, ErrorMessages.NOT_NULL)));
    }
}
