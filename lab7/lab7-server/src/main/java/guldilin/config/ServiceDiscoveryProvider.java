package guldilin.config;

import guldilin.discovery.JuddiClient;
import guldilin.discovery.JuddiClientInterface;
import guldilin.discovery.ServiceDiscovery;
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
    public JuddiClientInterface provideJuddiClient() throws ConfigurationException, TransportException {
        String username = PropertyKey.UDDI_USERNAME.lookupValue();
        String password = PropertyKey.UDDI_PASSWORD.lookupValue();
        return new JuddiClient(username, password);

    }

    /**
     * Providers ServiceDiscovery.
     *
     * @param juddiClient Injected JuddiClient
     * @return ServiceDiscovery instance
     */
    @Produces
    @Singleton
    public ServiceDiscovery provideServiceDiscovery(final JuddiClientInterface juddiClient) {
        return new ServiceDiscovery(juddiClient);
    }
}
