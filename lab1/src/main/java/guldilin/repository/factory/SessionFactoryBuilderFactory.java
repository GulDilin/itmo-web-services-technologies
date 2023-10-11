package guldilin.repository.factory;

import guldilin.repository.impl.SessionFactoryBuilderImpl;
import guldilin.repository.interfaces.SessionFactoryBuilderA;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Singleton
@NoArgsConstructor
public class SessionFactoryBuilderFactory implements Serializable {
    @Produces
    @Named("SessionFactoryBuilder")
    public SessionFactoryBuilderA getSessionFactoryBuilder() {
        return new SessionFactoryBuilderImpl();
    }
}
