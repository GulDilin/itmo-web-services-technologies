package guldilin.repository.factory;

import guldilin.repository.impl.CityRepositoryImpl;
import guldilin.repository.interfaces.CityRepository;
import guldilin.repository.interfaces.SessionFactoryBuilder;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Produces;

@ApplicationScoped
public class CityRepositoryFactory {

    @Inject
    private SessionFactoryBuilder sessionFactoryBuilder;

    @Produces
    public CityRepository getCityRepository() {
        return new CityRepositoryImpl(sessionFactoryBuilder);
    }
}
