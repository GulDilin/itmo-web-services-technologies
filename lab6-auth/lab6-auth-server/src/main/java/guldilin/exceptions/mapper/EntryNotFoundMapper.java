package guldilin.exceptions.mapper;

import guldilin.dto.ErrorDTO;
import guldilin.exceptions.EntryNotFound;
import guldilin.exceptions.ErrorCode;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * ExceptionMapper for EntryNotFound.
 * Maps an EntryNotFound exception to json response.
 */
@Provider
public class EntryNotFoundMapper implements ExceptionMapper<EntryNotFound> {
    /**
     * {@inheritDoc}
     */
    @Override
    public Response toResponse(final EntryNotFound e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorDTO.builder()
                        .code(ErrorCode.ENTITY_NOT_FOUND)
                        .message(e.getMessage())
                        .detail(e.getFaultInfo())
                        .build())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
