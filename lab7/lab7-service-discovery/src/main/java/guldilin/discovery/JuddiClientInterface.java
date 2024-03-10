package guldilin.discovery;

import org.uddi.api_v3.AuthToken;
import org.uddi.api_v3.BindingDetail;

import java.rmi.RemoteException;

public interface JuddiClientInterface {
    String registerBusiness(String businessName, AuthToken authToken) throws RemoteException;

    String registerService(String businessKey, String serviceName, AuthToken authToken) throws RemoteException;

    BindingDetail bindService(String serviceKey, String serviceUrl, AuthToken authToken) throws RemoteException;

    String registerBusinessService(String businessName, String serviceName, String serviceUrl) throws RemoteException;

    String findBusinessInfo(String businessName, AuthToken authToken) throws RemoteException, BusinessNotFound;


    String findServiceInfo(String businessKey, String serviceName, AuthToken authToken) throws RemoteException, ServiceNotFound;

    BindingDetail findServiceBindingInfo(String serviceKey, AuthToken authToken) throws RemoteException;

    BindingDetail findService(String businessName, String serviceName) throws RemoteException, ServiceNotFound, BusinessNotFound;
}
