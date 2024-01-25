package guldilin.exceptions.mapper;

import guldilin.dto.ErrorDTO;
import guldilin.exceptions.ErrorCode;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jakarta.ws.rs.NotFoundException;

@Provider
public class NotFoundMapper implements ExceptionMapper<NotFoundException> {
    private static final Logger LOGGER = LogManager.getLogger(ThrowableMapper.class);
    @Override
    public Response toResponse(NotFoundException e) {
        LOGGER.warn(e.getMessage());
        return Response.status(Response.Status.NOT_FOUND)
                .entity(ErrorDTO.builder()
                        .code(ErrorCode.RESOURCE_NOT_FOUND)
                        .message(e.getMessage())
                        .build())
                .build();
    }
}
