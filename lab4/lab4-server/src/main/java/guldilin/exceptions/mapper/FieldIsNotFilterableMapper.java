package guldilin.exceptions.mapper;

import guldilin.dto.ErrorDTO;
import guldilin.exceptions.ErrorCode;
import guldilin.exceptions.FieldIsNotFilterable;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class FieldIsNotFilterableMapper implements ExceptionMapper<FieldIsNotFilterable> {
    @Override
    public Response toResponse(FieldIsNotFilterable e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorDTO.builder()
                        .code(ErrorCode.FIELD_IS_NOT_FILTERABLE)
                        .message(e.getMessage())
                        .detail(e.getFaultInfo())
                        .build())
                .build();
    }
}
