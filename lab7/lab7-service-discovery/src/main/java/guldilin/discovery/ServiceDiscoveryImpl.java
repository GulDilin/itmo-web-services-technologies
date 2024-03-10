package guldilin.discovery;

import guldilin.discovery.exceptions.BusinessNotFound;
import guldilin.discovery.exceptions.ServiceNotFound;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.stream.Collectors;
import org.uddi.api_v3.AccessPoint;
import org.uddi.api_v3.BindingTemplate;

/**
 * Service Discovery class.
 */
public class ServiceDiscoveryImpl implements ServiceDiscovery {
    /**
     * Business name for that application.
     */
    public static final String BUSINESS_KEY = "tws";

    /**
     * jUDDI client implementation. Used for service registration and finding.
     */
    private final JuddiClient juddiClient;

    /**
     * Constructor.
     *
     * @param juddiClient jUDDI client implementation.
     */
    public ServiceDiscoveryImpl(final JuddiClient juddiClient) {
        this.juddiClient = juddiClient;
    }

    /**
     * Generate service name by class.
     *
     * @param clazz Service class.
     * @return service name
     */
    private String getServiceName(final Class<?> clazz) {
        return clazz.getSimpleName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerService(final String baseUrl, final Class<?> clazz)
            throws RemoteException, MalformedURLException {
        var name = this.getServiceName(clazz);
        var url = new URL(String.format("%s/%s?wsdl", baseUrl, name));
        System.out.printf("Register Service %s [%s]\n", name, url);
        this.juddiClient.registerBusinessService(BUSINESS_KEY, name, url);
    }

    private URL urlFromString(final String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<URL> findService(final Class<?> clazz) throws RemoteException, BusinessNotFound, ServiceNotFound {
        var name = this.getServiceName(clazz);
        var binding = this.juddiClient.findService(BUSINESS_KEY, name);
        var urls = binding.getBindingTemplate().stream()
                .map(BindingTemplate::getAccessPoint)
                .map(AccessPoint::getValue)
                .map(this::urlFromString)
                .collect(Collectors.toList());
        System.out.printf("Find Service %s access points: %s\n", name, urls);
        return urls;
    }
}
