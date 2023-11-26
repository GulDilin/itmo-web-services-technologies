package guldilin.service;

import guldilin.proxy.api.City;
import guldilin.proxy.api.CityService;
import java.net.MalformedURLException;
import java.net.URL;

public class ServiceProviderImpl implements ServiceProvider {
    private final URL cityServiceUrl;

    /**
     * Default constructor.
     *
     * @param baseUrl base url of server
     * @throws MalformedURLException If base URL is not correct
     */
    public ServiceProviderImpl(final URL baseUrl) throws MalformedURLException {
        this.cityServiceUrl = new URL(String.format("%s/CityService?wsdl", baseUrl));
    }

    /**
     * Provides City service of proxy-api for server.
     *
     * @return City service instance
     */
    @Override
    public City provideCityService() {
        var service = new CityService(this.cityServiceUrl);
        return service.getCityPort();
    }
}
