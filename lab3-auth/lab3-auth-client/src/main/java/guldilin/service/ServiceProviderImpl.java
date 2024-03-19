package guldilin.service;

import guldilin.proxy.api.CityService;
import guldilin.proxy.api.CityWs;
import jakarta.xml.ws.BindingProvider;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Implementation of ServiceProvider.
 */
public class ServiceProviderImpl implements ServiceProvider {
    /**
     * URL of city service url.
     */
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
     * {@inheritDoc}
     */
    @Override
    public CityWs provideCityService() {

        var service = new CityService(this.cityServiceUrl);
        var port = service.getCityPort();
        BindingProvider bp = (BindingProvider) port;

        var binding = bp.getBinding();
        var handlerList = binding.getHandlerChain();
        handlerList.add(new AuthHandler("test", "test"));
        binding.setHandlerChain(handlerList);

        return port;
    }
}
