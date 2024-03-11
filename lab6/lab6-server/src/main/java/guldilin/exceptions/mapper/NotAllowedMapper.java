package guldilin.exceptions.mapper;

import guldilin.dto.ErrorDTO;
import guldilin.exceptions.ErrorCode;
import jakarta.ws.rs.NotAllowedException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * ExceptionMapper for NotAllowedException.
 * Maps an NotAllowedException exception to json response.
 */
@Provider
public class NotAllowedMapper implements ExceptionMapper<NotAllowedException> {
    /**
     * The logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(ThrowableMapper.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public Response toResponse(final NotAllowedException e) {
        LOGGER.warn(e.getMessage());
        return Response.status(Response.Status.METHOD_NOT_ALLOWED)
                .entity(ErrorDTO.builder()
                        .code(ErrorCode.METHOD_NOT_ALLOWED)
                        .message(e.getMessage())
                        .build())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
