package guldilin.discovery;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.juddi.api_v3.AccessPointType;
import org.apache.juddi.v3.client.config.UDDIClient;
import org.apache.juddi.v3.client.transport.Transport;
import org.apache.juddi.v3.client.transport.TransportException;
import org.uddi.api_v3.AccessPoint;
import org.uddi.api_v3.AuthToken;
import org.uddi.api_v3.BindingDetail;
import org.uddi.api_v3.BindingTemplate;
import org.uddi.api_v3.BusinessEntity;
import org.uddi.api_v3.BusinessService;
import org.uddi.api_v3.DiscardAuthToken;
import org.uddi.api_v3.FindBinding;
import org.uddi.api_v3.FindBusiness;
import org.uddi.api_v3.FindService;
import org.uddi.api_v3.GetAuthToken;
import org.uddi.api_v3.Name;
import org.uddi.api_v3.SaveBinding;
import org.uddi.api_v3.SaveBusiness;
import org.uddi.api_v3.SaveService;
import org.uddi.v3_service.UDDIInquiryPortType;
import org.uddi.v3_service.UDDIPublicationPortType;
import org.uddi.v3_service.UDDISecurityPortType;

import java.rmi.RemoteException;
import java.util.Locale;


public class JuddiClient implements JuddiClientInterface {
    private final UDDIClient client;
    private final Transport transport;
    private final UDDISecurityPortType security;
    private final UDDIPublicationPortType publisher;
    private final UDDIInquiryPortType inquire;
    private final GetAuthToken authTokenReqData;

    public static final String DEFAULT_CONFIG_PATH = "META-INF/uddi.xml";
    public static final String DEFAULT_TRANSPORT_NODE_NAME = "default";

    public JuddiClient(String username, String password, String configPath, String transportNodeName) throws ConfigurationException, TransportException {
        this.client = new UDDIClient(configPath);
        this.transport = this.client.getTransport(transportNodeName);
        this.security = this.transport.getUDDISecurityService();
        this.publisher = this.transport.getUDDIPublishService();
        this.inquire = this.transport.getUDDIInquiryService();

        var getAuthTokenMyPub = new GetAuthToken();
        getAuthTokenMyPub.setUserID(username);
        getAuthTokenMyPub.setCred(password);
        this.authTokenReqData = getAuthTokenMyPub;
    }

    public JuddiClient(String username, String password) throws ConfigurationException, TransportException {
        this(username, password, DEFAULT_CONFIG_PATH, DEFAULT_TRANSPORT_NODE_NAME);
    }

    private AuthToken provideAuthToken() throws RemoteException {
        return this.security.getAuthToken(this.authTokenReqData);
    }

    private void discardAuthToken(AuthToken token) throws RemoteException {
        security.discardAuthToken(new DiscardAuthToken(token.getAuthInfo()));
    }

    @Override
    public String registerBusiness(String businessName, AuthToken authToken) throws RemoteException {
        // Creating the parent business entity that will contain our service.
        BusinessEntity businessEntity = new BusinessEntity();
        businessEntity.getName().add(new Name(businessName, Locale.ENGLISH.getDisplayLanguage()));

        // Adding the business entity to the "save" structure, using our publisher's authentication info
        // and saving away.
        SaveBusiness sb = new SaveBusiness();
        sb.getBusinessEntity().add(businessEntity);
        sb.setAuthInfo(authToken.getAuthInfo());
        return this.publisher.saveBusiness(sb).getBusinessEntity().get(0).getBusinessKey();
    }

    @Override
    public String registerService(String businessKey, String serviceName, AuthToken authToken) throws RemoteException {
        var service = new BusinessService();
        service.setBusinessKey(businessKey);
        service.getName().add(new Name(serviceName, Locale.ENGLISH.getDisplayLanguage()));

        // Adding the service entity to the "save" structure, using our publisher's authentication info
        // and saving away.
        SaveService ss = new SaveService();
        ss.getBusinessService().add(service);
        ss.setAuthInfo(authToken.getAuthInfo());
        return this.publisher.saveService(ss).getBusinessService().get(0).getServiceKey();
    }

    @Override
    public BindingDetail bindService(String serviceKey, String serviceUrl, AuthToken authToken) throws RemoteException {
        AccessPoint accessPoint = new AccessPoint(serviceUrl, AccessPointType.WSDL_DEPLOYMENT.toString());
        BindingTemplate bindingTemplate = new BindingTemplate();
        bindingTemplate.setServiceKey(serviceKey);
        bindingTemplate.setAccessPoint(accessPoint);
        // optional but recommended step, these annotations our binding with all
        // the standard SOAP tModel instance infos
        var bindingTemplateSoap = UDDIClient.addSOAPtModels(bindingTemplate);

        var saveBindingData = new SaveBinding();
        saveBindingData.setAuthInfo(authToken.getAuthInfo());
        saveBindingData.getBindingTemplate().add(bindingTemplateSoap);
        return this.publisher.saveBinding(saveBindingData);
    }

    @Override
    public String registerBusinessService(String businessName, String serviceName, String serviceUrl) throws RemoteException {
        var token = this.provideAuthToken();
        String businessKey;
        try {
            businessKey = this.findBusinessInfo(businessName, token);
        } catch (BusinessNotFound e) {
            businessKey = this.registerBusiness(businessName, token);
        }
        String serviceKey;
        try {
            serviceKey = this.findServiceInfo(businessKey, serviceName, token);
        } catch (ServiceNotFound e) {
            serviceKey = this.registerService(businessKey, serviceName, token);
        }
        var binding = this.findServiceBindingInfo(serviceKey, token);
        var hasBinding = binding.getBindingTemplate().stream().anyMatch(b -> b.getAccessPoint().getValue().equals(serviceUrl));
        if (!hasBinding) {
            this.bindService(serviceKey, serviceUrl, token);
        }
        // logout after done
        this.discardAuthToken(token);
        return serviceKey;
    }

    @Override
    public String findBusinessInfo(String businessName, AuthToken authToken) throws RemoteException, BusinessNotFound {
        var findBusinessData = new FindBusiness();
        findBusinessData.setAuthInfo(authToken.getAuthInfo());
        findBusinessData.getName().add(new Name(businessName, Locale.ENGLISH.getDisplayLanguage()));
        var businessList = this.inquire.findBusiness(findBusinessData);
        try {
            var businessInfos = businessList.getBusinessInfos().getBusinessInfo();
            if (businessInfos.isEmpty()) throw new BusinessNotFound();
            return businessInfos.get(0).getBusinessKey();
        } catch (NullPointerException ex) {
            throw new BusinessNotFound();
        }
    }

    @Override
    public String findServiceInfo(String businessKey, String serviceName, AuthToken authToken) throws RemoteException, ServiceNotFound {
        var findServiceData = new FindService();
        findServiceData.setAuthInfo(authToken.getAuthInfo());
        findServiceData.setBusinessKey(businessKey);
        findServiceData.getName().add(new Name(serviceName, Locale.ENGLISH.getDisplayLanguage()));
        var serviceList = this.inquire.findService(findServiceData);
        try {
            var serviceInfos = serviceList.getServiceInfos().getServiceInfo();
            if (serviceInfos.isEmpty()) throw new ServiceNotFound();
            return serviceInfos.get(0).getServiceKey();
        } catch (NullPointerException ex) {
            throw new ServiceNotFound();
        }
    }

    @Override
    public BindingDetail findServiceBindingInfo(String serviceKey, AuthToken authToken) throws RemoteException {
        var findBindingData = new FindBinding();
        findBindingData.setAuthInfo(authToken.getAuthInfo());
        findBindingData.setServiceKey(serviceKey);
        return this.inquire.findBinding(findBindingData);
    }

    @Override
    public BindingDetail findService(String businessName, String serviceName) throws RemoteException, ServiceNotFound, BusinessNotFound {
        var token = this.provideAuthToken();
        var businessKey = this.findBusinessInfo(businessName, token);
        var serviceKey = this.findServiceInfo(businessKey, serviceName, token);
        var binding = this.findServiceBindingInfo(serviceKey, token);
        this.discardAuthToken(token);
        return binding;
    }
}
