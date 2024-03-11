package guldilin.commands.common;

import guldilin.proxy.api.dto.ErrorDTO;
import jakarta.ws.rs.WebApplicationException;

/**
 * Handler for server exceptions.
 */
public final class ExceptionHandler {
    /**
     * Constructor.
     */
    private ExceptionHandler() {
        // default private constructor for utility class.
    }
    /**
     * Main functions of that class. Handles any exception.
     *
     * @param e the exception.
     */
    public static void handleException(final Exception e) {
        if (e instanceof WebApplicationException err) {
            ErrorDTO dto = err.getResponse().readEntity(ErrorDTO.class);
            handleException(dto);
        } else handleDefault(e);
    }

    /**
     * Handle an error DTO.
     *
     * @param e The ErrorDTO info.
     */
    public static void handleException(final ErrorDTO e) {
        handleDefault(e);
    }

    /**
     * Default error dto handler.
     *
     * @param e the exception DTO.
     */
    public static void handleDefault(final ErrorDTO e) {
        System.err.printf("ERROR: %s\n", e.getMessage());
    }

    /**
     * Default exception handler.
     *
     * @param e the exception DTO.
     */
    public static void handleDefault(final Exception e) {
        System.err.printf("ERROR: %s\n", e.getMessage());
    }
}
