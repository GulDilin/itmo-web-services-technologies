package guldilin.discovery;

import guldilin.discovery.exceptions.BusinessNotFound;
import guldilin.discovery.exceptions.ServiceNotFound;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Service Discovery.
 */
public interface ServiceDiscovery {
    /**
     * Register service and bind to URL by class.
     *
     * @param baseUrl base API url (without service path).
     * @param clazz   service class.
     * @throws RemoteException if got an error on register
     */
    void registerService(String baseUrl, Class<?> clazz) throws RemoteException, MalformedURLException;

    /**
     * Find service bindings in jUDDI by service class.
     *
     * @param clazz service class.
     * @return list of registered service urls
     * @throws RemoteException  if got an error from jUDDI
     * @throws BusinessNotFound if business did not find
     * @throws ServiceNotFound  if service did not find
     */
    List<URL> findService(Class<?> clazz) throws RemoteException, BusinessNotFound, ServiceNotFound;
}
