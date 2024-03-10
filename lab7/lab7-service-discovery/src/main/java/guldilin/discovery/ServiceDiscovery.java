package guldilin.discovery;

import java.rmi.RemoteException;

public class ServiceDiscovery {
    private final JuddiClientInterface juddiClient;

    public ServiceDiscovery(JuddiClientInterface juddiClient) {
        this.juddiClient = juddiClient;
    }

    private String getServiceName(Class<?> clazz) {
        return clazz.getSimpleName();
    }
    public void registerService(String baseUrl, Class<?> clazz) throws RemoteException {
        String name = this.getServiceName(clazz);
        String url = String.format("%s/%s?wsdl", baseUrl, name);
        System.out.printf("Register Service %s [%s]\n", name, url);
        this.juddiClient.registerBusinessService(ServiceDiscoveryProperties.BUSINESS_KEY, name, url);
    }

    public String findService(Class<?> clazz) throws RemoteException, BusinessNotFound, ServiceNotFound {
        String name = this.getServiceName(clazz);
        var binding = this.juddiClient.findService(ServiceDiscoveryProperties.BUSINESS_KEY, name);
        String url = binding.getBindingTemplate().get(0).getAccessPoint().getValue();
        System.out.printf("Find Service %s [%s]\n", name, url);
        return url;
    }
}
