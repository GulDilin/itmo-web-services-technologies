package guldilin.exceptions.mapper;

import guldilin.dto.ErrorDTO;
import guldilin.exceptions.ErrorCode;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * ExceptionMapper for NotFoundException.
 * Maps an NotFoundException exception to json response.
 */
@Provider
public class NotFoundMapper implements ExceptionMapper<NotFoundException> {
    /**
     * The logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(ThrowableMapper.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public Response toResponse(final NotFoundException e) {
        LOGGER.warn(e.getMessage());
        return Response.status(Response.Status.NOT_FOUND)
                .entity(ErrorDTO.builder()
                        .code(ErrorCode.RESOURCE_NOT_FOUND)
                        .message(e.getMessage())
                        .build())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
