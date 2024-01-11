package guldilin.exceptions;

import jakarta.xml.ws.WebFault;
import lombok.Getter;

/**
 * Exception for non-filterable fields.
 */
@WebFault(faultBean = "guldilin.exceptions.FieldIsNotFilterableFault")
@Getter
public class FieldIsNotFilterable extends Exception {
    /**
     * Additional info about exception.
     */
    private final FieldIsNotFilterableFault faultInfo;

    /**
     * Constructor.
     *
     * @param message The error message
     * @param faultInfo additional info
     */
    public FieldIsNotFilterable(String message, FieldIsNotFilterableFault faultInfo) {
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
    public FieldIsNotFilterable(String message, FieldIsNotFilterableFault faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * Default constructor
     */
    public FieldIsNotFilterable() {
        super(ErrorMessages.NOT_FOUND);
        this.faultInfo = FieldIsNotFilterableFault.builder().build();
    }

    /**
     * Constructor.
     *
     * @param faultInfo additional info
     */
    public FieldIsNotFilterable(FieldIsNotFilterableFault faultInfo) {
        super(ErrorMessages.NOT_FOUND);
        this.faultInfo = faultInfo;
    }
}
