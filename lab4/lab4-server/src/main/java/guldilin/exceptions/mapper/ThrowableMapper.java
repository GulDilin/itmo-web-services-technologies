package guldilin.exceptions.mapper;

import guldilin.dto.ErrorDTO;
import guldilin.exceptions.ErrorCode;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Provider
public class ThrowableMapper implements ExceptionMapper<Throwable> {
    private static final Logger LOGGER = LogManager.getLogger(ThrowableMapper.class);
    @Override
    public Response toResponse(Throwable e) {
        LOGGER.log(Level.ERROR, e.getMessage(), e);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ErrorDTO.builder()
                        .code(ErrorCode.INTERNAL_SERVER_ERROR)
                        .message(e.getMessage())
                        .build())
                .build();
    }
}
