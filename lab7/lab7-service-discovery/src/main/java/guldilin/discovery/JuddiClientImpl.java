package guldilin.discovery;

import guldilin.discovery.exceptions.BusinessNotFound;
import guldilin.discovery.exceptions.ServiceNotFound;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Locale;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.juddi.api_v3.AccessPointType;
import org.apache.juddi.v3.client.config.UDDIClient;
import org.apache.juddi.v3.client.transport.Transport;
import org.apache.juddi.v3.client.transport.TransportException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

/**
 * jUDDI client implementation.
 */
public class JuddiClientImpl implements JuddiClient {
    /**
     * Security UDDI port.
     */
    private final UDDISecurityPortType security;
    /**
     * Publisher UDDI port. Used for business and service registration.
     */
    private final UDDIPublicationPortType publisher;
    /**
     * Inquire UDDI port. Used for finding services and business entities from jUDDI.
     */
    private final UDDIInquiryPortType inquire;

    /**
     * Auth Token request data.
     */
    private final GetAuthToken authTokenReqData;

    /**
     * Default config path inside jar.
     */
    public static final String DEFAULT_CONFIG_PATH = "META-INF/uddi.xml";

    /**
     * Default transport node name inside uddi.xml config file.
     */
    public static final String DEFAULT_TRANSPORT_NODE_NAME = "default";

    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(JuddiClientImpl.class);

    /**
     * Full constructor.
     *
     * @param username jUDDI username used for publishing and service registration.
     * @param password jUDDI password used for publishing and service registration.
     * @param configPath config path to uddi.xml inside jar.
     * @param transportNodeName Transport node name from uddi.xml.
     * @throws ConfigurationException if provided configuration is not correct.
     * @throws TransportException if got an error from jUDDI.
     */
    public JuddiClientImpl(
            final String username, final String password, final String configPath, final String transportNodeName)
            throws ConfigurationException, TransportException {
        UDDIClient client = new UDDIClient(configPath);
        Transport transport = client.getTransport(transportNodeName);
        this.security = transport.getUDDISecurityService();
        this.publisher = transport.getUDDIPublishService();
        this.inquire = transport.getUDDIInquiryService();

        var getAuthTokenMyPub = new GetAuthToken();
        getAuthTokenMyPub.setUserID(username);
        getAuthTokenMyPub.setCred(password);
        this.authTokenReqData = getAuthTokenMyPub;
    }

    /**
     * Constructor using default config.
     *
     * @param username jUDDI username used for publishing and service registration.
     * @param password jUDDI password used for publishing and service registration.
     * @throws ConfigurationException if provided configuration is not correct.
     * @throws TransportException if got an error from jUDDI.
     */
    public JuddiClientImpl(final String username, final String password)
            throws ConfigurationException, TransportException {
        this(username, password, DEFAULT_CONFIG_PATH, DEFAULT_TRANSPORT_NODE_NAME);
    }

    /**
     * Default constructor. With empty username or password. Can be used for finding services.
     *
     * @throws ConfigurationException if provided configuration is not correct.
     * @throws TransportException if got an error from jUDDI.
     */
    public JuddiClientImpl() throws ConfigurationException, TransportException {
        this(null, null, DEFAULT_CONFIG_PATH, DEFAULT_TRANSPORT_NODE_NAME);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthToken provideAuthToken() throws RemoteException {
        return this.security.getAuthToken(this.authTokenReqData);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void discardAuthToken(final AuthToken token) throws RemoteException {
        security.discardAuthToken(new DiscardAuthToken(token.getAuthInfo()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String registerBusiness(final String businessName, final AuthToken authToken) throws RemoteException {
        LOGGER.info(String.format("Register Business: %s", businessName));
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String registerService(final String businessKey, final String serviceName, final AuthToken authToken)
            throws RemoteException {
        LOGGER.info(String.format("Register Service: %s. Business key: %s", serviceName, businessKey));
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

    /**
     * {@inheritDoc}
     */
    @Override
    public BindingDetail bindService(final String serviceKey, final URL serviceUrl, final AuthToken authToken)
            throws RemoteException {
        LOGGER.info(String.format("Bind Service URL: %s. Service key: %s", serviceUrl, serviceKey));
        AccessPoint accessPoint = new AccessPoint(serviceUrl.toString(), AccessPointType.WSDL_DEPLOYMENT.toString());
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String registerBusinessService(final String businessName, final String serviceName, final URL serviceUrl)
            throws RemoteException {
        LOGGER.info(String.format(
                "Register Business Service. Business name: %s. Service name: %s. URL: %s.",
                businessName, serviceName, serviceUrl));
        var token = this.provideAuthToken();
        String businessKey;
        try {
            businessKey = this.findBusinessInfo(businessName);
            LOGGER.info(String.format("Found business by name %s. Key: %s", businessName, businessKey));
        } catch (BusinessNotFound e) {
            businessKey = this.registerBusiness(businessName, token);
        }
        String serviceKey;
        try {
            serviceKey = this.findServiceInfo(businessKey, serviceName);
            LOGGER.info(String.format(
                    "Found service by name %s and business key %s. Key: %s", serviceName, businessKey, serviceKey));
        } catch (ServiceNotFound e) {
            serviceKey = this.registerService(businessKey, serviceName, token);
        }
        var binding = this.findServiceBindingInfo(serviceKey);
        var hasBinding = binding.getBindingTemplate().stream()
                .anyMatch(b -> b.getAccessPoint().getValue().equals(serviceUrl.toString()));
        if (!hasBinding) {
            this.bindService(serviceKey, serviceUrl, token);
        } else {
            LOGGER.info(String.format("Service URL %s for %s is already bound", serviceUrl, serviceName));
        }
        // logout after done
        this.discardAuthToken(token);
        return serviceKey;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String findBusinessInfo(final String businessName) throws RemoteException, BusinessNotFound {
        LOGGER.info(String.format("Find Business by name: %s.", businessName));
        var findBusinessData = new FindBusiness();
        findBusinessData.getName().add(new Name(businessName, Locale.ENGLISH.getDisplayLanguage()));
        var businessList = this.inquire.findBusiness(findBusinessData);
        try {
            var businessInfos = businessList.getBusinessInfos().getBusinessInfo();
            if (businessInfos.isEmpty()) throw new BusinessNotFound();
            var businessKey = businessInfos.get(0).getBusinessKey();
            LOGGER.info(String.format("Found Business key: %s by name: %s.", businessKey, businessName));
            return businessKey;
        } catch (NullPointerException ex) {
            LOGGER.info(String.format("Not found Business by name: %s.", businessName));
            throw new BusinessNotFound();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String findServiceInfo(final String businessKey, final String serviceName)
            throws RemoteException, ServiceNotFound {
        LOGGER.info(String.format("Find Service by name: %s and business key %s.", serviceName, businessKey));
        var findServiceData = new FindService();
        findServiceData.setBusinessKey(businessKey);
        findServiceData.getName().add(new Name(serviceName, Locale.ENGLISH.getDisplayLanguage()));
        var serviceList = this.inquire.findService(findServiceData);
        try {
            var serviceInfos = serviceList.getServiceInfos().getServiceInfo();
            if (serviceInfos.isEmpty()) throw new ServiceNotFound();
            var serviceKey = serviceInfos.get(0).getServiceKey();
            LOGGER.info(String.format(
                    "Found Service key: %s by name: %s and business key %s.", serviceKey, serviceName, businessKey));
            return serviceKey;
        } catch (NullPointerException ex) {
            LOGGER.info(String.format("Not found Service by name: %s and business key %s.", serviceName, businessKey));
            throw new ServiceNotFound();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BindingDetail findServiceBindingInfo(final String serviceKey) throws RemoteException {
        var findBindingData = new FindBinding();
        findBindingData.setServiceKey(serviceKey);
        return this.inquire.findBinding(findBindingData);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BindingDetail findService(final String businessName, final String serviceName)
            throws RemoteException, ServiceNotFound, BusinessNotFound {
        LOGGER.info(
                String.format("Find Service URL by business name: %s and service name %s.", businessName, serviceName));
        var businessKey = this.findBusinessInfo(businessName);
        var serviceKey = this.findServiceInfo(businessKey, serviceName);
        var binding = this.findServiceBindingInfo(serviceKey);
        return binding;
    }
}
