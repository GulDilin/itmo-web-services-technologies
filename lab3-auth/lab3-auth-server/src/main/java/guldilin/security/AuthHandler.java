package guldilin.security;

import guldilin.exceptions.AuthError;
import jakarta.xml.soap.Node;
import jakarta.xml.soap.SOAPElement;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.handler.soap.SOAPHandler;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;
import java.util.Collections;
import java.util.Set;
import java.util.stream.StreamSupport;
import javax.xml.namespace.QName;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * JAX-WS handler for incoming messages auth check.
 */
@NoArgsConstructor
public class AuthHandler implements SOAPHandler<SOAPMessageContext> {
    /**
     * Just logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(AuthHandler.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<QName> getHeaders() {
        return Collections.emptySet();
    }

    /**
     * Process auth header from soap mesage.
     *
     * @param soapMessage handled soap message
     * @throws AuthError if auth failed
     */
    private void authByHeader(final SOAPMessage soapMessage) throws AuthError {
        try {
            var header = soapMessage.getSOAPHeader();

            SOAPElement security =
                    (SOAPElement) StreamSupport.stream(((Iterable<Node>) header::getChildElements).spliterator(), false)
                            .filter(node -> node.getNodeName().equals("wsse:Security"))
                            .findFirst()
                            .orElseThrow(AuthError::new);

            SOAPElement auth = (SOAPElement)
                    StreamSupport.stream(((Iterable<Node>) security::getChildElements).spliterator(), false)
                            .filter(node -> node.getNodeName().equals("wsse:UsernameToken"))
                            .findFirst()
                            .orElseThrow(AuthError::new);

            SOAPElement user =
                    (SOAPElement) StreamSupport.stream(((Iterable<Node>) auth::getChildElements).spliterator(), false)
                            .filter(node -> node.getNodeName().equals("wsse:Username"))
                            .findFirst()
                            .orElseThrow(AuthError::new);

            SOAPElement pass =
                    (SOAPElement) StreamSupport.stream(((Iterable<Node>) auth::getChildElements).spliterator(), false)
                            .filter(node -> node.getNodeName().equals("wsse:Password"))
                            .findFirst()
                            .orElseThrow(AuthError::new);

            var authService = new AuthServiceImpl();
            LOGGER.info(String.format("Auth user [%s:%s]", user.getTextContent(), pass.getTextContent()));
            authService.authUser(user.getTextContent(), pass.getTextContent());
        } catch (SOAPException | ClassCastException e) {
            throw new AuthError();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean handleMessage(final SOAPMessageContext soapMessageContext) {
        Boolean outboundProperty = (Boolean) soapMessageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        if (outboundProperty) return true;
        try {
            authByHeader(soapMessageContext.getMessage());
        } catch (AuthError e) {
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
