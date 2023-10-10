package guldilin.repository.factory;

import guldilin.repository.impl.SessionFactoryBuilderImpl;
import guldilin.repository.interfaces.SessionFactoryBuilder;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Produces;

@ApplicationScoped
public class SessionFactoryBuilderFactory {
    @Produces
    public SessionFactoryBuilder getSessionFactoryBuilder() {
        return new SessionFactoryBuilderImpl();
    }
}
