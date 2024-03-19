package guldilin.security;

import guldilin.dto.ErrorDTO;
import guldilin.exceptions.AuthError;
import guldilin.exceptions.ErrorCode;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Incoming requests filter.
 */
@Provider
public class AuthHandler implements ContainerRequestFilter {
    /**
     * Prefix for basic auth header value.
     */
    private static final String BASIC_PREFIX = "Basic ";
    /**
     * Separator for basic auth.
     */
    private static final String BASIC_SEPARATOR = ":";
    /**
     * Just logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(AuthHandler.class);

    /**
     * Auth by header value.
     *
     * @param authHeader Auth header value.
     * @throws AuthError if auth failed.
     */
    private void authByHeader(final String authHeader) throws AuthError {
        if (!authHeader.startsWith(BASIC_PREFIX)) throw new AuthError();
        var authData = authHeader.substring(BASIC_PREFIX.length());
        authData = new String(Base64.getDecoder().decode(authHeader.getBytes()));
        LOGGER.info("Auth data: " + authHeader);
        var data = authData.split(BASIC_SEPARATOR);
        if (data.length != 2) throw new AuthError();
        var login = data[0];
        var pass = data[1];
        var authService = new AuthServiceImpl();
        authService.authUser(login, pass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void filter(final ContainerRequestContext requestContext) throws IOException {
        try {
            if (requestContext.getMethod().equals(HttpMethod.GET)) return;
            String authHeader =
                    requestContext.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            this.authByHeader(authHeader);
        } catch (AuthError | IndexOutOfBoundsException | NullPointerException e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity(ErrorDTO.builder()
                            .code(ErrorCode.NOT_AUTHORIZED)
                            .message("Not authorized")
                            .build())
                    .type(MediaType.APPLICATION_JSON)
                    .build());
        }
    }
}
