package guldilin.service;

import guldilin.proxy.api.CityApi;
import guldilin.proxy.api.ClientApiBuilder;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Implementation of ServiceProvider.
 */
public class ServiceProviderImpl implements ServiceProvider {
    /**
     * Client API builder.
     */
    private final ClientApiBuilder builder;

    /**
     * Default constructor.
     *
     * @param baseUrl base url of server
     * @throws MalformedURLException If base URL is not correct
     */
    public ServiceProviderImpl(final URL baseUrl) throws MalformedURLException, URISyntaxException {
        this.builder = new ClientApiBuilder(baseUrl.toURI());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CityApi provideCityService() {
        return this.builder.buildCityApiClient();
    }
}
