package guldilin.commands.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import guldilin.proxy.api.dto.EntryNotFoundFault;
import guldilin.proxy.api.dto.ErrorDTO;
import guldilin.proxy.api.dto.FieldIsNotFilterableFault;
import guldilin.proxy.api.dto.FieldValidationFault;
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
        switch (e.getCode()) {
            case ENTITY_NOT_FOUND -> handleEntryNotFound(e);
            case VALIDATION_FAILED -> handleValidationFailed(e);
            case FIELD_IS_NOT_FILTERABLE -> handleFieldIsNotFilterable(e);
            default -> handleDefault(e);
        }
    }

    /**
     * Handle EntryNotFoundFault.
     *
     * @param e the exception DTO.
     */
    public static void handleEntryNotFound(final ErrorDTO e) {
        var detailMap = e.getDetail();
        ObjectMapper mapper = new ObjectMapper();
        EntryNotFoundFault detail = mapper.convertValue(detailMap, EntryNotFoundFault.class);
        System.err.printf("ERROR: %s for entity %s with id %d\n", e.getMessage(), detail.getEntity(), detail.getId());
    }

    /**
     * Handle FieldValidationFault.
     *
     * @param e the exception DTO.
     */
    public static void handleValidationFailed(final ErrorDTO e) {
        var detailMap = e.getDetail();
        ObjectMapper mapper = new ObjectMapper();
        FieldValidationFault detail = mapper.convertValue(detailMap, FieldValidationFault.class);
        System.err.printf("ERROR: %s for\n", e.getMessage());
        detail.getErrors().forEach(c -> System.out.printf("\t- %s: %s\n", c.getField(), c.getMessage()));
    }

    /**
     * Handle FieldIsNotFilterableFault.
     *
     * @param e the exception DTO.
     */
    public static void handleFieldIsNotFilterable(final ErrorDTO e) {
        var detailMap = e.getDetail();
        ObjectMapper mapper = new ObjectMapper();
        FieldIsNotFilterableFault detail = mapper.convertValue(detailMap, FieldIsNotFilterableFault.class);
        System.err.printf("ERROR: %s for %s\n", e.getMessage(), detail.getField());
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
