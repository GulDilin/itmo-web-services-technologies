package guldilin.repository.impl;

import guldilin.entity.City;
import guldilin.repository.interfaces.CityRepository;
import guldilin.repository.interfaces.SessionFactoryBuilderA;

import javax.ejb.Stateless;


@Stateless
public class CityRepositoryImpl extends CrudRepositoryImpl<City> implements CityRepository {
//    public CityRepositoryImpl(SessionFactoryBuilderA sessionFactoryBuilder) {
//        super(City.class, sessionFactoryBuilder);
//    }
    public CityRepositoryImpl() {
        super(City.class, new SessionFactoryBuilderImpl());
    }
}
