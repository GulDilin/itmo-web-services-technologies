package guldilin.proxy.juddi;

import org.apache.juddi.v3.client.config.UDDIClient;
import org.apache.juddi.v3.client.transport.Transport;
import org.uddi.api_v3.AuthToken;
import org.uddi.api_v3.GetAuthToken;

public class JuddiClient {
    public void publish() {
        try {
            // create a client and read the config in the archive;
            // you can use your config file name
            UDDIClient uddiClient = new UDDIClient();
            // a UddiClient can be a client to multiple UDDI nodes, so
            // supply the nodeName (defined in your uddi.xml.
            // The transport can be WS, inVM, RMI etc which is defined in the uddi.xml
            Transport transport = uddiClient.getTransport("default");
            // Now you create a reference to the UDDI API
            security = transport.getUDDISecurityService();
            publish = transport.getUDDIPublishService();

            GetAuthToken getAuthTokenMyPub = new GetAuthToken();
            getAuthTokenMyPub.setUserID("bob"); // your username
            getAuthTokenMyPub.setCred("bob"); // your password
            AuthToken myPubAuthToken = security.getAuthToken(getAuthTokenMyPub);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
