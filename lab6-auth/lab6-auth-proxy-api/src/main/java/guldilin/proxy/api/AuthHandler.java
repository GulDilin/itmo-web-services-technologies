package guldilin.proxy.api;

import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Base64;

/**
 * This class describes an auth handler.
 */
@Provider
public class AuthHandler implements ClientRequestFilter {
    /**
     * Gets the basic authentication header value.
     *
     * @return     The basic authentication.
     */
    private String getBasicAuthentication() {
        var userPass = String.format("%s:%s", "test", "test");
        var userPassB64 = Base64.getEncoder().encode(userPass.getBytes());
        return String.format("Basic %s", new String(userPassB64));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void filter(final ClientRequestContext requestContext) throws IOException {
        requestContext.getHeaders().add(HttpHeaders.AUTHORIZATION, getBasicAuthentication());
    }
}
