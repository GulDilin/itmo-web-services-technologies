package guldilin.repository.impl;

import guldilin.entity.City;
import guldilin.repository.interfaces.CityRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.hibernate.SessionFactory;

/**
 * Implementation for CRUD repository for City entity. It is Bean in Application Scope.
 */
@ApplicationScoped
public class CityRepositoryImpl extends CrudRepositoryImpl<City> implements CityRepository {
    /**
     * Constructor for CityRepositoryImpl.
     * Shouldn't be called because it is Bean.
     *
     * @param sessionFactory SessionFactory (Injected)
     */
    @Inject
    public CityRepositoryImpl(final SessionFactory sessionFactory) {
        super(City.class, sessionFactory);
    }
}
