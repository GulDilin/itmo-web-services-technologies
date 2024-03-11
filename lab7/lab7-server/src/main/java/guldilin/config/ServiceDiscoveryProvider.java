package guldilin.config;

import guldilin.discovery.JuddiClient;
import guldilin.discovery.JuddiClientImpl;
import guldilin.discovery.ServiceDiscovery;
import guldilin.discovery.ServiceDiscoveryImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import lombok.NoArgsConstructor;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.juddi.v3.client.transport.TransportException;

/**
 * Provider.
 */
@NoArgsConstructor
@ApplicationScoped
public class ServiceDiscoveryProvider {

    /**
     * Provides JuddiClient.
     *
     * @return JuddiClient
     * @throws ConfigurationException if JuddiClient creation error occurred
     * @throws TransportException if JuddiClient creation error occurred
     */
    @Produces
    @Singleton
    public JuddiClient provideJuddiClient() throws ConfigurationException, TransportException {
        String host = PropertyKey.UDDI_HOST.lookupValue();
        Integer port = Integer.parseInt(PropertyKey.UDDI_PORT.lookupValue());
        String username = PropertyKey.UDDI_USERNAME.lookupValue();
        String password = PropertyKey.UDDI_PASSWORD.lookupValue();
        return new JuddiClientImpl(host, port, username, password);
    }

    /**
     * Providers ServiceDiscovery.
     *
     * @param juddiClient Injected JuddiClient
     * @return ServiceDiscovery instance
     */
    @Produces
    @Singleton
    public ServiceDiscovery provideServiceDiscovery(final JuddiClient juddiClient) {
        return new ServiceDiscoveryImpl(juddiClient);
    }
}
