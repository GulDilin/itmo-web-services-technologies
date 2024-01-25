package guldilin.commands.common;

import guldilin.proxy.api.EntryNotFound;
import guldilin.proxy.api.FieldIsNotFilterable;
import guldilin.proxy.api.ValidationFailed;

/**
 * Exception handler utility class.
 */
public final class ExceptionHandler {

    private ExceptionHandler() {
        // empty constructor for utility class
    }
    /**
     * Handle exception and call specified handlers.
     *
     * @param e an exception
     */
    public static void handleException(final Exception e) {
        if (e instanceof EntryNotFound) handleEntryNotFound((EntryNotFound) e);
        else if (e instanceof ValidationFailed) handleValidationFailed((ValidationFailed) e);
        else if (e instanceof FieldIsNotFilterable) handleFieldIsNotFilterable((FieldIsNotFilterable) e);
        else handleDefault(e);
    }

    /**
     * Handler for EntryNotFound.
     *
     * @param e an exception
     */
    public static void handleEntryNotFound(final EntryNotFound e) {
        System.err.printf(
                "ERROR: %s for entity %s with id %d\n",
                e.getMessage(), e.getFaultInfo().getEntity(), e.getFaultInfo().getId());
    }
    /**
     * Handler for ValidationFailed.
     *
     * @param e an exception
     */
    public static void handleValidationFailed(final ValidationFailed e) {
        System.err.printf("ERROR: %s for\n", e.getMessage());
        e.getFaultInfo().getErrors().forEach(c -> System.out.printf("\t- %s: %s\n", c.getField(), c.getMessage()));
    }

    /**
     * Handler for FieldIsNotFilterable.
     *
     * @param e an exception
     */
    public static void handleFieldIsNotFilterable(final FieldIsNotFilterable e) {
        System.err.printf("ERROR: %s for %s\n", e.getMessage(), e.getFaultInfo().getField());
    }

    /**
     * Handler default exception.
     *
     * @param e an exception
     */
    public static void handleDefault(final Exception e) {
        System.err.printf("ERROR: %s\n", e.getMessage());
    }
}
