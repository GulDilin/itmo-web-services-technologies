package guldilin.proxy.api;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import java.net.URI;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.plugins.providers.jackson.ResteasyJackson2Provider;

/**
 * Builder class for city API rest client.
 */
public class ClientApiBuilder {
    /**
     * Base URI of application.
     */
    private final URI baseURI;

    /**
     * Constructor.
     * @param baseURI Base URI of application.
     */
    public ClientApiBuilder(final URI baseURI) {
        this.baseURI = baseURI;
    }

    /**
     * Build API client wrapper.
     * @return CityApi service implementation
     */
    public CityApi buildCityApiClient() {
        Client client = ClientBuilder.newBuilder()
                .register(ResteasyJackson2Provider.class)
                .build();
        ResteasyWebTarget target = (ResteasyWebTarget) client.target(this.baseURI);
        return target.proxy(CityApi.class);
    }
}
