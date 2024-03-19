package guldilin.service;

import jakarta.xml.soap.SOAPElement;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPHeader;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.handler.soap.SOAPHandler;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;
import java.util.Collections;
import java.util.Set;
import javax.xml.namespace.QName;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * SOAP Handler for incoming messages auth.
 */
@NoArgsConstructor
@AllArgsConstructor
public class AuthHandler implements SOAPHandler<SOAPMessageContext> {

    /**
     * Username for auth on server.
     */
    private String user;
    /**
     * Password for auth on server.
     */
    private String pass;

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<QName> getHeaders() {
        return Collections.emptySet();
    }

    private void addAuth(final SOAPMessage soapMessage) throws SOAPException {
        SOAPHeader header = soapMessage.getSOAPHeader();

        SOAPElement security = header.addChildElement(
                "Security",
                "wsse",
                "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");

        SOAPElement usernameToken = security.addChildElement("UsernameToken", "wsse");
        usernameToken.addAttribute(
                new QName("xmlns:wsu"),
                "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
        usernameToken.addAttribute(QName.valueOf("wsu:Id"), "UsernameToken-1");

        SOAPElement username = usernameToken.addChildElement("Username", "wsse");
        username.addTextNode(this.user);

        SOAPElement password = usernameToken.addChildElement("Password", "wsse");
        password.setAttribute(
                "Type",
                "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");
        password.addTextNode(this.pass);

        soapMessage.saveChanges();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean handleMessage(final SOAPMessageContext soapMessageContext) {
        Boolean outboundProperty = (Boolean) soapMessageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        if (!outboundProperty) return true;
        try {
            addAuth(soapMessageContext.getMessage());
        } catch (SOAPException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean handleFault(final SOAPMessageContext soapMessageContext) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close(final MessageContext messageContext) {}
}
