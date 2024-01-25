package guldilin.exceptions.mapper;

import guldilin.dto.ErrorDTO;
import guldilin.exceptions.ErrorCode;
import guldilin.exceptions.ValidationFailed;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ValidationMapper implements ExceptionMapper<ValidationFailed> {
    @Override
    public Response toResponse(ValidationFailed e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorDTO.builder()
                        .code(ErrorCode.VALIDATION_FAILED)
                        .message(e.getMessage())
                        .detail(e.getFaultInfo())
                        .build())
                .build();
    }
}
