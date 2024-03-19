package guldilin.cli;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.NoArgsConstructor;

/**
 * Provides Validator object for Java Embedded server.
 */
@NoArgsConstructor
@ApplicationScoped
public class HibernateValidatorProvider {

    /**
     * Provider Validator object.
     *
     * @return validator instance
     */
    @Produces
    @Singleton
    public Validator provide() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            return factory.getValidator();
        }
    }
}
