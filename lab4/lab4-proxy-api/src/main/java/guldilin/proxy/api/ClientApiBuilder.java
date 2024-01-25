package guldilin.proxy.api;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import java.net.URI;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.plugins.providers.jackson.ResteasyJackson2Provider;

public class ClientApiBuilder {
    private final URI baseURI;

    public ClientApiBuilder(URI baseURI) {
        this.baseURI = baseURI;
    }

    public CityApi buildCityApiClient() {
        Client client = ClientBuilder.newBuilder()
                .register(ResteasyJackson2Provider.class)
                .build();
        ResteasyWebTarget target = (ResteasyWebTarget) client.target(this.baseURI);
        return target.proxy(CityApi.class);
    }
}
