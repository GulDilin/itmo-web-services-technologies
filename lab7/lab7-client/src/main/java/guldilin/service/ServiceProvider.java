package guldilin.service;

import guldilin.discovery.exceptions.BusinessNotFound;
import guldilin.discovery.exceptions.ServiceNotFound;
import guldilin.proxy.api.CityWs;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

/**
 * Interface for service provider.
 */
public interface ServiceProvider {
    /**
     * Provides City service of proxy-api for server.
     *
     * @return City service instance
     */
    CityWs provideCityService() throws RemoteException, MalformedURLException, BusinessNotFound, ServiceNotFound;
}
