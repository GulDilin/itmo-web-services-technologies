package guldilin.utils;

import guldilin.exceptions.ErrorMessages;
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
     * @throws Exception if not valid
     */
    public static void validate(final Object o) throws Exception {
        if (o == null) return;
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            var validator = factory.getValidator();
            var constraintViolations = validator.validate(o);
            if (!constraintViolations.isEmpty()) throw new Exception("Validation failed");
        }
    }

    /**
     * Validate that specified field is not null.
     *
     * @param o value
     * @param fieldName name of field for ValidationFailed exception
     * @throws Exception if value is null
     */
    public static void validateNotNull(final Object o, final String fieldName) throws Exception {
        Optional.ofNullable(o)
                .orElseThrow(() -> new Exception(String.format("%s error: %s", fieldName, ErrorMessages.NOT_NULL)));
    }
}
