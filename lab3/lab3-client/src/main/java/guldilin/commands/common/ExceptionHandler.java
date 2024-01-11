package guldilin.commands.common;

import guldilin.proxy.api.EntryNotFound;
import guldilin.proxy.api.FieldIsNotFilterable;
import guldilin.proxy.api.ValidationFailed;

public class ExceptionHandler {
    public static void handleException(Exception e) {
        if (e instanceof EntryNotFound) handleEntryNotFound((EntryNotFound) e);
        else if (e instanceof ValidationFailed) handleValidationFailed((ValidationFailed) e);
        else if (e instanceof FieldIsNotFilterable) handleFieldIsNotFilterable((FieldIsNotFilterable) e);
        else handleDefault(e);
    }

    public static void handleEntryNotFound(EntryNotFound e) {
        System.err.printf(
                "ERROR: %s for entity %s with id %d\n",
                e.getMessage(), e.getFaultInfo().getEntity(), e.getFaultInfo().getId());
    }

    public static void handleValidationFailed(ValidationFailed e) {
        System.err.printf("ERROR: %s for\n", e.getMessage());
        e.getFaultInfo().getErrors().forEach(c -> System.out.printf("\t- %s: %s\n", c.getField(), c.getMessage()));
    }

    public static void handleFieldIsNotFilterable(FieldIsNotFilterable e) {
        System.err.printf("ERROR: %s for %s\n", e.getMessage(), e.getFaultInfo().getField());
    }

    public static void handleDefault(Exception e) {
        System.err.printf("ERROR: %s\n", e.getMessage());
    }
}
