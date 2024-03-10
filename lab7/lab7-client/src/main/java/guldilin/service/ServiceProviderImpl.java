package guldilin.service;

import guldilin.discovery.BusinessNotFound;
import guldilin.discovery.JuddiClient;
import guldilin.discovery.ServiceDiscovery;
import guldilin.discovery.ServiceNotFound;
import guldilin.proxy.api.CityService;
import guldilin.proxy.api.CityWs;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.juddi.v3.client.transport.TransportException;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

/**
 * Implementation of ServiceProvider.
 */
public class ServiceProviderImpl implements ServiceProvider {
    /**
     * URL of city service url.
     */
    private final ServiceDiscovery serviceDiscovery;

    /**
     * Default constructor.
     *
     */
    public ServiceProviderImpl(final String juddiUsername, final String juddiPassword) throws ConfigurationException, TransportException {
        var juddiClient = new JuddiClient(juddiUsername, juddiPassword);
        this.serviceDiscovery = new ServiceDiscovery(juddiClient);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CityWs provideCityService() throws RemoteException, MalformedURLException, BusinessNotFound, ServiceNotFound {
        var urlS = this.serviceDiscovery.findService(CityService.class);
        var url = new URL(urlS);
        var service = new CityService(url);
        return service.getCityPort();
    }
}
