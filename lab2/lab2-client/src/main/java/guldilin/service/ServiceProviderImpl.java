package guldilin.service;

import guldilin.proxy.api.CityService;
import guldilin.proxy.api.CityWs;
import java.net.MalformedURLException;
import java.net.URL;

public class ServiceProviderImpl implements ServiceProvider {
    private final URL cityServiceUrl;

    /**
     * {@inheritDoc}
     */
    public ServiceProviderImpl(final URL baseUrl) throws MalformedURLException {
        this.cityServiceUrl = new URL(String.format("%s/CityService?wsdl", baseUrl));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CityWs provideCityService() {
        var service = new CityService(this.cityServiceUrl);
        return service.getCityPort();
    }
}
