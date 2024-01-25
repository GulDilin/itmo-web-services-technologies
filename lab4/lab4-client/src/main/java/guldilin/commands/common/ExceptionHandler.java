package guldilin.commands.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import guldilin.proxy.api.dto.EntryNotFoundFault;
import guldilin.proxy.api.dto.ErrorDTO;
import guldilin.proxy.api.dto.FieldIsNotFilterableFault;
import guldilin.proxy.api.dto.FieldValidationFault;
import jakarta.ws.rs.WebApplicationException;

public class ExceptionHandler {
    public static void handleException(Exception e) {
        if (e instanceof WebApplicationException err) {
            ErrorDTO dto = err.getResponse().readEntity(ErrorDTO.class);
            handleException(dto);
        } else handleDefault(e);
    }

    public static void handleException(ErrorDTO e) {
        switch (e.getCode()) {
            case ENTITY_NOT_FOUND -> handleEntryNotFound(e);
            case VALIDATION_FAILED -> handleValidationFailed(e);
            case FIELD_IS_NOT_FILTERABLE -> handleFieldIsNotFilterable(e);
            default -> handleDefault(e);
        }
    }

    public static void handleEntryNotFound(ErrorDTO e) {
        var detailMap = e.getDetail();
        ObjectMapper mapper = new ObjectMapper();
        EntryNotFoundFault detail = mapper.convertValue(detailMap, EntryNotFoundFault.class);
        System.err.printf("ERROR: %s for entity %s with id %d\n", e.getMessage(), detail.getEntity(), detail.getId());
    }

    public static void handleValidationFailed(ErrorDTO e) {
        var detailMap = e.getDetail();
        ObjectMapper mapper = new ObjectMapper();
        FieldValidationFault detail = mapper.convertValue(detailMap, FieldValidationFault.class);
        System.err.printf("ERROR: %s for\n", e.getMessage());
        detail.getErrors().forEach(c -> System.out.printf("\t- %s: %s\n", c.getField(), c.getMessage()));
    }

    public static void handleFieldIsNotFilterable(ErrorDTO e) {
        var detailMap = e.getDetail();
        ObjectMapper mapper = new ObjectMapper();
        FieldIsNotFilterableFault detail = mapper.convertValue(detailMap, FieldIsNotFilterableFault.class);
        System.err.printf("ERROR: %s for %s\n", e.getMessage(), detail.getField());
    }

    public static void handleDefault(ErrorDTO e) {
        System.err.printf("ERROR: %s\n", e.getMessage());
    }

    public static void handleDefault(Exception e) {
        System.err.printf("ERROR: %s\n", e.getMessage());
    }
}
