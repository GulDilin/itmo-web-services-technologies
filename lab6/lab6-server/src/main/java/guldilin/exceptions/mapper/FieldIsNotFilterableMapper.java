package guldilin.exceptions.mapper;

import guldilin.dto.ErrorDTO;
import guldilin.exceptions.ErrorCode;
import guldilin.exceptions.FieldIsNotFilterable;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * ExceptionMapper for FieldIsNotFilterableMapper.
 * Maps an FieldIsNotFilterableMapper exception to json response.
 */
@Provider
public class FieldIsNotFilterableMapper implements ExceptionMapper<FieldIsNotFilterable> {
    /**
     * {@inheritDoc}
     */
    @Override
    public Response toResponse(final FieldIsNotFilterable e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorDTO.builder()
                        .code(ErrorCode.FIELD_IS_NOT_FILTERABLE)
                        .message(e.getMessage())
                        .detail(e.getFaultInfo())
                        .build())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
