package guldilin.exceptions.mapper;

import guldilin.dto.ErrorDTO;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * ExceptionMapper for all Throwable that did not match to other mappers.
 */
@Provider
public class ThrowableMapper implements ExceptionMapper<Throwable> {
    /**
     * The logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(ThrowableMapper.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public Response toResponse(final Throwable e) {
        LOGGER.log(Level.ERROR, e.getMessage(), e);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ErrorDTO.builder().message(e.getMessage()).build())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
