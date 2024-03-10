package guldilin.discovery;

import guldilin.discovery.exceptions.BusinessNotFound;
import guldilin.discovery.exceptions.ServiceNotFound;
import java.net.URL;
import java.rmi.RemoteException;
import org.uddi.api_v3.AuthToken;
import org.uddi.api_v3.BindingDetail;

/**
 * jUDDI Client.
 */
public interface JuddiClient {
    /**
     * Generate auth token.
     *
     * @return generated token
     * @throws RemoteException if got an error from jUDDI
     */
    AuthToken provideAuthToken() throws RemoteException;

    /**
     * Discard auth token after done.
     *
     * @param token auth token
     * @throws RemoteException if got an error from jUDDI
     */
    void discardAuthToken(AuthToken token) throws RemoteException;

    /**
     * Register business by name.
     *
     * @param businessName Name of business.
     * @param authToken auth token
     * @return business key of saved business
     * @throws RemoteException if got an error from jUDDI
     */
    String registerBusiness(String businessName, AuthToken authToken) throws RemoteException;

    /**
     * Register service by name and business key.
     *
     * @param businessKey jUDDI business key.
     * @param serviceName Name of service
     * @param authToken auth token
     * @return service key of saved service
     * @throws RemoteException if got an error from jUDDI
     */
    String registerService(String businessKey, String serviceName, AuthToken authToken) throws RemoteException;

    /**
     * Bind service url to exist service.
     *
     * @param serviceKey jUDDI service key
     * @param serviceUrl URL of service
     * @param authToken auth token
     * @return binding details
     * @throws RemoteException if got an error from jUDDI
     */
    BindingDetail bindService(String serviceKey, URL serviceUrl, AuthToken authToken) throws RemoteException;

    /**
     * Register both business and service by name.
     * Then bind service to URL.
     * Don't create business or service if already exists.
     *
     * @param businessName Name of business entity.
     * @param serviceName Name of service.
     * @param serviceUrl URL of service.
     * @return service key of saved service
     * @throws RemoteException if got an error from jUDDI
     */
    String registerBusinessService(String businessName, String serviceName, URL serviceUrl) throws RemoteException;

    /**
     * Find business key in jUDDI by name.
     *
     * @param businessName Name of business entity.
     * @return business key of found business
     * @throws RemoteException if got an error from jUDDI
     * @throws BusinessNotFound if business did not find
     */
    String findBusinessInfo(String businessName) throws RemoteException, BusinessNotFound;

    /**
     * Find service key in jUDDI by name and business key.
     *
     * @param businessKey business key
     * @param serviceName Name of service
     * @return service key of found service
     * @throws RemoteException if got an error from jUDDI
     * @throws ServiceNotFound if service did not find
     */
    String findServiceInfo(String businessKey, String serviceName) throws RemoteException, ServiceNotFound;

    /**
     * Find service binding by service key.
     *
     * @param serviceKey Service key
     * @return binding detail
     * @throws RemoteException if got an error from jUDDI
     */
    BindingDetail findServiceBindingInfo(String serviceKey) throws RemoteException;

    /**
     * Find service binding by business name and service name.
     *
     * @param businessName Name of business entity.
     * @param serviceName Name of service
     * @return binding detail
     * @throws RemoteException if got an error from jUDDI
     * @throws ServiceNotFound if service did not find
     * @throws BusinessNotFound if business did not find
     */
    BindingDetail findService(String businessName, String serviceName)
            throws RemoteException, ServiceNotFound, BusinessNotFound;
}
