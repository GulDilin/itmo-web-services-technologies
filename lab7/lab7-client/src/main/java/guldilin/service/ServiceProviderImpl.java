package guldilin.service;

import guldilin.discovery.JuddiClientImpl;
import guldilin.discovery.ServiceDiscoveryImpl;
import guldilin.discovery.exceptions.BusinessNotFound;
import guldilin.discovery.exceptions.ServiceNotFound;
import guldilin.proxy.api.CityService;
import guldilin.proxy.api.CityWs;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.rmi.RemoteException;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.juddi.v3.client.transport.TransportException;

/**
 * Implementation of ServiceProvider.
 */
public class ServiceProviderImpl implements ServiceProvider {
    /**
     * URL of city service url.
     */
    private final ServiceDiscoveryImpl serviceDiscovery;

    /**
     * Default Constructor.
     *
     * @param juddiHost jUDDI host name.
     * @param juddiPort jUDDI port.
     * @throws ConfigurationException for incorrect config.
     * @throws TransportException     if jUDDI client cannot be created.
     */
    public ServiceProviderImpl(final String juddiHost, final Integer juddiPort)
            throws ConfigurationException, TransportException {
        this.serviceDiscovery = new ServiceDiscoveryImpl(new JuddiClientImpl(juddiHost, juddiPort));
    }

    private Boolean isUrlAlive(final URL url) {
        try {
            var client = HttpClient.newBuilder().build();
            var request = HttpRequest.newBuilder(url.toURI()).GET().build();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() < Response.Status.BAD_REQUEST.getStatusCode();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CityWs provideCityService() throws RemoteException, BusinessNotFound, ServiceNotFound {
        var urls = this.serviceDiscovery.findService(CityService.class);
        var urlOptional = urls.stream().filter(this::isUrlAlive).findFirst();
        if (urlOptional.isEmpty()) {
            System.out.println("Not Found city service alive url");
            throw new ServiceNotFound();
        }
        var url = urlOptional.get();
        System.out.printf("Found city service alive url: %s\n", url);
        var service = new CityService(url);
        return service.getCityPort();
    }
}
