package guldilin.repository.impl;

import guldilin.entity.City;
import guldilin.repository.interfaces.CityRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.hibernate.SessionFactory;

@ApplicationScoped
public class CityRepositoryImpl extends CrudRepositoryImpl<City> implements CityRepository {
    @Inject
    public CityRepositoryImpl(SessionFactory sessionFactory) {
        super(City.class, sessionFactory);
    }
}