package guldilin.repository.impl;

import guldilin.entity.City;
import guldilin.repository.interfaces.CityRepository;
import guldilin.repository.interfaces.SessionFactoryBuilder;

public class CityRepositoryImpl extends CrudRepositoryImpl<City> implements CityRepository {
    public CityRepositoryImpl(SessionFactoryBuilder sessionFactoryBuilder) {
        super(City.class, sessionFactoryBuilder);
    }
}
